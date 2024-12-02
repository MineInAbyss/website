package templates

import components.navigation
import kotlinx.html.*
import me.dvyy.shocky.markdown
import me.dvyy.shocky.page.Page

inline fun Page.default(
    includeNavigation: Boolean = true,
    crossinline body: FlowContent.() -> Unit = { },
    crossinline prose: MAIN.() -> Unit = { markdown(content) },
) = html {
    lang = "en"
    head {
        meta(charset = "utf-8")
        meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
        link(rel = "icon", type = "image/png", href = "/assets/favicon.png")
        link(rel = LinkRel.stylesheet, href = "/assets/tailwind/styles.css")
        title(page.title)
        meta(name = "description", content = page.desc ?: page.title)
        script(src = "/assets/scripts/dark-mode.js") {}
    }
    body(classes = "bg-stone-800 text-stone-100 min-h-screen overflow-x-hidden") {
        body()
        main("""prose prose-invert prose-stone prose-lg
            |prose-figcaption:italic prose-figcaption:text-center prose-figcaption:mt-0 prose-figcaption:mb-2 prose-figcaption:px-4
            |prose-img:mb-2
            |md:mx-auto max-w-screen-lg mt-12 px-4
            |overflow-x-hidden""".trimMargin()) {
            if (includeNavigation) {
                h1("flex mt-8 mx-auto justify-center") { +page.title }
                navigation(page = page)
            }
            prose()
        }
        div("h-4 md:h-36")
    }
}
