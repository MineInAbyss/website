package components

import kotlinx.html.FlowContent
import kotlinx.html.a
import kotlinx.html.div
import kotlinx.html.img
import kotlinx.html.nav
import me.dvyy.shocky.page.Page

fun FlowContent.navigation(
    outlined: Boolean = true,
    center: Boolean = true,
    showLogo: Boolean = false,
    alwaysFloating: Boolean = false,
    page: Page,
) = div("flex w-full") {
    div(buildString {
        appendLine("flex items-center p-2 bg-stone-800")
        if (alwaysFloating) appendLine("rounded-lg") else appendLine("max-md:rounded-lg md:w-full")
        if (center) appendLine("justify-center mx-auto w-max")
        if (outlined) {
            if (alwaysFloating) appendLine("border border-stone-700")
        }
    }) {
        val selected = ""
//        fun isSelected(url: String) = if (page.url == url) selected else unselected
//        fun startsWith(url: String) = if (page.url.startsWith(url)) selected else unselected
        if (showLogo) a(href = "/") { img(classes = "not-prose mr-2") { src = "/assets/favicon.png" } }
        nav("flex space-x-2") {
            coloredButton("Home", selected = page.url == "/", url = "/")
            coloredButton("Gallery", selected = page.url.startsWith("/albums") || page.url == "/gallery", url = "/gallery")//, icon = { icons.camera })
            coloredButton("Rules", url = "/rules")//, icon = { icons.article })
            coloredButton("Wiki", url = "https://wiki.mineinabyss.com")//, icon = { icons.infoCircle })
        }
    }
}
