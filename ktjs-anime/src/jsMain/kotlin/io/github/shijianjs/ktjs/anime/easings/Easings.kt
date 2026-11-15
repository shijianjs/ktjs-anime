package io.github.shijianjs.ktjs.anime.easings

import io.github.shijianjs.ktjs.anime.core.EasingParam
import io.github.shijianjs.ktjs.anime.core.JSAnimation
import kotlin.js.JsName

/** Parameters of a [Spring], upstream `SpringParams`. */
external interface SpringParams {
    /** Mass, defaults to `1`. */
    var mass: Double?

    /** Stiffness, defaults to `100`. */
    var stiffness: Double?

    /** Damping, defaults to `10`. */
    var damping: Double?

    /** Initial velocity, defaults to `0`. */
    var velocity: Double?

    /** Initial bounce, defaults to `0`. */
    var bounce: Double?

    /** Perceived duration, defaults to `0`. */
    var duration: Double?

    /** Called when the spring current time reaches the perceived duration. */
    var onComplete: ((JSAnimation) -> Unit)?
}

/**
 * A spring easing, upstream `class Spring`.
 *
 * A spring is a stateful easing: every animation using it drives the same solver.
 */
external class Spring {
    /** Called when the spring reaches its perceived duration. */
    var onComplete: ((JSAnimation) -> Unit)?

    /** Easing function produced by the solver. */
    val ease: (Double) -> Double

    /** Solves the spring position at [time] in milliseconds. */
    fun solve(time: Double): Double

    /** Mass of the spring. */
    var mass: Double

    /** Stiffness of the spring. */
    var stiffness: Double

    /** Damping of the spring. */
    var damping: Double

    /** Initial velocity of the spring. */
    var velocity: Double

    /** Initial bounce of the spring. */
    var bounce: Double

    /** Perceived duration of the spring. */
    var duration: Double
}

/** The easing factories of `anime.easings`. */
external interface Easings {
    /** Builds a spring easing with default parameters. */
    fun spring(): Spring

    /** Builds a spring easing from [parameters]. */
    fun spring(parameters: SpringParams): Spring

    /** Builds a cubic-bezier easing. */
    /** Builds a cubic-bezier easing with the default control points. */
    fun cubicBezier(): (Double) -> Double

    /** Builds a cubic-bezier easing from the four control point coordinates. */
    fun cubicBezier(mX1: Double, mY1: Double, mX2: Double, mY2: Double): (Double) -> Double

    /** Builds a steps easing with [steps] steps, optionally starting at the first step. */
    /** Builds a steps easing with two steps starting at the end. */
    fun steps(): (Double) -> Double

    /** Builds a steps easing with [steps] steps. */
    fun steps(steps: Double): (Double) -> Double

    /** Builds a steps easing with [steps] steps, optionally starting at the first step. */
    fun steps(steps: Double, fromStart: Boolean): (Double) -> Double

    /** Builds a linear easing from the given points. */
    fun linear(point: Double, vararg points: Double): (Double) -> Double

    /** Builds an irregular easing from [length] samples with [randomness]. */
    /** Builds an irregular easing with the default sample count. */
    fun irregular(): (Double) -> Double

    /** Builds an irregular easing from [length] samples with [randomness]. */
    fun irregular(length: Double, randomness: Double): (Double) -> Double

    /** The built-in easing functions, upstream `eases`. */
    val eases: EaseFunctions
}

/** The built-in easing set, upstream `eases`. */
external interface EaseFunctions {
    /** Linear easing. */
    val linear: (Double) -> Double

    /** No easing, the value jumps to its end value. */
    val none: (Double) -> Double

    /** Power easing factory, upstream `in(power)`. */
    @JsName("in")
    val inPower: (Double) -> (Double) -> Double

    /** Power easing factory, upstream `out(power)`. */
    val outPower: (Double) -> (Double) -> Double

    /** Power easing factory, upstream `inOut(power)`. */
    val inOutPower: (Double) -> (Double) -> Double

    /** Quadratic ease in. */
    val inQuad: (Double) -> Double

    /** Quadratic ease out. */
    val outQuad: (Double) -> Double

    /** Quadratic ease in and out. */
    val inOutQuad: (Double) -> Double

    /** Cubic ease in. */
    val inCubic: (Double) -> Double

    /** Cubic ease out. */
    val outCubic: (Double) -> Double

    /** Cubic ease in and out. */
    val inOutCubic: (Double) -> Double

    /** Sine ease in. */
    val inSine: (Double) -> Double

    /** Sine ease out. */
    val outSine: (Double) -> Double

    /** Sine ease in and out. */
    val inOutSine: (Double) -> Double

    /** Exponential ease in. */
    val inExpo: (Double) -> Double

    /** Exponential ease out. */
    val outExpo: (Double) -> Double

    /** Exponential ease in and out. */
    val inOutExpo: (Double) -> Double

    /** Circular ease in. */
    val inCirc: (Double) -> Double

    /** Circular ease out. */
    val outCirc: (Double) -> Double

    /** Circular ease in and out. */
    val inOutCirc: (Double) -> Double

    /** Elastic ease in, `inElastic(amplitude, period)`. */
    val inElastic: (Double, Double) -> (Double) -> Double

    /** Elastic ease out, `outElastic(amplitude, period)`. */
    val outElastic: (Double, Double) -> (Double) -> Double

    /** Back ease in, `inBack(overshoot)`. */
    val inBack: (Double) -> (Double) -> Double

    /** Back ease out, `outBack(overshoot)`. */
    val outBack: (Double) -> (Double) -> Double

    /** Bounce ease in. */
    val inBounce: (Double) -> Double

    /** Bounce ease out. */
    val outBounce: (Double) -> Double
}

/** Wraps an easing name such as `"outQuad"` as an [EasingParam]. */
fun easeName(name: String): EasingParam = io.github.shijianjs.ktjs.anime.core.EasingParam(name)
