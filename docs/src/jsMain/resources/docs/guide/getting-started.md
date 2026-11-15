---
sidebar_position: 2
title: Getting started
---

# Getting started

## Dependency

```kotlin
kotlin {
    js {
        binaries.executable()
        browser()
    }
    sourceSets {
        jsMain.dependencies {
            implementation("io.github.shijianjs:ktjs-anime:4.5.0-kt.1")
            // The public API exposes `kotlin-browser`'s `web.*` types, so a consumer must be able to
            // compile against them.
            implementation("org.jetbrains.kotlin-wrappers:kotlin-browser:2026.11.1")
        }
    }
}
```

Kotlin 2.4.21 or newer; the module emits ESM and targets `es2015` or later.

## Register the plugins

anime.js 4 has no plugin registry: everything is a named export of the `animejs` module. Kotlin/JS cannot bind named exports directly, so the wrapper declares the module as one external object (`AnimeNamespace`) and the public functions (`animate`, `createTimeline`, …) delegate to it. Importing `io.github.shijianjs.ktjs.anime.animate` is therefore enough — there is nothing to register.

## First call

```kotlin
import io.github.shijianjs.ktjs.anime.animate
import io.github.shijianjs.ktjs.anime.animationParams
import io.github.shijianjs.ktjs.anime.core.EasingParam
import io.github.shijianjs.ktjs.anime.core.Target
import io.github.shijianjs.ktjs.anime.core.TargetsParam
import io.github.shijianjs.ktjs.anime.core.TweenParamValue
import io.github.shijianjs.ktjs.anime.core.property

fun slideIn(element: web.dom.Element) {
    animate(
        TargetsParam(Target(element)),
        animationParams {
            property("translateX", 240.0)
            property("opacity", 1.0)
            duration = TweenParamValue(800.0)
            ease = EasingParam("outQuad")
        },
    )
}
```

Animated properties are set with `property(name, value)` because the upstream `AnimationParams`
is an index signature; every fixed option (`duration`, `ease`, `loop`, callbacks, …) is a typed
field of the same object.

## Build and test

```bash
./gradlew build                     # compile, Detekt, browser tests
./gradlew :ktjs-anime:jsBrowserTest     # only the wrapper's browser tests
./gradlew :docs:docusaurusStart     # documentation dev server
./gradlew pagesSite                 # docs at /, demo at /demo/, Dokka at /api/
```
