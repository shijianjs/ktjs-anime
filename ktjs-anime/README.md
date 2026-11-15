# ktjs-anime (module)

Kotlin/JS wrapper for [`animejs`](https://www.npmjs.com/package/animejs) 4.5.0.

One npm package, one Gradle module, one Kotlin package
(`io.github.shijianjs.ktjs.anime`) with sub-packages `core`, `utils` and `easings`.

## Installation

```kotlin
dependencies {
    implementation("io.github.shijianjs:ktjs-anime:4.5.0-kt.1")
}
```

## Usage

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
            duration = TweenParamValue(800.0)
            ease = EasingParam("outQuad")
        },
    )
}
```

- Animated properties are index-signature keys upstream, so they are set with `property(name, value)`.
- Every fixed option is a typed field of the same params object.
- `createTimeline()` / `createTimer()` are real zero-argument overloads: anime.js destructures its
  parameter object with `= {}`, so forwarding `null` would throw.

## Build and test

```bash
./gradlew :ktjs-anime:jsBrowserTest
```

After adding or upgrading an npm dependency, refresh the shared lock file with the official registry
(the lock file is committed, and CI has no mirror):

```bash
./gradlew --stop
$env:npm_config_registry='https://registry.npmjs.org'
./gradlew kotlinUpgradePackageLock
```

## License

MIT, same as the upstream `animejs` package (Copyright (c) 2017 Julian Garnier).
