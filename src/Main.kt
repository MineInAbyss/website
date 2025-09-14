import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.decodeFromStream
import me.dvyy.shocky.page.CommonFrontMatter
import me.dvyy.shocky.page.Page
import me.dvyy.shocky.shocky
import pages.*
import templates.blogPost
import templates.default
import templates.redirect
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.div
import kotlin.io.path.inputStream

@OptIn(ExperimentalPathApi::class)
suspend fun main(args: Array<String>) = shocky {
    dest("out")
    assets("site/assets")
    watch("site")
    siteRoot("site")

    tailwind {
        inputCss = siteRoot / "custom.css"
    }
    routing {
        template("default", Page::default)
        template("gallery", Page::gallery)
        template("album", Page::album)
        template("home", Page::homePage)
        template("blog", Page::blogPost)
        template("contributors", Page::contributors)

        pages(".")

        val redirectUrls = Yaml.default
            .decodeFromStream<Map<String, String>>((siteRoot / "redirects.yml").inputStream())

        redirectUrls.forEach { (from, to) ->
            generate(from, meta = CommonFrontMatter()) {
                redirect(to)
            }
        }
        "blog" {
            generate(meta = CommonFrontMatter(title = "Blog", url = "/blog")) { blogIndex() }
        }

        generate("faq", meta = CommonFrontMatter(title = "Faq")) { faq() }
    }
}.run(args)