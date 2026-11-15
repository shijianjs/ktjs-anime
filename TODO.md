# TODO

## ktjs-anime 上游 API 对齐

把上游 `animejs` 4.5.0-kt.1 的类型声明逐个落成 Kotlin/JS 声明，并让 demo、文档、
测试与之一致。

- 总计：23
- 已完成：7
- 明确不做：0
- 待完成：16

详细清单：[coverage.md](docs/development/coverage.md)
技术决策：[technical-decisions.md](docs/development/technical-decisions.md)

### 当前待办

- [ ] `createAnimatable` 及其 `Animatable` 工厂（编号插值）
- [ ] `createDraggable` 与 `DraggableParams`
- [ ] `createScope` 与 `ScopeParams`
- [ ] `anime.svg`（`createDrawable`、`createMotionPath`、`morphTo`）
- [ ] `anime.text`（`createSplitText`、`scrambleText`）
- [ ] `waapi`（`waapi.animate`、`waapi.createTimeline`）
- [ ] `ScrollObserver`（`onScroll`）与 `events` 命名空间
- [ ] `layout`（`AutoLayout`）与 `engine`（`engine` 对象）
- [ ] `adapters/three`
- [ ] 百分比关键帧与时长关键帧的类型化建模
- [ ] `utils.snap` 的数组分支、`utils.get`/`set` 的重载收窄
- [ ] `Timeline.add` 的 `TweakRegister` 分支
- [ ] `stagger` 的 `use` 方法与 `jitter`/`seed` 的类型化
- [ ] `eases` 剩余缓动（`outIn*` 系列、`inOutElastic` 等）
- [ ] 测试覆盖 `seek`、`alternate`、`revert` 的时序语义
- [ ] 上游升级后的声明对齐

### 注意事项

- 只封装 MIT 授权的 `animejs` 4 动画核心；未覆盖的导出项列在 coverage.md，不假装完成。
- 可选参数用重载而非 `null`：anime.js 的参数对象默认是 `{}`。
- 有意放宽的类型逐条记在 `technical-decisions.md`，不得悄悄退回 `JsAny`。
