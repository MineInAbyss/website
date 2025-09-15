package pages

import components.card
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.hr
import me.dvyy.shocky.markdown
import me.dvyy.shocky.page.Page
import me.dvyy.shocky.page.Pages
import templates.default
import kotlin.io.path.Path

fun Page.gallery() = default {
    markdown(
        """
        Welcome to our gallery! This page contains historical albums from our Reddit posts, as well as many fun images
        taken throughout the years. Click on any image to view a full size, uncompressed version.
        """.trimIndent()
    )
    div("space-y-8") {
        Pages.walk(Path("site/albums"), Path("site"))
            .map { it.read() }
            .groupBy { it.date?.year }
            .entries
            .sortedByDescending { it.key }
            .forEach { (year, pages) ->
                h1("mb-2") { +"$year" }
                hr("!my-4")
                div("grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-3 gap-4") {
                    pages.sortedByDescending { it.date }.forEach { page ->
                        card(
                            title = page.title,
                            image = page.meta<AlbumMeta>().images.firstOrNull()?.thumbnail,
                            subtitle = page.desc,
                            url = page.url,
                            imageClasses = "aspect-square",
                            showContent = false,
                        )
                    }
                }
            }
    }
}