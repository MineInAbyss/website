package pages

import kotlinx.html.a
import kotlinx.html.img
import me.dvyy.shocky.page.Page
import templates.default
import kotlin.io.path.*

fun Page.gallery() = default {
    for (image in Path("site/assets/gallery").listDirectoryEntries()
        .map { it.relativeTo(Path("site")) }
        .filter { !it.nameWithoutExtension.endsWith("-min") }
    ) {
        a(href = image.pathString) {
            img { src = image.pathString; alt = image.nameWithoutExtension }
        }
    }
}
