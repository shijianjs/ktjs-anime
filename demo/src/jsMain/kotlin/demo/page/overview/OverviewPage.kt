package demo.page.overview

import io.github.shijianjs.ktjs.demo.page.DemoPage
import io.github.shijianjs.ktjs.demo.i18n.Binder
import io.github.shijianjs.ktjs.demo.i18n.Lang
import io.github.shijianjs.ktjs.demo.i18n.Langs
import io.github.shijianjs.ktjs.demo.i18n.Translations
import io.github.shijianjs.ktjs.demo.i18n.texts
import io.github.shijianjs.ktjs.demo.page.Blocks.anchor
import io.github.shijianjs.ktjs.demo.page.Blocks.codeText
import io.github.shijianjs.ktjs.demo.page.Blocks.paragraph
import io.github.shijianjs.ktjs.demo.page.Blocks.row
import io.github.shijianjs.ktjs.demo.page.SectionBlock
import io.github.shijianjs.ktjs.dom.element.invoke
import io.github.shijianjs.ktjs.dom.element.unaryPlus
import demo.API_URL
import demo.DOCS_URL
import demo.SOURCE_URL
import web.html.HtmlTagName

/**
 * Landing page: what the library wraps, how it is consumed, where the reference lives.
 *
 * The sections are plain blocks, not components: they have nothing to swap at run time, so a component
 * per section would only add indirection.
 */
internal class OverviewPage : DemoPage() {

    private val texts = Binder(overviewTexts)

    init {
        addLink(texts.bind(anchor("", DOCS_URL, external = true), { it.docsLink }))
        addLink(texts.bind(anchor("", API_URL, external = true), { it.apiLink }))
        addLink(texts.bind(anchor("", SOURCE_URL, external = true), { it.sourceLink }))

        val purpose = SectionBlock.NAME { show() }
        texts.bind(purpose.heading, { it.purposeHeading })

        val bullets = HtmlTagName.ul {
            List(4) { index -> texts.bind(+HtmlTagName.li()) { it.bullets.getOrNull(index).orEmpty() } }
        }
        purpose.body.append(texts.bind(paragraph(), { it.body }), bullets)

        val coordinates = SectionBlock.NAME { show() }
        texts.bind(coordinates.heading, { it.coordinatesHeading })
        texts.bind(coordinates.hint, { it.coordinatesNote })
        coordinates.body.append(
            row(codeText("implementation(\"io.github.shijianjs:ktjs-anime:4.5.0-kt.1\")")),
            row(texts.bind(anchor("", SOURCE_URL, external = true), { it.sourceLink })),
        )

        val next = SectionBlock.NAME { show() }
        texts.bind(next.heading, { it.nextHeading })
        next.body.append(texts.bind(paragraph(), { it.nextBody }))

        body.append(purpose.root, coordinates.root, next.root)
    }

    override fun applyTranslation(lang: Lang) {
        val current = overviewTexts[lang]
        setTitle(current.title)
        setSummary(current.summary)
        texts.apply(lang)
    }

    companion object : OverviewPageDefaultReference() {

        private val overviewEnglish: OverviewTexts = OverviewTexts(
            title = "Overview",
            summary = "A Kotlin/JS wrapper for anime.js: typed targets, tween parameters, timelines and " +
                "easings, with no `JsAny` in the public API.",
            docsLink = "Documentation",
            sourceLink = "Source repository",
            apiLink = "API reference",
            purposeHeading = "What it wraps",
            body = "anime.js takes plain objects: `{ targets, translateX, duration, ease }`. In Kotlin those " +
                "objects would be `JsAny` literals; this wrapper models the same options as declarations, so " +
                "a misspelled property or a wrong value type fails at compile time instead of at runtime.",
            bullets = listOf(
                "Target / TargetsParam — a DOM node or a selector, typed.",
                "animationParams { } and timelineParams { } — the option objects of `animate` and `createTimeline`.",
                "EasingParam, TweenParamValue — the value types anime accepts for `ease` and `duration`.",
                "TimelinePosition — where a child animation starts on the timeline.",
            ),
            coordinatesHeading = "Coordinates",
            coordinatesNote = "The demo next to this site is built with the library itself, so every page doubles as an integration test.",
            installLabel = "Gradle coordinate",
            nextHeading = "Reading order",
            nextBody = "The overview, then the animation page: a staggered timeline and a single tween whose easing comes from a picker.",
        )
        private val overviewChinese: OverviewTexts = OverviewTexts(
            title = "概览",
            summary = "anime.js 的 Kotlin/JS 封装：类型化的目标、补间参数、时间线与缓动，公开 API 里没有 `JsAny`。",
            docsLink = "使用文档",
            sourceLink = "源码仓库",
            apiLink = "API 文档",
            purposeHeading = "它封装了什么",
            body = "anime.js 收的是普通对象：`{ targets, translateX, duration, ease }`。在 Kotlin 里这些对象只能写成 " +
                "`JsAny` 字面量；这个封装把同一套选项变成声明，属性拼错或类型不对在编译期就报错，而不是运行时才失效。",
            bullets = listOf(
                "Target / TargetsParam —— DOM 节点或选择器，带类型。",
                "animationParams { } 与 timelineParams { } —— `animate` 与 `createTimeline` 的选项对象。",
                "EasingParam、TweenParamValue —— anime 接受的 `ease` 与 `duration` 取值类型。",
                "TimelinePosition —— 子动画在时间线上的起点。",
            ),
            coordinatesHeading = "坐标",
            coordinatesNote = "这个站点旁边的 demo 本身就用这个库搭建，每页同时充当它的集成测试。",
            installLabel = "Gradle 坐标",
            nextHeading = "建议顺序",
            nextBody = "先看概览，再看动画页：一条带错开的时间线，和一条缓动取自下拉框的补间。",
        )
        private val overviewTexts: Translations<OverviewTexts> = texts(
            Langs.EN to overviewEnglish,
            Langs.ZH_HANS to overviewChinese,
        )
    }
}
