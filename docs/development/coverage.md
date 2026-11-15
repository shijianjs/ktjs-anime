# API Coverage

## 来源与范围

- 来源：`animejs` 4.5.0（npm），源码克隆 `S:\workspace\github\juliangarnier\anime`
- 声明文件：`dist/modules/index.d.ts` 及其子模块（`timer`、`animation`、`timeline`、`animatable`、
  `draggable`、`scope`、`events`、`engine`、`easings`、`layout`、`utils`、`svg`、`text`、`waapi`、
  `types`）
- 核对日期：2026-11-16
- 范围：`animejs` 的公开导出（具名导出 `animate`、`createTimeline`、`createTimer`、`stagger`、
  `utils`、`easings`，以及 `types` 里的公开类型）。按功能组统计，一组算一项。
- 不纳入统计：`adapters/three` 的内部文件（`instance.d.ts`、`resolvers.d.ts` 等），它们不是
  `index.d.ts` 的导出入口。

## 统计

| 状态 | 数量 |
|---|---:|
| 总计 | 23 |
| 已完成 | 7 |
| 明确不做 | 0 |
| 待完成 | 16 |

已完成组（不逐项列名，实现见源码）：

1. `animate` + `JSAnimation`（`An.kt`、`core/Animation.kt`）
2. `createTimeline` + `Timeline`（`core/Timeline.kt`）
3. `createTimer` + `Timer`（`core/Timer.kt`）
4. `stagger` + `StaggerParams`（`Anime.kt`、`core/Timer.kt`）
5. `anime.utils`（16 个函数，`utils/Utils.kt`）
6. `anime.easings`（`spring`、`cubicBezier`、`steps`、`linear`、`irregular` + `eases`，`easings/Easings.kt`）
7. 公开类型建模（`TargetsParam`、`Target`、`EasingParam`、`TimelinePosition`、`TweenParamValue`、
  `TimerId`、`StaggerFrom`、`StaggerGrid`、`StaggerValue`、`DefaultsParams`、`TweenKeyValue`，
  `core/Types.kt`）

## 明确不做

无。本轮没有把任何导出项判定为「不在范围内」：未完成的都保持待完成状态。

## 待完成

### 创建器

- [ ] `createAnimatable` 及其 `Animatable` 工厂（编号插值）
- [ ] `createDraggable` 与 `DraggableParams`
- [ ] `createScope` 与 `ScopeParams`

### 命名空间

- [ ] `anime.svg`（`createDrawable`、`createMotionPath`、`morphTo`）
- [ ] `anime.text`（`createSplitText`、`scrambleText`、`ScrambleTextParams`）
- [ ] `waapi`（`waapi.animate`、`waapi.createTimeline`、`WAAPIAnimation`）
- [ ] `ScrollObserver`（`onScroll`）与 `events` 命名空间
- [ ] `layout`（`AutoLayout`）与 `engine`（`engine` 对象）
- [ ] `adapters/three`

### 类型收窄

- [ ] 百分比关键帧（`PercentageKeyframes`）与时长关键帧（`DurationKeyframes`）的类型化建模
- [ ] `utils.snap` 的数组分支、`utils.get` / `set` 的重载收窄
- [ ] `Timeline.add` 的 `TweakRegister` 分支
- [ ] `stagger` 的 `use` 方法与 `jitter` / `seed` 的类型化
- [ ] `eases` 剩余缓动（`outIn*` 系列、`inOutElastic` 等）

### 验证

- [ ] 测试覆盖 `seek`、`alternate`、`revert` 的时序语义
- [ ] 上游升级后的声明对齐

## 验证依据

| 命令 | 结果 |
|---|---|
| `./gradlew :ktjs-anime:compileKotlinJs` | 通过 |
| `./gradlew :ktjs-anime:jsBrowserTest` | 8 个用例全部通过（Chromium） |
| `./gradlew :demo:e2eTest` | 通过（Playwright，含语言切换） |
| `./gradlew :docs:docusaurusBuild` | en 与 zh-Hans 均成功 |
