<img src="screenshots/jetchatlogo.png"/>

# Jetchat sample

Jetchat is a sample chat app built with [Jetpack Compose][compose].

To try out this sample app, use the latest stable version
of [Android Studio](https://developer.android.com/studio).
You can clone this repository or import the
project from Android Studio following the steps
[here](https://developer.android.com/jetpack/compose/setup#sample).

This sample showcases:

* UI state management
* Integration with Architecture Components: Navigation, Fragments, ViewModel
* Back button handling
* Text Input and focus management
* Multiple types of animations and transitions
* Saved state across configuration changes
* Material Design 3 theming and Material You dynamic color
* UI tests

## Screenshots

<img src="screenshots/screenshots.png"/>

<img src="screenshots/widget.png" width="300"/>

<img src="screenshots/widget_discoverability.png" width="300"/>

### Status: 🚧 In progress

Jetchat is still in under development, and some features are not yet implemented.

## Features

### UI State management
The [ConversationContent](app/src/main/java/com/example/compose/jetchat/conversation/Conversation.kt) composable is the entry point to this screen and takes a [ConversationUiState](app/src/main/java/com/example/compose/jetchat/conversation/ConversationUiState.kt) that defines the data to be displayed. This doesn't mean all the state is served from a single point: composables can have their own state too. For an example, see `scrollState` in [ConversationContent](app/src/main/java/com/example/compose/jetchat/conversation/Conversation.kt) or `currentInputSelector` in [UserInput](app/src/main/java/com/example/compose/jetchat/conversation/UserInput.kt)

### Architecture Components
The [ProfileFragment](app/src/main/java/com/example/compose/jetchat/profile/ProfileFragment.kt) shows how to pass data between fragments with the [Navigation component](https://developer.android.com/guide/navigation) and observe state from a
[ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel), served via [LiveData](https://developer.android.com/topic/libraries/architecture/livedata).

### Back button handling
When the Emoji selector is shown, pressing back in the app closes it, intercepting any navigation events. The implementation can be found in [UserInput](app/src/main/java/com/example/compose/jetchat/conversation/UserInput.kt).

### Text Input and focus management
When the Emoji panel is shown the keyboard must be hidden and vice versa. This is achieved with a combination of the [FocusRequester](https://developer.android.com/reference/kotlin/androidx/compose/ui/focus/FocusRequester) and [onFocusChanged](https://developer.android.com/reference/kotlin/androidx/compose/ui/focus/package-summary#(androidx.compose.ui.Modifier).onFocusChanged(kotlin.Function1)) APIs.

### Multiple types of animations and transitions
This sample uses animations ranging from simple `AnimatedVisibility` in [FunctionalityNotAvailablePanel](app/src/main/java/com/example/compose/jetchat/conversation/UserInput.kt) to choreographed transitions found in the [FloatingActionButton](https://material.io/develop/android/components/floating-action-button) of the Profile screen and implemented in [AnimatingFabContent](app/src/main/java/com/example/compose/jetchat/conversation/UserInput.kt)

### Edge-to-edge UI with synchronized IME transitions
This sample is laid out [edge-to-edge](https://medium.com/androiddevelopers/gesture-navigation-going-edge-to-edge-812f62e4e83e), drawing its content behind the system bars for a more immersive look.

The sample also supports synchronized IME transitions when running on API 30+ devices. See the use of `Modifier.navigationBarsPadding().imePadding()` in [ConversationContent](app/src/main/java/com/example/compose/jetchat/conversation/UserInput.kt).

### Saved state across configuration changes
Some composable state survives activity or process recreation, like `currentInputSelector` in [UserInput](app/src/main/java/com/example/compose/jetchat/conversation/UserInput.kt).

### Material Design 3 theming and Material You dynamic color
Jetchat follows the [Material Design 3](https://m3.material.io) principles and uses the `MaterialTheme` composable and M3 components. On Android 12+ Jetchat supports Material You dynamic color, which extracts a custom color scheme from the device wallpaper. Jetchat uses a custom, branded color scheme as a fallback. It also implements custom typography using the Karla and Montserrat font families.

### Nested scrolling interop
Jetchat contains an example of how to use [`rememberNestedScrollInteropConnection()`](https://developer.android.com/reference/kotlin/androidx/compose/ui/platform/package-summary#rememberNestedScrollInteropConnection()) to achieve successful nested scroll interop between a View parent that implements `androidx.core.view.NestedScrollingParent3` and a Compose child. The example used here is a combination of a View parent `CoordinatorLayout` and a nested, Compose child `BoxWithConstraints` in [ProfileFragment](app/src/main/java/com/example/compose/jetchat/profile/ProfileFragment.kt). 

### UI tests
In [androidTest](app/src/androidTest/java/com/example/compose/jetchat) you'll find a suite of UI tests that showcase interesting patterns in Compose:

#### [ConversationTest](app/src/androidTest/java/com/example/compose/jetchat/ConversationTest.kt)
UI tests for the Conversation screen. Includes a test that checks the behavior of the app when dark mode changes.

#### [NavigationTest](app/src/androidTest/java/com/example/compose/jetchat/NavigationTest.kt)
Shows how to write tests that assert directly on the [Navigation Controller](https://developer.android.com/reference/androidx/navigation/NavController).

#### [UserInputTest](app/src/androidTest/java/com/example/compose/jetchat/UserInputTest.kt)
Checks that the user input composable, including extended controls, behave as expected showing and hiding the keyboard.


## Known issues
1. If the emoji selector is shown, clicking on the TextField can sometimes show both input methods.
Tracked in https://issuetracker.google.com/164859446

2. There are only two profiles, clicking on anybody except "me" will show the same data.

## License
```
Copyright 2020 The Android Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

[compose]: https://developer.android.com/jetpack/compose



### 状态：🚧 进行中

Jetchat 仍在开发中，部分功能尚未实现。

## 特性

### 用户界面状态管理

[ConversationContent](app/src/main/java/com/example/compose/jetchat/conversation/Conversation.kt) 可组合项是此屏幕的入口点，它接收一个 [ConversationUiState](app/src/main/java/com/example/compose/jetchat/conversation/ConversationUiState.kt) 对象，该对象定义了要显示的数据。但这并不意味着所有状态都来自单一来源：可组合项也可以有自己的状态。例如，可以查看 [ConversationContent](app/src/main/java/com/example/compose/jetchat/conversation/Conversation.kt) 中的 `scrollState` 或 [UserInput](app/src/main/java/com/example/compose/jetchat/conversation/UserInput.kt) 中的 `currentInputSelector` 。

### 架构组件

[ProfileFragment](app/src/main/java/com/example/compose/jetchat/profile/ProfileFragment.kt) 展示了如何使用 [Navigation 组件](https://developer.android.com/guide/navigation) 在片段之间传递数据，并通过 [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) 观察由 [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) 提供的状态。

### 返回按钮处理

当表情符号选择器显示时，在应用中按返回键会将其关闭，从而拦截任何导航事件。该实现可在 [UserInput](app/src/main/java/com/example/compose/jetchat/conversation/UserInput.kt) 中找到。

### 文本输入与焦点管理

当表情符号面板显示时，键盘必须隐藏，反之亦然。这是通过结合使用 [FocusRequester](https://developer.android.com/reference/kotlin/androidx/compose/ui/focus/FocusRequester) 和 [onFocusChanged](https://developer.android.com/reference/kotlin/androidx/compose/ui/focus/package-summary#(androidx.compose.ui.Modifier).onFocusChanged(kotlin.Function1)) API 实现的。

多种类型的动画和过渡效果

此示例使用了多种动画，从 [FunctionalityNotAvailablePanel](app/src/main/java/com/example/compose/jetchat/conversation/UserInput.kt) 中简单的 `AnimatedVisibility` 到 [FloatingActionButton](https://material.io/develop/android/components/floating-action-button) 中精心编排的过渡动画，这些过渡动画在 [AnimatingFabContent](app/src/main/java/com/example/compose/jetchat/conversation/UserInput.kt) 中得以实现。

全屏无边距用户界面，搭配输入法同步过渡效果

此示例采用 [边缘到边缘](https://medium.com/androiddevelopers/gesture-navigation-going-edge-to-edge-812f62e4e83e) 布局，其内容绘制在系统栏之后，以实现更具沉浸感的外观。

该示例还支持在 API 30 及以上版本的设备上运行时同步输入法的过渡。请参阅 [ConversationContent](app/src/main/java/com/example/compose/jetchat/conversation/UserInput.kt) 中对 `Modifier.navigationBarsPadding().imePadding()` 的使用。

### 配置更改时保存的状态

某些可组合的状态在活动或进程重建后仍能保留，例如 [UserInput](app/src/main/java/com/example/compose/jetchat/conversation/UserInput.kt) 中的 `currentInputSelector` 。

Material Design 3 主题和 Material You 动态色彩

Jetchat 遵循 [Material Design 3](https://m3.material.io) 原则，并使用 `MaterialTheme` 可组合组件和 M3 组件。在 Android 12 及以上版本中，Jetchat 支持 Material You 动态颜色，该功能会从设备壁纸中提取自定义配色方案。Jetchat 还使用了自定义的品牌配色方案作为备用方案。此外，它还使用了 Karla 和 Montserrat 字体家族实现了自定义排版。

嵌套滚动互操作

Jetchat 包含了一个使用 [`rememberNestedScrollInteropConnection()`](https://developer.android.com/reference/kotlin/androidx/compose/ui/platform/package-summary#rememberNestedScrollInteropConnection()) 实现视图父级（实现了 `androidx.core.view.NestedScrollingParent3`）与 Compose 子级之间成功嵌套滚动交互的示例。这里使用的示例是一个视图父级 `CoordinatorLayout` 和一个嵌套的 Compose 子级 `BoxWithConstraints` 的组合，位于 [ProfileFragment](app/src/main/java/com/example/compose/jetchat/profile/ProfileFragment.kt) 中。

