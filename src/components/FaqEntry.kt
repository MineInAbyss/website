package components

import kotlinx.html.FlowContent
import kotlinx.html.details
import kotlinx.html.div
import kotlinx.html.summary
import me.dvyy.shocky.markdown

fun FlowContent.faqEntry(question: String, answer: String, open: Boolean = false) {
    details("border border-stone-700 rounded-lg") {
        this.open = open
        summary("p-4 cursor-pointer text-lg font-semibold select-none") {
            +question
        }
        div("px-4 pb-4 text-sm") {
            markdown(answer)
        }
    }
}

fun FlowContent.faq(entries: Map<String, String>) {
    div("flex flex-col space-y-4") {
        for ((index, entry) in entries.entries.withIndex()) {
            val (question, answer) = entry
            faqEntry(question, answer, open = index == 0)
        }
    }
}
