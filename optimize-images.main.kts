#!/usr/bin/env kotlin

@file:DependsOn("com.sksamuel.scrimage:scrimage-core:4.3.4")
@file:DependsOn("com.sksamuel.scrimage:scrimage-formats-extra:4.3.4")
@file:DependsOn("com.sksamuel.scrimage:scrimage-webp:4.3.4")
@file:DependsOn("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")

import com.sksamuel.scrimage.ImmutableImage
import com.sksamuel.scrimage.ScaleMethod
import com.sksamuel.scrimage.nio.ImageWriter
import com.sksamuel.scrimage.nio.PngReader
import com.sksamuel.scrimage.webp.WebpImageReader
import com.sksamuel.scrimage.webp.WebpWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Semaphore
import java.nio.file.Path
import kotlin.io.path.*

val loader = ImmutableImage.loader()
    .withImageReaders(listOf(WebpImageReader(), PngReader()))

val uncompressed = WebpWriter.DEFAULT.withMultiThread().withoutAlpha().withLossless()
val min = WebpWriter.DEFAULT.withMultiThread().withoutAlpha().withQ(85).withM(4)
val tiny = WebpWriter.DEFAULT.withMultiThread().withoutAlpha().withQ(75).withM(4)

fun compressImage(
    source: Path,
    outputPath: Path,
    edit: ImmutableImage.() -> ImmutableImage,
//    ext: String,
    writer: ImageWriter,
) {
    val webpOutput =
        outputPath.parent / (outputPath.nameWithoutExtension + ".webp") //Path("out") / path.parent.relativeTo(Path("site")) / (path.nameWithoutExtension + "-$ext.webp")
    if (webpOutput.exists()) return
    loader.fromPath(source)
//        .scaleToHeight(1080, ScaleMethod.Bicubic)
        .run(edit)
        .output(writer, webpOutput)
}

suspend fun compress() = coroutineScope {
    val compress = Path("compress").takeIf { it.exists() } ?: return@coroutineScope
    val semaphore = Semaphore(5)
    compress.walk().filter { it.extension == "png" || it.extension == "webp" }.forEach { path ->
        launch(Dispatchers.IO) {
            semaphore.acquire()
            val out = Path("output")
            val relative = path.relativeTo(compress)
            println("Compressing $path...")
            compressImage(path, (out / relative).createParentDirectories(), { this }, uncompressed)
//            compressImage(
//                path,
//                (out / relative.parent / "tiny" / relative.name).createParentDirectories(),
//                { scaleToHeight(560, ScaleMethod.Bicubic) },
//                tiny,
//            )
            compressImage(
                path,
                (out / relative.parent / "min" / relative.name).createParentDirectories(),
                { scaleToHeight(1080, ScaleMethod.Bicubic) },
                min,
            )
            semaphore.release()
        }
    }
}

runBlocking {
    compress()
}
