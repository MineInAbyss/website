package pages

import Colors.primary
import components.*
import kotlinx.html.*
import kotlinx.serialization.Serializable
import me.dvyy.shocky.markdown
import me.dvyy.shocky.page.Page
import templates.default

@Serializable
data class HomeMeta(val faq: Map<String, String>)

fun Page.homePage() = default(
    includeNavigation = false,
    body = {
        fullScreenImage("/assets/Render-min.webp") {
            div("flex flex-col items-center justify-center h-full relative") {
//                div("absolute top-0 left-0 right-0 drop-shadow-md") { navbar("Home") }
                img(
                    src = "/assets/logo.svg",
                    alt = "The Mine in Abyss logo",
                    classes = "drop-shadow-md mx-auto md:w-1/2 w-3/4 transform transition-transform duration-300 hover:scale-105"
                )
                a(href = "#about", classes = "hidden md:block animate-pulse") {
                    icons.withClasses("h-14 w-14").chevronDown
                }
                div("absolute bottom-0 left-0 right-0 h-32 bg-gradient-to-t from-stone-800 to-transparent") {}
            }
        }

        div("relative") {
            id = "about"
            div("absolute bottom-0 transform -translate-y-4 w-full") {
                navigation(page = page)
            }
        }
    }
) {
    markdown(
        """
        # About us

        Mine in Abyss is a community-run recreation of the Made in Abyss anime in Minecraft. 
        You can join our servers for free *without any mods*, and all our custom code is available on our [GitHub](https://github.com/MineInAbyss).
        """.trimIndent()
    )

    div("flex flex-row flex-wrap gap-4") {
        coloredButton("Join our Community", "bg-[#5865F2]", "https://discord.gg/jDd7x8V", icon = { icons.discord })
        div("group grow") {
            coloredButton("Contribute", "bg-$primary", "https://mineinabyss.com/contributing",
                icon = { div("group-hover:animate-heartBeat") { icons.heart } }
            )
        }
    }

    h2 { +"Our Servers" }

    div("grid grid-cols-1 md:grid-cols-3 gap-4 not-prose") {
        card(
            "Survival",
            subtitle = "survive.mineinabyss.com",
            icon = { icons.meat },
        ) {
            p { +"Play and build with other members of the community." }
        }
        card(
            "Build",
            subtitle = "build.mineinabyss.com",
            icon = { icons.hammer },
        ) {
            p { +"Explore the map with flight enabled, build in the creative world." }
        }
        card(
            "Archive",
            subtitle = "archive.mineinabyss.com",
            icon = { icons.archive },
        ) {
            p { +"An archive of past maps, containing old survival builds and towns." }
        }
    }

    h2 { +"Features" }

    div("grid grid-cols-1 md:grid-cols-2 gap-4 not-prose") {
        card("Explore the abyss", image = "/assets/gallery/orth-tiny.webp", icon = { icons.map }) {
            p { +"Explore the deepest Minecraft world, featuring 5 layers, over 7000 blocks, and a fully built city of Orth." }
        }

        card("Survive and thrive", image = "/assets/gallery/survival-tiny.webp", icon = { icons.meat }) {
            p { +"Create guilds, beautiful player builds, and thrive in the depths of the abyss." }
        }
        card("Discover new wildlife", image = "/assets/gallery/mobs-tiny.webp", icon = { icons.butterfly }) {
            p { +"Discover a large variety of custom monsters and animals on each layer" }
        }
        card(
            "Play with custom mechanics",
            image = "/assets/gallery/bonfire-tiny.webp",
            icon = { icons.settingsSpark }) {
            p { +"Use our climbing system, feel the curse of the abyss, and play with many more features custom built for this server." }
        }
        card(
            "Get our modpack",
            image = "/assets/gallery/voxy-tiny.webp",
            url = "/modpack",
            icon = { icons.download }
        ) {
            p { +"See the entire abyss at once using Voxy, as well as other quality of life and performance optimizing mods." }
        }
    }

    h2 { +"FAQ" }

    div("not-prose flex flex-col space-y-4") {
        val homeMeta = page.meta<HomeMeta>()
        for ((question, answer) in homeMeta.faq.entries) {
            faqEntry(question, answer)
        }
    }

    h2 { +"Support us! " }
    p { +"We're a community-run project and rely on contributions from community members. We use all donations to keep our servers running and pay for tools like Axiom which our builders use to make the map." }
    div("flex flex-row flex-wrap gap-4") {
        coloredButton("Patreon", "bg-[#e76551]", "https://www.patreon.com/mineinabyss", icon = { icons.patreon })
        coloredButton("Ko-fi", "bg-[#467CEB]", "https://ko-fi.com/mineinabyss", icon = { icons.kofi })
    }
}

