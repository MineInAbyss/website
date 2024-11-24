package components

import kotlinx.html.*

inline fun FlowContent.optionalA(href: String?, crossinline block: FlowContent.() -> Unit) {
    if (href != null) a(href = href, block = block)
    else block()
}

inline fun FlowContent.card(
    title: String,
    subtitle: String? = null,
    image: String? = null,
    url: String? = null,
    imageHeight: String = "280px",
    showContent: Boolean = true,
    crossinline icon: HTMLTag.() -> Unit = {},
    crossinline content: FlowContent.() -> Unit = {},
) {
    div("not-prose flex flex-col bg-stone-800 border border-stone-700 rounded-lg shadow-md") {
        optionalA(href = url) {
            div("group w-full ${if (image != null) "h-[$imageHeight]" else ""} relative overflow-hidden") {
                if (image != null) {
                    lazyImg(
                        src = image,
                        alt = title,
                        classes = "w-full h-full object-cover ${if (showContent) "rounded-t-lg" else "rounded-lg"} m-0"
                    ) { }
                    if (showContent) div("absolute bottom-0 left-0 right-0 h-32 bg-gradient-to-t from-stone-800 to-transparent") {}
                    else div("rounded-b-lg absolute bottom-0 left-0 right-0 h-1/2 md:h-32 bg-gradient-to-t from-[rgba(0,0,0,0.6)] to-transparent") {}
                }
                div("${if (image != null) "absolute" else "pt-4"} ${if (url != null) "transition-opacity duration-300 group-hover:opacity-70" else ""} bottom-0 w-full px-4 mb-2 drop-shadow-lg") {
                    div("flex flex-row items-center space-x-1") {
                        icon()
                        p("text-2xl font-bold") { +title }
                    }
                    if (subtitle != null) code("text-sm select-all") { +subtitle }
                }
            }
        }
        if (showContent) div(if (subtitle == null) "p-4" else "px-4 pb-4") {
            div("text-sm") {
                content()
            }
        }
    }
}
