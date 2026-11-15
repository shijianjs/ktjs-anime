# ktjs-anime

anime.js 4 的 Kotlin/JS 绑定 —— 动画、时间线、定时器、缓动与工具函数

封装覆盖 `animejs` 4 的动画核心：`animate`、`createTimeline`、`createTimer`、`stagger`、`Timer`/`JSAnimation`/`Timeline` 层次及其带类型的参数对象、`anime.utils` 与 `anime.easings`。上游的可选参数变成真正的零参数重载 —— anime.js 默认解构 `{}`，传 `null` 会抛错。

- 上游包：[`animejs`](https://github.com/juliangarnier/anime) 4.5.0
- 文档：https://shijianjs.github.io/ktjs-anime/
- 演示：https://shijianjs.github.io/ktjs-anime/demo/
- API：https://shijianjs.github.io/ktjs-anime/api/
- 英文说明：[README.md](README.md)

## 用法

```kotlin
implementation("io.github.shijianjs:ktjs-anime:4.5.0-kt.1")
```

## 构建

```bash
./gradlew build
./gradlew :ktjs-anime:jsBrowserTest
./gradlew :demo:e2eTest
./gradlew pagesSite
```

## 许可证

wrapper 与上游 `animejs` 同为 MIT；版权声明见文档的「许可证」页。
