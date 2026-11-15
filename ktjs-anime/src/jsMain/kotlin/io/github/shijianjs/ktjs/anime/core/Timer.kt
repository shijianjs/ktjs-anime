package io.github.shijianjs.ktjs.anime.core

import io.github.shijianjs.ktjs.anime.easings.Spring
import js.objects.Record
import kotlin.js.JsAny
import kotlin.js.Promise

/**
 * Base timer of anime.js, upstream `class Timer`.
 *
 * A timer only drives time: it fires callbacks but animates nothing. Every animation and timeline
 * inherits these members.
 */
open external class Timer {
    /** Timer id, `String | Number`. */
    var id: TimerId

    /** Parent timeline, `null` for root timers. */
    val parent: Timeline?

    /** Total duration in milliseconds, including delays and loops. */
    var duration: Double

    /** Whether the timer currently runs backwards. */
    var backwards: Boolean

    /** Whether the timer is paused. */
    var paused: Boolean

    /** Whether the timer has started at least once. */
    val began: Boolean

    /** Whether the timer reached its end. */
    val completed: Boolean

    /** Called once when the timer starts. */
    var onBegin: ((Timer) -> Unit)?

    /** Called before every frame update. */
    var onBeforeUpdate: ((Timer) -> Unit)?

    /** Called on every frame update. */
    var onUpdate: ((Timer) -> Unit)?

    /** Called at the end of every loop. */
    var onLoop: ((Timer) -> Unit)?

    /** Called when the timer is paused. */
    var onPause: ((Timer) -> Unit)?

    /** Called when the timer completes. */
    var onComplete: ((Timer) -> Unit)?

    /** Duration of a single iteration in milliseconds. */
    var iterationDuration: Double

    /** Number of iterations. */
    var iterationCount: Double

    /** Whether the timer was cancelled. */
    var cancelled: Boolean

    /** Current time in milliseconds. */
    var currentTime: Double

    /** Current time inside the current iteration. */
    var iterationCurrentTime: Double

    /** Playback progress in the `0..1` range. */
    var progress: Double

    /** Progress of the current iteration in the `0..1` range. */
    var iterationProgress: Double

    /** Index of the current iteration. */
    var currentIteration: Double

    /** Whether playback is reversed. */
    var reversed: Boolean

    /** Resets the timer to its initial state. */
    fun reset(softReset: Boolean?): Timer

    /** Initialises the timer, starting it when `autoplay` is enabled. */
    fun init(internalRender: Boolean?): Timer

    /** Resets the internal time without touching the callbacks. */
    fun resetTime(): Timer

    /** Pauses the timer. */
    fun pause(): Timer

    /** Resumes a paused timer. */
    fun resume(): Timer

    /** Restarts the timer from the beginning. */
    fun restart(): Timer

    /** Seeks to [time] in milliseconds. */
    fun seek(time: Double, muteCallbacks: JsAny?, internalRender: JsAny?): Timer

    /** Reverses the playback direction. */
    fun alternate(): Timer

    /** Plays the timer. */
    fun play(): Timer

    /** Reverses the playback direction and plays. */
    fun reverse(): Timer

    /** Cancels the timer. */
    fun cancel(): Timer

    /** Rescales the timer to [newDuration] milliseconds. */
    open fun stretch(newDuration: Double): Timer

    /** Cancels the timer and reverts the values it changed. */
    open fun revert(): Timer

    /** Immediately completes the timer and fires `onComplete`. */
    fun complete(muteCallbacks: JsAny?): Timer

    /** Promise that settles when the timer completes. */
    open fun then(callback: ((Timer) -> Unit)?): Promise<Timer>
}

/**
 * Parameters accepted by [Timer], upstream `TimerParams = TimerOptions & TickableCallbacks<Timer>`.
 */
external interface TimerParams {
    /** Timer id. */
    var id: TimerId?

    /** Duration in milliseconds, or a function returning one per target. */
    var duration: TweenParamValue?

    /** Delay in milliseconds, or a function returning one per target. */
    var delay: TweenParamValue?

    /** Delay between loops in milliseconds. */
    var loopDelay: Double?

    /** Whether playback starts reversed. */
    var reversed: Boolean?

    /** Whether playback alternates direction on every loop. */
    var alternate: Boolean?

    /** Number of loops, `true` for an infinite loop. */
    var loop: JsAny?

    /** Whether the timer starts automatically. */
    var autoplay: JsAny?

    /** Frame rate cap in frames per second. */
    var frameRate: Double?

    /** Playback speed multiplier. */
    var playbackRate: Double?

    /** Render priority. */
    var priority: Double?

    /** Called once when the timer starts. */
    var onBegin: ((Timer) -> Unit)?

    /** Called before every frame update. */
    var onBeforeUpdate: ((Timer) -> Unit)?

    /** Called on every frame update. */
    var onUpdate: ((Timer) -> Unit)?

    /** Called at the end of every loop. */
    var onLoop: ((Timer) -> Unit)?

    /** Called when the timer is paused. */
    var onPause: ((Timer) -> Unit)?

    /** Called when the timer completes. */
    var onComplete: ((Timer) -> Unit)?
}

/** `stagger` options, upstream `StaggerParams`. */
external interface StaggerParams {
    /** Stagger start value in milliseconds. */
    var start: JsAny?

    /** Where the stagger starts from. */
    var from: StaggerFrom?

    /** Whether the stagger runs backwards. */
    var reversed: Boolean?

    /** Grid dimensions used to compute the distance between targets. */
    var grid: StaggerGrid?

    /** Axis used with [grid], `"x"`, `"y"` or `"z"`. */
    var axis: String?

    /** Custom method returning the index of each target. */
    var use: JsAny?

    /** Total stagger time in milliseconds. */
    var total: Double?

    /** Easing applied to the stagger values. */
    var ease: EasingParam?

    /** Modifies each computed stagger value. */
    var modifier: ((Double) -> JsAny)?

    /** Uniform random noise added to each stagger value. */
    var jitter: JsAny?

    /** Seed of the random generator, `true` seeds with `0`. */
    var seed: JsAny?
}

/** A `Record<String, Number>` of timeline labels. */
typealias TimelineLabels = Record<String, Double>
