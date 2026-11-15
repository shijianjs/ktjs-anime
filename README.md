# ktjs-anime

Kotlin/JS bindings for anime.js 4 — animations, timelines, timers, easing and utils

The wrapper covers the animation core of `animejs` 4: `animate`, `createTimeline`, `createTimer`, `stagger`, the `Timer`/`JSAnimation`/`Timeline` hierarchy with typed parameter objects, `anime.utils` and `anime.easings`. Optional upstream arguments become real zero-argument overloads, because anime.js destructures `{}` and would throw on `null`.

- Upstream package: [`animejs`](https://github.com/juliangarnier/anime) 4.5.0
- Docs: https://shijianjs.github.io/ktjs-anime/
- Demo: https://shijianjs.github.io/ktjs-anime/demo/
- API: https://shijianjs.github.io/ktjs-anime/api/
- 中文说明：[README.zh-CN.md](README.zh-CN.md)

## Usage

```kotlin
implementation("io.github.shijianjs:ktjs-anime:4.5.0-kt.1")
```

## Build

```bash
./gradlew build
./gradlew :ktjs-anime:jsBrowserTest
./gradlew :demo:e2eTest
./gradlew pagesSite
```

## License

The wrapper is MIT, same as the upstream `animejs` — see the "License" page of the docs.
