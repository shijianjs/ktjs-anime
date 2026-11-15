package io.github.shijianjs.ktjs.anime.core

import kotlin.js.JsAny
import web.dom.Element
import kotlin.js.JsName
import kotlin.js.unsafeCast

/** A DOM or plain-object animation target, upstream `Target = DOMTarget | JSTarget`. */
external interface Target

/**
 * One or more animation targets, upstream
 * `TargetsParam = Array<TargetSelector> | TargetSelector` where a selector is a target, a `NodeList`
 * or a CSS selector string.
 */
external interface TargetsParam

/** A `string | number` id used by timers and animations, upstream `String | Number`. */
external interface TimerId

/** An easing value, upstream `EasingParam = string | EasingFunction | Spring | TweakRegister`. */
external interface EasingParam

/** A timeline position, upstream `number | '+=N' | '-=N' | '*=N' | '<' | '<<' | string`. */
external interface TimelinePosition

/** A stagger value, upstream `number | string | [number, number]`. */
external interface StaggerValue

/** `stagger.from`, upstream `number | 'first' | 'center' | 'last' | 'random' | Array<number>`. */
external interface StaggerFrom

/** `stagger.grid`, upstream `Array<number> | boolean`. */
external interface StaggerGrid

/** A stagger function, upstream `StaggerFunction<T>`. */
typealias StaggerFunction =
    (
    target: Target?,
    index: Double?,
    targets: Array<Target>?,
    prevTween: JsAny?,
    timeline: Timeline?,
    ) -> JsAny

/** Wraps a CSS selector string as a [TargetsParam]. */
fun TargetsParam(selector: String): TargetsParam = selector.unsafeCast<TargetsParam>()

/** Wraps an element as a [TargetsParam]. */
fun TargetsParam(element: Element): TargetsParam = element.unsafeCast<TargetsParam>()

/** Wraps a single [Target]. */
fun TargetsParam(target: Target): TargetsParam = target.unsafeCast<TargetsParam>()

/** Wraps an array of [Target]s. */
fun TargetsParam(targets: Array<Target>): TargetsParam = targets.unsafeCast<TargetsParam>()

/** Wraps a DOM [element][Element] as a [Target]. */
fun Target(element: Element): Target = element.unsafeCast<Target>()

/** Wraps a plain JavaScript object as a [Target]. */
fun Target(value: JsAny): Target = value.unsafeCast<Target>()

/** Wraps a string or number as a [TimerId]. */
fun TimerId(id: String): TimerId = id.unsafeCast<TimerId>()

/** Wraps a number as a [TimerId]. */
fun TimerId(id: Double): TimerId = id.unsafeCast<TimerId>()

/** Wraps an easing name such as `"outQuad"` or `"cubicBezier(0.5, 0, 0.5, 1)"`. */
fun EasingParam(name: String): EasingParam = name.unsafeCast<EasingParam>()

/** Wraps a Kotlin function as an easing function, upstream `EasingFunction = (time) => number`. */
fun EasingParam(function: (Double) -> Double): EasingParam = function.unsafeCast<EasingParam>()

/** Wraps a [Spring] as an easing value. */
fun EasingParam(spring: io.github.shijianjs.ktjs.anime.easings.Spring): EasingParam =
    spring.unsafeCast<EasingParam>()

/** Wraps an absolute position in milliseconds. */
fun TimelinePosition(time: Double): TimelinePosition = time.unsafeCast<TimelinePosition>()

/** Wraps a relative or named position such as `"+=200"`, `"<"` or `"intro"`. */
fun TimelinePosition(value: String): TimelinePosition = value.unsafeCast<TimelinePosition>()

/** Wraps a plain number as a [StaggerValue]. */
fun StaggerValue(value: Double): StaggerValue = value.unsafeCast<StaggerValue>()

/** Wraps a string as a [StaggerValue]. */
fun StaggerValue(value: String): StaggerValue = value.unsafeCast<StaggerValue>()

/** Wraps a `[from, to]` range as a [StaggerValue]. */
fun StaggerValue(range: List<Double>): StaggerValue = range.unsafeCast<StaggerValue>()

/** Wraps a plain number as a [StaggerFrom]. */
fun StaggerFrom(index: Double): StaggerFrom = index.unsafeCast<StaggerFrom>()

/** Wraps one of `"first"`, `"center"`, `"last"`, `"random"` as a [StaggerFrom]. */
fun StaggerFrom(keyword: String): StaggerFrom = keyword.unsafeCast<StaggerFrom>()

/** Wraps an array of grid coordinates as a [StaggerFrom]. */
fun StaggerFrom(indexes: Array<Double>): StaggerFrom = indexes.unsafeCast<StaggerFrom>()

/** Wraps a boolean as a [StaggerGrid]. */
fun StaggerGrid(enabled: Boolean): StaggerGrid = enabled.unsafeCast<StaggerGrid>()

/** Wraps the grid dimensions as a [StaggerGrid]. */
fun StaggerGrid(columns: Int, rows: Int): StaggerGrid =
    arrayOf(columns.toDouble(), rows.toDouble()).unsafeCast<StaggerGrid>()
