package pages

import components.cardText
import kotlinx.html.*
import kotlinx.serialization.Serializable
import me.dvyy.shocky.markdown
import me.dvyy.shocky.page.Page
import templates.default

@Serializable
data class AlbumMeta(
    val images: List<ImageMeta>,
)

fun Page.album() = default() {
    val images = page.meta<AlbumMeta>().images
    markdown(page.content)
    div("grid grid-cols-1 full-bleed") {
        div("md:wrapper-wide space-y-6") {
//        Path("site/assets/gallery")
//            .listDirectoryEntries()
//            .filter { !it.nameWithoutExtension.endsWith("-min") && it.extension == "webp" }
//            .map { it.relativeTo(Path("site")) to it.getImageMetaOrNull() }
            images
                .sortedWith(compareBy({ it.order ?: 1000.0 }/*, { it.nameWithoutExtension }*/))
                .forEach { meta ->
                    albumImage(
                        meta.title,
                        subtitle = buildString {
                            if (meta.author != null) append("By ${meta.author}")
                            if (meta.desc != null) {
                                append(" — ")
                                append(meta.desc)
                            }
                        },
//                    imageClasses = "min-h-54",
//                    showContent = false,
//                    textInsideImage = false,
                        url = meta.url,
                        image = meta.thumbnail,
                    )
                }
        }
    }
}

inline fun FlowContent.albumImage(
    title: String?,
    subtitle: String? = null,
    image: String? = null,
    url: String? = null,
    crossinline icon: HTMLTag.() -> Unit = {},
) = div("not-prose") {
    a(href = url) {
        lazyImg(
            src = image,
            alt = title ?: "Untitled",
            classes = "drop-shadow-lg md:drop-shadow-xl w-full object-cover md:rounded-lg max-md:border-y md:border border-stone-700 min-h-54"
        ) { }
    }
    div("px-2 pt-2 pb-3") {
        cardText(icon, title, subtitle)
    }
}
