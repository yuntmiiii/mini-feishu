# Mini-feishu

## 项目概述

这是一个基于 Jetpack Compose 开发的 Android 客户端应用程序。Jetpack Compose 是 Android 推荐的现代化 UI 工具包，使用声明式 UI 设计模式，简化了 Android UI 开发流程。

## 技术栈

- **Jetpack Compose** - 声明式 UI 框架
- **Kotlin** - 主要开发语言
- **MVVM 架构** - 应用架构模式
- **Hilt** - 依赖注入
- **Retrofit** - 网络请求
- **Room** - 本地数据库
- **Coroutines & Flow** - 异步处理

## 安装与运行

### 前提条件

- Android Studio Arctic Fox (2020.3.1) 或更高版本
- JDK 11 或更高版本
- Android SDK 31 或更高版本

### 构建步骤

1. 克隆仓库到本地：
   ```
   git clone https://github.com/afkdsghk211331/mini-feishu.git
   ```

2. 在 Android Studio 中打开项目

3. 同步 Gradle 文件

4. 运行应用：
   - 选择目标设备（真机或模拟器）
   - 点击运行按钮

## 开发指南

### Compose 组件创建规范

- 所有 Composable 函数使用大驼峰命名法并以 `Screen` 或 `Component` 结尾
- 私有 Composable 函数使用 `private` 修饰符
- 为每个 Composable 创建对应的预览函数

### 状态管理

- 使用 ViewModel 管理 UI 状态
- 使用 `mutableStateOf`、`remember` 和 `collectAsState` 管理本地状态
- 复杂页面使用 `State` 和 `Event` 模式


## 贡献指南

1. Fork 项目
2. 创建功能分支 (`git checkout -b feature/amazing-feature`)
3. 提交更改 (`git commit -m 'Add some amazing feature'`)
4. 推送到分支 (`git push origin feature/amazing-feature`)
5. 开启 Pull Request
