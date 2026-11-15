package io.github.shijianjs.ktjs.anime

import io.github.shijianjs.ktjs.anime.core.AnimationParams
import io.github.shijianjs.ktjs.anime.core.JSAnimation
import io.github.shijianjs.ktjs.anime.core.StaggerFunction
import io.github.shijianjs.ktjs.anime.core.StaggerParams
import io.github.shijianjs.ktjs.anime.core.StaggerValue
import io.github.shijianjs.ktjs.anime.core.TargetsParam
import io.github.shijianjs.ktjs.anime.core.Timer
import io.github.shijianjs.ktjs.anime.core.TimerParams
import io.github.shijianjs.ktjs.anime.core.Timeline
import io.github.shijianjs.ktjs.anime.core.TimelineParams
import io.github.shijianjs.ktjs.anime.easings.Easings
import io.github.shijianjs.ktjs.anime.utils.AnimeUtils
import js.objects.unsafeJso
import kotlin.js.JsModule
import kotlin.js.JsName

/*
 * The `animejs` module namespace.
 *
 * anime.js 4 is an ES module with named exports and no default export. Kotlin/JS turns an
 * `@JsModule` + `@JsName` *function declaration* into a default import, which webpack rejects
 * ("export 'default' was not found"), while an `@JsModule` external *object* becomes a namespace
 * import (`import * as animejs from 'animejs'`). Every entry point is therefore a member of this
 * object.
 *
 * Upstream treats a missing parameter object as `{}` (`constructor(parameters = {})`), so an explicit
 * `null` must never be forwarded: destructuring `null` throws. The empty overloads are separate
 * members bound to the same JavaScript function with zero arguments.
 */
@JsModule("animejs")
external object AnimeNamespace {
    /** Creates an animation running [parameters] on [targets]. */
    fun animate(targets: TargetsParam, parameters: AnimationParams): JSAnimation

    /** Creates a timeline configured by [parameters]. */
    @JsName("createTimeline")
    fun createTimeline(parameters: TimelineParams): Timeline

    /** Creates an empty timeline. */
    @JsName("createTimeline")
    fun createTimelineEmpty(): Timeline

    /** Creates a timer configured by [parameters]. */
    @JsName("createTimer")
    fun createTimer(parameters: TimerParams): Timer

    /** Creates an empty timer. */
    @JsName("createTimer")
    fun createTimerEmpty(): Timer

    /** Stagger helper spreading [value] across the targets. */
    fun stagger(value: StaggerValue, params: StaggerParams): StaggerFunction

    /** `anime.utils`: number, target and time helpers. */
    val utils: AnimeUtils

    /** `anime.easings`: easing factories and the built-in eases. */
    val easings: Easings
}

/** `anime.utils`: number, target and time helpers. */
val animeUtils: AnimeUtils get() = AnimeNamespace.utils

/** `anime.easings`: easing factories and the built-in eases. */
val animeEasings: Easings get() = AnimeNamespace.easings

/** Creates an animation running [parameters] on [targets]. */
fun animate(targets: TargetsParam, parameters: AnimationParams): JSAnimation =
    AnimeNamespace.animate(targets, parameters)

/** Creates an empty timeline. */
fun createTimeline(): Timeline = AnimeNamespace.createTimelineEmpty()

/** Creates a timeline configured by [parameters]. */
fun createTimeline(parameters: TimelineParams): Timeline = AnimeNamespace.createTimeline(parameters)

/** Creates an empty timer. */
fun createTimer(): Timer = AnimeNamespace.createTimerEmpty()

/** Creates a timer configured by [parameters]. */
fun createTimer(parameters: TimerParams): Timer = AnimeNamespace.createTimer(parameters)

/** Stagger helper spreading [value] across the targets. */
fun stagger(value: Double, params: StaggerParams = staggerParams {}): StaggerFunction =
    AnimeNamespace.stagger(StaggerValue(value), params)

/** Stagger helper for string values. */
fun stagger(value: String, params: StaggerParams = staggerParams {}): StaggerFunction =
    AnimeNamespace.stagger(StaggerValue(value), params)

/** Stagger helper for `[from, to]` range values. */
fun stagger(value: List<Double>, params: StaggerParams = staggerParams {}): StaggerFunction =
    AnimeNamespace.stagger(StaggerValue(value), params)

/** Builds an [AnimationParams] object. */
fun animationParams(build: AnimationParams.() -> Unit): AnimationParams = unsafeJso(build)

/** Builds a [TimelineParams] object. */
fun timelineParams(build: TimelineParams.() -> Unit): TimelineParams = unsafeJso(build)

/** Builds a [TimerParams] object. */
fun timerParams(build: TimerParams.() -> Unit): TimerParams = unsafeJso(build)

/** Builds a [StaggerParams] object. */
fun staggerParams(build: StaggerParams.() -> Unit): StaggerParams = unsafeJso(build)
