package io.github.shijianjs.ktjs.anime.utils

import io.github.shijianjs.ktjs.anime.core.AnimationParams
import io.github.shijianjs.ktjs.anime.core.JSAnimation
import io.github.shijianjs.ktjs.anime.core.Target
import io.github.shijianjs.ktjs.anime.core.TargetsParam
import io.github.shijianjs.ktjs.anime.core.Timer
import kotlin.js.JsAny

/** `anime.utils`: the number, target and time helpers of anime.js 4. */
external interface AnimeUtils {
    /** Clamps [value] between [min] and [max]. */
    fun clamp(value: Double, min: Double, max: Double): Double

    /** Rounds [value] to [decimalLength] decimals. */
    fun round(value: Double, decimalLength: Double): Double

    /** Snaps [value] to the closest multiple of [increment]. */
    fun snap(value: Double, increment: Double): Double

    /** Wraps [value] inside the `[min, max]` range. */
    fun wrap(value: Double, min: Double, max: Double): Double

    /** Linearly interpolates between [start] and [end] by [factor]. */
    fun lerp(start: Double, end: Double, factor: Double): Double

    /** Damps [start] towards [end] with [deltaTime] milliseconds and [factor]. */
    fun damp(start: Double, end: Double, deltaTime: Double, factor: Double): Double

    /** Maps [value] from `[inLow, inHigh]` to `[outLow, outHigh]`. */
    fun mapRange(
        value: Double,
        inLow: Double,
        inHigh: Double,
        outLow: Double,
        outHigh: Double,
    ): Double

    /** Converts [degrees] to radians. */
    fun degToRad(degrees: Double): Double

    /** Converts [radians] to degrees. */
    fun radToDeg(radians: Double): Double

    /** Random number between [min] and [max], rounded to [decimalLength] decimals. */
    fun random(min: Double, max: Double, decimalLength: Double): Double

    /** Picks a random item of [items]. */
    fun randomPick(items: JsAny): JsAny

    /** Shuffles [items], optionally with a seeded random generator [rnd]. */
    fun shuffle(items: Array<JsAny>, rnd: JsAny): Array<JsAny>

    /** Reads the current [propertyName] of the first target of [targets]. */
    fun get(targets: TargetsParam, propertyName: String, unit: JsAny): JsAny

    /** Sets [parameters] on [targets] without animating them. */
    fun set(targets: TargetsParam, parameters: AnimationParams): JSAnimation

    /** Removes the tweens of [targets]. */
    fun remove(targets: TargetsParam, renderable: JsAny, propertyName: String): Array<Target>

    /** Creates a timer synchronised with the animation engine clock. */
    fun sync(callback: (Timer) -> Unit): Timer
}
