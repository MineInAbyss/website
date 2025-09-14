package components

import kotlinx.html.*

fun FlowContent.coloredButton(
    text: String,
    classes: String,
    url: String,
    icon: A.() -> Unit = {},
) {
    a(
        href = url,
        classes = "$classes not-prose w-full sm:w-max py-2 px-3 sm:px-4 md:px-6 h-10 text-white hover:text-stone-100 uppercase rounded-md font-bold text-sm no-underline flex items-center space-x-2"
    ) {
        icon()
        span { +text }
    }
}
