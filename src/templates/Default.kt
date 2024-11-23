package templates

import components.navigation
import kotlinx.html.*
import me.dvyy.shocky.markdown
import me.dvyy.shocky.page.Page

inline fun Page.default(
    includeNavigation: Boolean = true,
    crossinline body: FlowContent.() -> Unit = { },
    crossinline prose: FlowContent.() -> Unit = { markdown(content) },
) = html {
    head {
        meta(charset = "utf-8")
        meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
        link(rel = LinkRel.stylesheet, href = "/assets/tailwind/styles.css")
        link(rel = LinkRel.stylesheet, href = "/assets/custom.css")
        title(page.title)
        script(src = "/assets/scripts/dark-mode.js") {}
    }
    body(classes = "bg-stone-800 text-stone-100 min-h-screen overflow-x-hidden") {
        body()
        main("prose prose-invert prose-stone prose-lg md:mx-auto max-w-screen-lg mt-12 px-4") {
            if (includeNavigation) {
                navigation(page = page)
                h1("mt-8") { +page.title }
            }
            prose()
        }
        div("md:h-36")
    }
}