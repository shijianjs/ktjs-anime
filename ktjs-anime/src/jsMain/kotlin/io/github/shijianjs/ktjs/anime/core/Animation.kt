package io.github.shijianjs.ktjs.anime.core

import js.objects.unsafeJso
import kotlin.js.JsAny
import kotlin.js.Promise
import kotlin.js.unsafeCast

/** A tween parameter value, upstream `number | string | FunctionValue | EasingParam | TweakRegister`. */
external interface TweenParamValue

/** Wraps a number as a [TweenParamValue]. */
fun TweenParamValue(value: Double): TweenParamValue = value.unsafeCast<TweenParamValue>()

/** Wraps a string such as `"2rem"` or `"50%"` as a [TweenParamValue]. */
fun TweenParamValue(value: String): TweenParamValue = value.unsafeCast<TweenParamValue>()

/** Wraps a per-target function as a [TweenParamValue]. */
fun TweenParamValue(value: (Target?, Double?, Array<Target>?) -> JsAny): TweenParamValue =
    value.unsafeCast<TweenParamValue>()

/** Wraps an easing value as a [TweenParamValue]. */
fun TweenParamValue(value: EasingParam): TweenParamValue = value.unsafeCast<TweenParamValue>()

/**
 * Parameters of [animate], upstream
 * `AnimationParams = Record<string, TweenOptions | …> & TimerOptions & AnimationOptions & …`.
 *
 * The upstream type is an index signature, so every animated property (`translateX`, `opacity`,
 * a custom object field, …) is set with [property] while all fixed options are typed fields.
 */
external interface AnimationParams : TimerParams {
    /** Easing of the whole animation. */
    var ease: EasingParam?

    /** Easing applied to the playback of the timeline this animation belongs to. */
    var playbackEase: EasingParam?

    /** Composition mode of the animated values, `"none"`, `"replace"` or `"blend"`. */
    var composition: String?

    /** Modifies every animated value before it is applied. */
    var modifier: ((Double) -> JsAny)?

    /** Keyframes, either percentage based or duration based. */
    var keyframes: JsAny?

    /** Called on every render of the animation. */
    var onRender: ((JSAnimation) -> Unit)?
}

/** Builds the parameters of a single animated property, upstream `TweenKeyValue`. */
external interface TweenKeyValue {
    /** Duration of the tween in milliseconds. */
    var duration: TweenParamValue?

    /** Delay of the tween in milliseconds. */
    var delay: TweenParamValue?

    /** Easing of the tween. */
    var ease: EasingParam?

    /** Modifier of the tweened value. */
    var modifier: ((Double) -> JsAny)?

    /** Composition mode of the tween. */
    var composition: String?

    /** Start value of the tween. */
    var from: TweenParamValue?

    /** End value of the tween. */
    var to: TweenParamValue?

    /** Start and end value of the tween. */
    var fromTo: JsAny?
}

/** Builds a [TweenKeyValue] object. */
fun tweenKeyValue(build: TweenKeyValue.() -> Unit): TweenKeyValue = unsafeJso(build)

/** Sets the animated property [name] to [value]. */
fun AnimationParams.property(name: String, value: Double) {
    putProperty(this, name, value.unsafeCast<JsAny>())
}

/** Sets the animated property [name] to a string value such as `"50%"`. */
fun AnimationParams.property(name: String, value: String) {
    putProperty(this, name, value.unsafeCast<JsAny>())
}

/** Sets the animated property [name] to an arbitrary JavaScript value. */
fun AnimationParams.property(name: String, value: JsAny) {
    putProperty(this, name, value)
}

/** Sets the animated property [name] to a per-property [TweenKeyValue]. */
fun AnimationParams.property(name: String, value: TweenKeyValue) {
    putProperty(this, name, value.unsafeCast<JsAny>())
}

/**
 * An anime.js animation, upstream `class JSAnimation extends Timer`.
 *
 * Instances are created with [io.github.shijianjs.ktjs.anime.animate].
 */
external class JSAnimation : Timer {
    /** The targets this animation drives. */
    val targets: Array<Target>

    /** Called on every render of the animation. */
    var onRender: ((JSAnimation) -> Unit)?

    /** Rescales the animation to [newDuration] milliseconds. */
    override fun stretch(newDuration: Double): JSAnimation

    /** Recomputes every tween of the animation. */
    fun refresh(): JSAnimation

    /** Cancels the animation and reverts every value it changed. */
    override fun revert(): JSAnimation

    /** Promise that settles when the animation completes. */
    fun then(callback: ((JSAnimation) -> Unit)?): Promise<JSAnimation>
}

private fun putProperty(target: JsAny, name: String, value: JsAny) {
    js("target[name] = value")
}
