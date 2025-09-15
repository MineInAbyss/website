package templates

import components.navigation
import kotlinx.html.*
import me.dvyy.shocky.markdown
import me.dvyy.shocky.page.Page

inline fun Page.default(
    includeNavigation: Boolean = true,
    wide: Boolean = false,
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
    body(classes = "bg-stone-800 text-stone-100 ") {
        body()
        main(
            """
            prose prose-invert prose-stone prose-md ${if (wide) "wrapper-wide" else "wrapper"} max-w-none w-auto
            prose-figcaption:italic prose-figcaption:text-center prose-figcaption:mt-0 prose-figcaption:mb-2 prose-figcaption:px-4
            prose-img:mb-2
            prose-h1:mb-6
            prose-h2:mb-4 prose-h2:mt-6
            prose-h3:mb-3 prose-h3:mt-4
            prose-h4:mb-2 prose-h4:mt-3 prose-h4:uppercase prose-h4:text-stone-200
            prose-li:my-1
            prose-ul:my-2
            prose-p:mt-1 prose-p:mb-4
            prose-ol:my-2
            prose-a:text-stone-400
            overflow-x-hidden""".trimIndent()
        ) {
            if (includeNavigation) {
                navigation(page = page, showLogo = true, center = false)
                hr("full-bleed !my-0")
                h1("flex mt-8") { +page.title }
            }
            prose()
        }
        div("h-4 md:h-36")
    }
}
