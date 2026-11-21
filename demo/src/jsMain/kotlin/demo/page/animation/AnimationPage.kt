package demo.page.animation

import io.github.shijianjs.ktjs.demo.page.DemoPage
import io.github.shijianjs.ktjs.anime.animate
import io.github.shijianjs.ktjs.anime.animationParams
import io.github.shijianjs.ktjs.anime.core.EasingParam
import io.github.shijianjs.ktjs.anime.core.TargetsParam
import io.github.shijianjs.ktjs.anime.core.TimelinePosition
import io.github.shijianjs.ktjs.anime.core.TweenParamValue
import io.github.shijianjs.ktjs.anime.core.property
import io.github.shijianjs.ktjs.anime.core.tweenKeyValue
import io.github.shijianjs.ktjs.anime.createTimeline
import io.github.shijianjs.ktjs.anime.timelineParams
import io.github.shijianjs.ktjs.demo.i18n.Binder
import io.github.shijianjs.ktjs.demo.i18n.Lang
import io.github.shijianjs.ktjs.demo.i18n.Langs
import io.github.shijianjs.ktjs.demo.i18n.Translations
import io.github.shijianjs.ktjs.demo.i18n.texts
import io.github.shijianjs.ktjs.demo.page.Blocks.row
import io.github.shijianjs.ktjs.demo.page.SectionBlock
import io.github.shijianjs.ktjs.dom.element.invoke
import io.github.shijianjs.ktjs.vaadin.button.createButton
import web.dom.clickEvent
import web.events.addHandler
import web.html.HTMLElement
import web.html.HtmlTagName

/**
 * The animation page: both examples on one page, because they are one story — the timeline builds
 * children, the tween takes one value from a picker.
 *
 * The nodes are fields and the animations are built once; a language switch only rewrites text, so a
 * running or finished animation keeps its position while the copy changes.
 */
internal class AnimationPage : DemoPage() {

    private val texts = Binder(animationTexts)

    private val boxes: List<Box> = (1..5).map { index -> Box.NAME { show(index) } }

    private val timelineStage: Stage = Stage.NAME().apply { boxes.forEach { append(it) } }

    private val easingStage: Stage = Stage.NAME()

    private val dotElement: Dot = Dot.NAME()

    private val timelinePlay = createButton()

    private val timelineReset = createButton()

    private val easingPlay = createButton()

    private val easingLabel: HTMLElement = HtmlTagName.label {}

    private val easingSelect: EasingPicker = EasingPicker.NAME { offer(EASINGS) }

    /** The staggered timeline; built once, restarted by the Play button. */
    private val timeline = createTimeline(timelineParams { autoplay = false }).apply {
        boxes.forEachIndexed { index, each ->
            add(
                TargetsParam(each.target()),
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
    }

    init {
        texts.bind(timelinePlay, { it.play })
        texts.bind(timelineReset, { it.reset })
        texts.bind(easingPlay, { it.play })
        texts.bind(easingLabel) { it.easingLabel + ": " }

        val timelineSection = SectionBlock.NAME { show() }
        texts.bind(timelineSection.heading, { it.timelineHeading })
        texts.bind(timelineSection.hint, { it.timelineHint })
        timelineSection.body.append(row(timelinePlay, timelineReset), timelineStage)

        val easingSection = SectionBlock.NAME { show() }
        texts.bind(easingSection.heading, { it.easingHeading })
        texts.bind(easingSection.hint, { it.easingHint })
        easingSection.body.append(row(easingLabel, easingSelect, easingPlay), easingStage)

        attachDot()

        body.append(timelineSection.root, easingSection.root)

        timelinePlay.clickEvent.addHandler {
            resetBoxes()
            timeline.restart()
        }
        timelineReset.clickEvent.addHandler {
            timeline.pause()
            resetBoxes()
        }
        easingPlay.clickEvent.addHandler { playDot() }
        // A native `<select>`: the value it reports is exactly the `ease` name anime takes.
        easingSelect.onChange { playDot() }
    }

    /** The dot sits on the stage from the first paint; playing only restarts its position. */
    private fun attachDot() {
        easingStage.append(dotElement)
    }

    private fun resetBoxes() {
        boxes.forEach { each ->
            each.style.setProperty("transform", "")
            each.style.setProperty("opacity", "1")
        }
    }

    private fun playDot() {
        dotElement.style.setProperty("transform", "")
        animate(
            TargetsParam(dotElement.target()),
            animationParams {
                property("translateX", 400.0)
                duration = TweenParamValue(1400.0)
                ease = EasingParam(easingSelect.value)
            },
        )
    }

    override fun applyTranslation(lang: Lang) {
        val current = animationTexts[lang]
        setTitle(current.title)
        setSummary(current.summary)
        texts.apply(lang)
    }

    companion object : AnimationPageDefaultReference() {

        private val animationEnglish: AnimationTexts = AnimationTexts(
            title = "Animation",
            summary = "Two real anime.js animations created through the Kotlin/JS wrapper: a timeline with " +
                "staggered children, and one tween whose easing comes from the picker.",
            timelineHeading = "Timeline with a stagger",
            timelineHint = "Each box starts 150 ms after the previous one, on a timeline built once at start-up.",
            easingHeading = "Single tween, easing from the picker",
            easingHint = "Choosing an easing plays the same tween again with the chosen curve.",
            play = "Play",
            reset = "Reset",
            easingLabel = "Easing",
        )
        private val animationChinese: AnimationTexts = AnimationTexts(
            title = "动画",
            summary = "两条通过 Kotlin/JS wrapper 创建的真实 anime.js 动画：一条带错开子项的时间线，" +
                "以及一条缓动取自下拉框的补间。",
            timelineHeading = "带错开的时间线",
            timelineHint = "每个方块比前一个晚 150 毫秒入场，时间线在启动时构建一次。",
            easingHeading = "单条补间，缓动取自下拉框",
            easingHint = "换一个缓动会按所选曲线重新播放同一条补间。",
            play = "播放",
            reset = "重置",
            easingLabel = "缓动",
        )
        private val animationTexts: Translations<AnimationTexts> = texts(
            Langs.EN to animationEnglish,
            Langs.ZH_HANS to animationChinese,
        )
        /** The easing names the demo offers; all of them ship with `animejs`. */
        private val EASINGS: List<String> = listOf("outQuad", "outCubic", "outExpo", "outBounce", "outElastic")
    }
}
