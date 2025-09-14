package components

import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.nav
import me.dvyy.shocky.page.Page

fun FlowContent.navigation(outlined: Boolean = true, center: Boolean = true, page: Page) = div("flex w-full justify-center") {
    div(buildString {
        appendLine("flex items-center p-2 bg-stone-800 rounded-lg")
        if (center) appendLine("justify-center mx-auto w-max")
        if (outlined) appendLine("border border-stone-700")
    }) {
        val selected = "bg-stone-700 hover:bg-stone-600"
        val unselected = "bg-stone-800 hover:bg-stone-700"
        fun isSelected(url: String) = if (page.url == url) selected else unselected
        nav("flex space-x-2") {
            coloredButton("Home", isSelected("/"), "/")
            coloredButton("Gallery", isSelected("/gallery"), "/gallery")//, icon = { icons.camera })
            coloredButton("Rules", isSelected("/rules"), "/rules")//, icon = { icons.article })
            coloredButton("Wiki", unselected, "https://wiki.mineinabyss.com")//, icon = { icons.infoCircle })
        }
    }
}
