---
sidebar_position: 2
title: 快速开始
---

# 快速开始

## 依赖

```kotlin
kotlin {
    js {
        binaries.executable()
        browser()
    }
    sourceSets {
        jsMain.dependencies {
            implementation("io.github.shijianjs:ktjs-anime:4.5.0-kt.1")
            // `kotlin-browser` 的 `web.*` 类型出现在公开 API 里，所以消费方要能编译到它们。
            implementation("org.jetbrains.kotlin-wrappers:kotlin-browser:2026.11.1")
        }
    }
}
```

要求 Kotlin 2.4.21 或更新版本；wrapper 输出 ESM，`es2015` 目标起可用。

## 注册插件

anime.js 4 没有插件注册表：一切都是 `animejs` 模块的具名导出。Kotlin/JS 不能直接绑定具名导出，所以 wrapper 把模块声明成一个 external object（`AnimeNamespace`），公开函数（`animate`、`createTimeline` 等）转发给它。因此导入 `io.github.shijianjs.ktjs.anime.animate` 就够了 —— 没有需要注册的东西。

## 第一个调用

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

被动画的属性用 `property(name, value)` 设置 —— 上游 `AnimationParams` 是索引签名；所有固定选项
（`duration`、`ease`、`loop`、回调等）都是同一对象上的带类型字段。

## 构建与测试

```bash
./gradlew build                     # 编译、Detekt、浏览器测试
./gradlew :ktjs-anime:jsBrowserTest     # 只跑 wrapper 的浏览器测试
./gradlew :docs:docusaurusStart     # 文档开发服务器
./gradlew pagesSite                 # 文档在 /，演示在 /demo/，Dokka 在 /api/
```
