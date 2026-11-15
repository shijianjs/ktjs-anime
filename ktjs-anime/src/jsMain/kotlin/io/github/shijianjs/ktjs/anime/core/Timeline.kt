package io.github.shijianjs.ktjs.anime.core

import kotlin.js.JsAny
import kotlin.js.Promise

/**
 * An anime.js timeline, upstream `class Timeline extends Timer`.
 *
 * Instances are created with [io.github.shijianjs.ktjs.anime.createTimeline].
 */
external class Timeline : Timer {
    /** Named positions of the timeline. */
    val labels: TimelineLabels

    /** Default parameters inherited by every animation added to the timeline. */
    var defaults: DefaultsParams?

    /** Whether the children are composed together instead of played one after the other. */
    var composition: Boolean?

    /** Called on every render of the timeline. */
    var onRender: ((Timeline) -> Unit)?

    /** Adds an animation for [targets] at [position]. */
    fun add(targets: TargetsParam, parameters: AnimationParams, position: TimelinePosition?): Timeline

    /** Adds a child timer configured by [parameters] at [position]. */
    fun add(parameters: TimerParams, position: TimelinePosition?): Timeline

    /** Synchronises [synced] with the timeline at [position]. */
    fun sync(synced: JsAny, position: TimelinePosition?): Timeline

    /** Sets [targets] to the values of [parameters] at [position], without animating. */
    fun set(targets: TargetsParam, parameters: AnimationParams, position: TimelinePosition?): Timeline

    /** Calls [callback] at [position]. */
    fun call(callback: (Timer) -> Unit, position: TimelinePosition?): Timeline

    /** Records a label named [labelName] at [position]. */
    fun label(labelName: String, position: TimelinePosition?): Timeline

    /** Removes every tween of [targets], optionally only the ones of [propertyName]. */
    fun remove(targets: TargetsParam, propertyName: String?): Timeline

    /** Rescales the timeline to [newDuration] milliseconds. */
    override fun stretch(newDuration: Double): Timeline

    /** Recomputes every child of the timeline. */
    fun refresh(): Timeline

    /** Cancels the timeline and reverts every value it changed. */
    override fun revert(): Timeline

    /** Promise that settles when the timeline completes. */
    fun then(callback: ((Timeline) -> Unit)?): Promise<Timeline>
}

/**
 * Parameters of [Timeline], upstream `TimelineParams = TimerOptions & TimelineOptions & callbacks`.
 */
external interface TimelineParams : TimerParams {
    /** Default parameters inherited by the animations added to the timeline. */
    var defaults: DefaultsParams?

    /** Easing applied to the playback of the timeline. */
    var playbackEase: EasingParam?

    /** Whether the children are composed together. */
    var composition: Boolean?

    /** Called on every render of the timeline. */
    var onRender: ((Timeline) -> Unit)?
}

/**
 * Default parameters shared by animations, upstream `DefaultsParams`.
 *
 * Only the documented options are typed; the animated properties themselves are not part of this
 * object.
 */
external interface DefaultsParams {
    /** Easing used by animations that do not set one. */
    var ease: EasingParam?

    /** Duration used by animations that do not set one. */
    var duration: Double?

    /** Delay used by animations that do not set one. */
    var delay: Double?

    /** Loop count used by animations that do not set one. */
    var loop: JsAny?

    /** Loop delay used by animations that do not set one. */
    var loopDelay: Double?

    /** Playback rate used by animations that do not set one. */
    var playbackRate: Double?

    /** Whether animations alternate direction on every loop by default. */
    var alternate: Boolean?

    /** Composition mode used by animations that do not set one. */
    var composition: String?

    /** Modifier used by animations that do not set one. */
    var modifier: ((Double) -> JsAny)?
}
