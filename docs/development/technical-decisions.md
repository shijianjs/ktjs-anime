# Technical decisions

Decisions taken while wrapping `animejs` 4.5.0. Each one is either forced by the upstream
declarations or by a Kotlin/JS limitation; none of them is incidental.

## 1. The module is bound as one namespace object, not as named imports

anime.js 4 has named exports and no default export. Kotlin/JS compiles an `@JsModule` + `@JsName`
*declaration* into a **default** import (`import x from 'animejs'`), which webpack rejects with
`export 'default' … was not found`. An `@JsModule` external *object* becomes a namespace import
(`import * as animejs from 'animejs'`), so every entry point lives on `AnimeNamespace` and the
top-level functions (`animate`, `createTimeline`, …) delegate to it.

## 2. Optional parameter objects become real zero-argument overloads

`Timer`'s constructor is `constructor(parameters = {})`: it destructures the object it receives.
Forwarding `null` would therefore throw instead of falling back to the defaults. `createTimeline()`
and `createTimer()` are separate members bound to the same JavaScript function with **zero**
arguments (overloads inside `AnimeNamespace`), and `Easings` mirrors that for `spring()`,
`cubicBezier()`, `steps()` and `irregular()`.

## 3. Accessor pairs become `var`

`Timer` declares `set progress(v)` / `get progress()` pairs. In JavaScript that *is* one property,
so the Kotlin side uses `var progress: Double`. Read-only getters stay `val`.

## 4. `Timer.stretch` / `revert` / `then` are `open`

`JSAnimation` and `Timeline` override them to return their own type. `external` members are
`final` by default in Kotlin, so the base declarations are explicitly `open`.

## 5. Animated properties use `property(name, value)`

`AnimationParams` upstream is an index signature (`Record<string, TweenOptions | …> & TimerOptions
& …`). Kotlin external interfaces cannot express an index signature, so the fixed options are typed
fields and every animated property goes through the `AnimationParams.property` extension, which
assigns through a one-line `js("target[name] = value")` shim. `property` has overloads for `Double`,
`String`, `JsAny` and a per-property `TweenKeyValue`.

## 6. Union types are opaque interfaces with same-name factories

`TargetsParam` (selector string / element / target / array), `Target`, `EasingParam`,
`TimelinePosition`, `TweenParamValue`, `TimerId`, `StaggerFrom`, `StaggerGrid` and `StaggerValue`
each have one factory per upstream branch. No field widens to `Any` for convenience.

## 7. Deliberately loose types

| Member | Kotlin type | Reason |
| --- | --- | --- |
| `AnimationParams.keyframes` | `JsAny` | upstream is `PercentageKeyframes \| DurationKeyframes`; two different record shapes |
| `AnimationParams.modifier`, `StaggerParams.modifier`, `DefaultsParams.modifier` | `(Double) -> JsAny` | upstream returns `number \| string` |
| `TimerParams.loop`, `autoplay` | `JsAny` | upstream mixes `boolean \| number` with `ScrollObserver` |
| `StaggerParams.use`, `jitter`, `seed` | `JsAny` | upstream is `string \| function`, `number \| [number, number]`, `boolean \| number` |
| `utils.randomPick`, `utils.shuffle`, `utils.get`, `utils.remove` | `JsAny` / `Array<JsAny>` | upstream is generic over the item type |
| `Timer.seek`, `complete` optional flags | `JsAny?` | upstream is `boolean \| number` |

Every row is a boundary of the upstream `any`/union, not a shortcut: shrinking one of them means
adding the corresponding declared union.

## 8. `anime.utils` argument order follows the implementation, not the docs

`clamp(v, min, max)`, `round(v, decimalLength)`, `snap(v, increment)`, `wrap(v, min, max)`,
`mapRange(value, inLow, inHigh, outLow, outHigh)`, `damp(start, end, deltaTime, factor)`. The d.ts
file re-exports these from `core/helpers.js`, so the implementation is the source of truth; the
tests assert `clamp(120, 0, 100) == 100` to pin the order.

## 9. Tests run in Chromium against the real package

The jsTest suite creates real elements, drives `progress`, and reads back `element.style.transform`
and `style.opacity`; it also compares `utils` and `eases` results to the numbers the library
computes. A wrapper that compiles but binds the wrong module fails these assertions.

## 10. The demo is a Kotlin/JS page, not a Docusaurus page

`demo` is its own module with its own `main`, built to `dist/js/productionExecutable` and copied
under `/demo/` by `pagesSite`. Its Playwright suite (`:demo:e2eTest`) checks the rendered page,
the animation actually producing a transform, and the language switch flipping both `h1` and
`html[lang]`.

## 11. The lock file is regenerated against the official registry

`kotlin-js-store/package-lock.json` is committed, so CI (which has no mirror) resolves exactly what
a local build resolves. Adding an npm dependency therefore requires
`gradlew --stop` followed by `kotlinUpgradePackageLock` with `npm_config_registry` pointing at
`https://registry.npmjs.org`; the mirror in `~/.gradle/init.d` only affects Maven artifacts.

## 12. Localised documentation lives directly under `current/`

Docusaurus resolves a translated doc by its **doc id**, so
`i18n/zh-Hans/docusaurus-plugin-content-docs/current/guide/api-overview.md` is the only correct
location for `guide/api-overview`. An extra `docs/` segment silently falls back to the English
document, which is why the docs generator strips it.
