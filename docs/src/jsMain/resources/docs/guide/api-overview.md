---
sidebar_position: 3
title: API overview
---

# API overview

## Package layout

| Package | Contents |
| --- | --- |
| `io.github.shijianjs.ktjs.anime` | the `AnimeNamespace` import, `animate`, `createTimeline`, `createTimer`, `stagger`, the params builders `animationParams` / `timelineParams` / `timerParams` / `staggerParams` |
| `…anime.core` | `Timer`, `JSAnimation`, `Timeline`, their parameter interfaces (`TimerParams`, `AnimationParams`, `TimelineParams`, `DefaultsParams`, `StaggerParams`), the union types (`TargetsParam`, `EasingParam`, `TimelinePosition`, `TweenParamValue`) and the `property` / `tweenKeyValue` helpers |
| `…anime.utils` | `anime.utils`: `clamp`, `round`, `snap`, `wrap`, `lerp`, `damp`, `mapRange`, `degToRad`, `radToDeg`, `random`, `randomPick`, `shuffle`, `get`, `set`, `remove`, `sync` |
| `…anime.easings` | `anime.easings` (`spring`, `cubicBezier`, `steps`, `linear`, `irregular`) and the `eases` set |

## Conventions this wrapper follows

- A union type is one opaque interface plus one factory per branch: `TargetsParam("#app")`,
  `TargetsParam(Target(element))`, `TargetsParam(arrayOf(…))`, `EasingParam("outQuad")`,
  `TimelinePosition(500.0)` / `TimelinePosition("+=200")`, `TweenParamValue(800.0)`.
- Parameter objects are `unsafeJso` builders. Animated property names are index-signature keys, so
  they go through `property(name, value)` instead of a Kotlin field.
- Optional upstream parameters are separate overloads rather than `null`: anime.js destructures its
  parameter object with `= {}`, and an explicit `null` throws. `createTimeline()` and
  `createTimer()` therefore bind the same JavaScript function with zero arguments.
- Members that upstream declares as accessor pairs are plain `var`s — in JavaScript a getter/setter
  pair *is* one property.
- `external` classes cannot redeclare a parent member with a narrower return type unless the parent
  member is `open`; `stretch`, `revert` and `then` are declared `open` on `Timer` so `JSAnimation`
  and `Timeline` can return themselves.

## What is deliberately typed loosely

| Member | Kotlin type | Why |
| --- | --- | --- |
| `AnimationParams.keyframes` | `JsAny` | upstream is `PercentageKeyframes \| DurationKeyframes`, a union of two record shapes |
| `AnimationParams.modifier` / `StaggerParams.modifier` | `(Double) -> JsAny` | upstream returns `number \| string` |
| `TimerParams.loop` / `autoplay` / `StaggerParams.seed` | `JsAny` | upstream mixes a literal union with a callback serializer |
| `utils.randomPick` / `shuffle` | `JsAny` / `Array<JsAny>` | upstream is generic over the item type |

Each of these is recorded in `docs/development/technical-decisions.md`.
