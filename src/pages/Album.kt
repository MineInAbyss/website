package pages

import components.card
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.serialization.Serializable
import me.dvyy.shocky.markdown
import me.dvyy.shocky.page.Page
import templates.default
import java.nio.file.Path
import kotlin.io.path.*

@Serializable
data class AlbumMeta(
    val images: List<ImageMeta>,
)

fun Page.album() = default {
    classes += "max-w-screen-xl"
    val images = page.meta<AlbumMeta>().images
    markdown(page.content)
    div("grid grid-cols-1 space-y-4") {
//        Path("site/assets/gallery")
//            .listDirectoryEntries()
//            .filter { !it.nameWithoutExtension.endsWith("-min") && it.extension == "webp" }
//            .map { it.relativeTo(Path("site")) to it.getImageMetaOrNull() }
        images
            .sortedWith(compareBy({ it.order ?: 1000.0 }/*, { it.nameWithoutExtension }*/))
            .forEach { meta ->
                card(
                    meta.title,
                    subtitle = buildString {
                        if(meta.author != null) append("By ${meta.author}")
                        if (meta.desc != null) {
                            append(" — ")
                            append(meta.desc)
                        }
                    },
                    imageClasses = "min-h-54",
                    showContent = false,
                    url = meta.url,
                    image = meta.thumbnail,
                )
            }
    }
}
