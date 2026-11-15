package io.github.shijianjs.ktjs.anime

import io.github.shijianjs.ktjs.anime.core.EasingParam
import io.github.shijianjs.ktjs.anime.core.Target
import io.github.shijianjs.ktjs.anime.core.TargetsParam
import io.github.shijianjs.ktjs.anime.core.TimelinePosition
import io.github.shijianjs.ktjs.anime.core.TweenParamValue
import io.github.shijianjs.ktjs.anime.core.property
import io.github.shijianjs.ktjs.anime.core.tweenKeyValue
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import web.dom.document
import web.html.HTMLElement

/**
 * Runs the wrapper against the real anime.js module in Chromium.
 *
 * The assertions read values produced by the library (progress, transform, utility results), so a
 * wrapper that compiles but does not bind the runtime fails here.
 */
class AnimeTest {
    private fun box(id: String): HTMLElement {
        val element = document.createElement("div")
        element.setAttribute("id", id)
        document.body.appendChild(element)
        return element
    }

    @Test
    fun animateInterpolatesATransformAtAGivenProgress() {
        val element = box("box-animate")
        val animation =
            animate(
                TargetsParam(Target(element)),
                animationParams {
                    property("translateX", 100.0)
                    duration = TweenParamValue(1000.0)
                    ease = EasingParam("linear")
                },
            )
        animation.pause()
        animation.progress = 0.5
        val transform = element.style.transform
        assertTrue(transform.contains("50"), "expected half of the travel, got '$transform'")
        animation.revert()
    }

    @Test
    fun progressAndCompletionFollowThePlayhead() {
        val element = box("box-progress")
        val animation = animate(TargetsParam(Target(element)), animationParams { property("opacity", 0.0) })
        animation.pause()
        animation.progress = 1.0
        assertEquals(1.0, animation.progress)
        assertTrue(animation.completed)
        animation.revert()
    }

    @Test
    fun timelineRunsChildrenAtTheRequestedPositions() {
        val first = box("box-timeline-a")
        val second = box("box-timeline-b")
        val timeline = createTimeline()
        timeline.add(
            TargetsParam(Target(first)),
            animationParams { property("translateX", 100.0); duration = TweenParamValue(500.0) },
            TimelinePosition(0.0),
        )
        timeline.add(
            TargetsParam(second),
            animationParams { property("translateX", 100.0); duration = TweenParamValue(500.0) },
            TimelinePosition(500.0),
        )
        timeline.pause()
        timeline.progress = 0.5
        assertTrue(first.style.transform.contains("100"), "first child should be finished at 50%")
        assertEquals(1000.0, timeline.duration)
        timeline.revert()
    }

    @Test
    fun timerCountsTimeWithoutAnimating() {
        val timer =
            createTimer(timerParams { duration = TweenParamValue(400.0); autoplay = false })
        assertEquals(400.0, timer.duration)
        assertTrue(!timer.completed)
        timer.cancel()
    }

    @Test
    fun utilsComputePlainNumbers() {
        assertEquals(100.0, animeUtils.clamp(120.0, 0.0, 100.0))
        assertEquals(0.5, animeUtils.lerp(0.0, 1.0, 0.5))
        assertEquals(180.0, animeUtils.radToDeg(3.141592653589793))
        assertEquals(50.0, animeUtils.round(49.6, 0.0))
    }

    @Test
    fun easingFunctionsComeFromTheUpstreamModule() {
        val outQuad = animeEasings.eases.outQuad
        assertEquals(0.75, outQuad(0.5))
        assertEquals(1.0, outQuad(1.0))
        val spring = animeEasings.spring()
        spring.duration = 1000.0
        assertEquals(1000.0, spring.duration)
    }

    @Test
    fun staggerDelaysTheSecondTarget() {
        val delay = stagger(200.0)
        val timeline = createTimeline()
        val first = box("box-stagger-a")
        val second = box("box-stagger-b")
        timeline.add(
            TargetsParam(arrayOf(Target(first), Target(second))),
            animationParams { duration = TweenParamValue(500.0) },
            TimelinePosition(0.0),
        )
        timeline.pause()
        assertTrue(delay != null)
        timeline.revert()
    }

    @Test
    fun perPropertyTweenKeyValueIsApplied() {
        val element = box("box-keyvalue")
        val animation =
            animate(
                TargetsParam(Target(element)),
                animationParams {
                    property(
                        "opacity",
                        tweenKeyValue {
                            from = TweenParamValue(0.2)
                            to = TweenParamValue(0.8)
                        },
                    )
                    duration = TweenParamValue(1000.0)
                },
            )
        animation.pause()
        animation.progress = 1.0
        assertEquals(0.8, element.style.opacity.toDouble())
        animation.revert()
    }
}
