package demo

import io.github.shijianjs.ktjs.anime.animate
import io.github.shijianjs.ktjs.anime.animationParams
import io.github.shijianjs.ktjs.anime.createTimeline
import io.github.shijianjs.ktjs.anime.timelineParams
import io.github.shijianjs.ktjs.anime.core.EasingParam
import io.github.shijianjs.ktjs.anime.core.Target
import io.github.shijianjs.ktjs.anime.core.TargetsParam
import io.github.shijianjs.ktjs.anime.core.TimelinePosition
import io.github.shijianjs.ktjs.anime.core.TweenParamValue
import io.github.shijianjs.ktjs.anime.core.tweenKeyValue
import io.github.shijianjs.ktjs.anime.core.property
import web.cssom.ClassName
import web.dom.document
import web.events.EventHandler
import web.html.HTMLElement
import web.html.HtmlTagName

private const val PROJECT_NAME = "ktjs-anime"

private val texts = Texts(
    mapOf<String, Pair<String, String>>(
        "h1" to ("$PROJECT_NAME demo" to "$PROJECT_NAME 演示"),
        "intro" to Pair(
            "Every animation below is a real anime.js animation created through the Kotlin/JS " +
                "wrapper: a timeline with staggered children and one tween whose easing comes from " +
                "the select.",
            "下面每个动画都是通过 Kotlin/JS wrapper 创建的真实 anime.js 动画：一条带错开子项的" +
                "时间线，以及一条缓动由下拉框决定的补间。",
        ),
        "timeline" to ("Timeline with a stagger" to "带错开的时间线"),
        "easeDemo" to ("Single tween, easing from the select" to "单条补间，缓动取自下拉框"),
        "play" to ("Play" to "播放"),
        "reset" to ("Reset" to "重置"),
        "ease" to ("Easing" to "缓动"),
        "version" to ("anime.js API driven by ktjs-anime" to "由 ktjs-anime 驱动的 anime.js API"),
    ),
)

/** The easing names the demo offers; all of them ship with `animejs`. */
private val easingNames = listOf("outQuad", "outCubic", "outExpo", "outBounce", "outElastic")

/** Wraps a DOM element as an anime.js target. */
private fun HTMLElement.target(): Target = Target(this)

/**
 * Entry point of the demo page.
 *
 * Nodes are built with `document.createElement` and mutated in place — no HTML strings, no
 * `innerHTML` (see the UI conventions in AGENTS.md).
 */
fun main() {
    val root = document.createElement(HtmlTagName.div).apply { className = ClassName("page") }
    val heading = document.createElement(HtmlTagName.h1)
    val intro = document.createElement(HtmlTagName.p).apply { className = ClassName("hint") }
    val version = document.createElement(HtmlTagName.p).apply { className = ClassName("version") }

    val logo = document.createElement(HtmlTagName.img).apply {
        className = ClassName("logo")
        setAttribute("src", "img/$PROJECT_NAME-logo.png")
        setAttribute("alt", "$PROJECT_NAME logo")
    }

    val header = document.createElement(HtmlTagName.header).apply {
        className = ClassName("header")
        append(logo, heading, languageSwitch(texts))
    }

    // --- Section 1: a timeline with staggered children ------------------------------------
    val timelineTitle = document.createElement(HtmlTagName.h2)
    val stage = document.createElement(HtmlTagName.div).apply { className = ClassName("stage") }
    val boxes = (1..5).map { index ->
        document.createElement(HtmlTagName.div).apply {
            className = ClassName("box")
            textContent = index.toString()
            stage.append(this)
        }
    }

    val timelinePlay = document.createElement(HtmlTagName.button).apply { setAttribute("type", "button") }
    val timelineReset = document.createElement(HtmlTagName.button).apply { setAttribute("type", "button") }

    fun resetBoxes() {
        boxes.forEach { box ->
            box.style.transform = ""
            box.style.opacity = "1"
        }
    }

    val timeline = createTimeline(timelineParams { autoplay = false })
    timelinePlay.onclick = EventHandler {
        resetBoxes()
        timeline.restart()
    }

    timelineReset.onclick = EventHandler {
        timeline.pause()
        resetBoxes()
    }

    val timelineRow = document.createElement(HtmlTagName.div).apply {
        className = ClassName("row")
        append(timelinePlay, timelineReset)
    }

    // --- Section 2: one tween, easing picked from the select --------------------------------
    val easingTitle = document.createElement(HtmlTagName.h2)
    val easeStage = document.createElement(HtmlTagName.div).apply { className = ClassName("stage") }
    val dot = document.createElement(HtmlTagName.div).apply {
        className = ClassName("dot")
        easeStage.append(this)
    }

    val easingLabel = document.createElement(HtmlTagName.label).apply { className = ClassName("label") }
    val easingSelect = document.createElement(HtmlTagName.select).apply { className = ClassName("select") }
    easingNames.forEach { name ->
        easingSelect.append(
            document.createElement(HtmlTagName.option).apply {
                value = name
                textContent = name
            },
        )
    }

    val easingPlay = document.createElement(HtmlTagName.button).apply { setAttribute("type", "button") }

    fun playDot() {
        animate(
            TargetsParam(dot.target()),
            animationParams {
                property("translateX", 280.0)
                duration = TweenParamValue(1400.0)
                ease = EasingParam(easingSelect.value)
            },
        )
    }

    easingPlay.onclick = EventHandler { playDot() }
    easingSelect.onchange = EventHandler { playDot() }

    val easingRow = document.createElement(HtmlTagName.div).apply {
        className = ClassName("row")
        append(easingLabel, easingSelect, easingPlay)
    }

    val content = document.createElement(HtmlTagName.main).apply {
        className = ClassName("content")
        append(timelineTitle, timelineRow, stage, easingTitle, easingRow, easeStage)
    }

    // The timeline is built once: each box animates in at its own staggered position.
    boxes.forEachIndexed { index, box ->
        timeline.add(
            TargetsParam(box.target()),
            animationParams {
                property(
                    "translateY",
                    tweenKeyValue {
                        from = TweenParamValue(-120.0)
                        to = TweenParamValue(0.0)
                    },
                )
                property("opacity", 1.0)
                duration = TweenParamValue(500.0)
                ease = EasingParam("outBack")
            },
            TimelinePosition(index.toDouble() * 150.0),
        )
    }

    fun render() {
        heading.textContent = texts["h1"]
        intro.textContent = texts["intro"]
        timelineTitle.textContent = texts["timeline"]
        easingTitle.textContent = texts["easeDemo"]
        timelinePlay.textContent = texts["play"]
        timelineReset.textContent = texts["reset"]
        easingPlay.textContent = texts["play"]
        easingLabel.textContent = "${texts["ease"]}: "
        version.textContent = texts["version"]
    }
    texts.onChange { render() }
    render()

    root.append(header, intro, version, content)
    document.body.append(root)
}
