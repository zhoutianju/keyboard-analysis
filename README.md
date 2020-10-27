# keyboard-analysis 键盘使用习惯分析

<img src="https://img.shields.io/badge/JDK-1.8+-green.svg" alt=""> <img src="https://img.shields.io/badge/Maven-3.0+-green.svg" alt=""> <img src="https://img.shields.io/badge/Platform-Windows-green.svg" alt=""> <img src="https://img.shields.io/badge/Browser-Chrome-green.svg" alt="">

## 背景

起初想买一个键盘日常写代码用，犹豫买 87 键还是 104 键（带小键盘），于是写了这个程序统计下买键盘前（用笔记本）日常各按键的使用频率。

## 安装 & 使用

**仅支持 Windows 系统！**

```bash
# 安装
git clone git@github.com:zhoutianju/keyboard-analysis.git
cd keyboard-analysis
mvn clean compile assembly:single

# 开始记录
java -jar target/exec.jar record

# 重置历史，重新记录
java -jar target/exec.jar reset

# 打开当前记录的分析结果
java -jar target/exec.jar analysis
```

* `record`、`reset` 让程序进入记录状态，会保持前台运行
    * 记录状态中，会监控全局的（在任何软件下的）按键事件，并打印在控制台
    * 记录状态中，每记录 100 个字符，会自动保存按键记录文件
* `Ctrl + C` 终止记录状态，并保存按键记录文件（Windows CMD 可正常触发，Git-bash 无法触发）
* `record` 每次启动会先加载历史保存的按键记录文件
* `reset` 会清除历史按键记录，重新开始记录
* `analysis` 会打开一个新的 Chrome 标签页，显示本次记录的键盘热力图

## 技术栈

* JDK 1.8
* Windows 平台 JNA 接口中提供的 Windows 消息 SDK
* [开源键盘热力图前端代码](http://github.com/pa7/Keyboard-Heatmap.git) 原作者：Patrick Wied

## 关于最终的键盘选择

程序跑了大概一周左右，我自己的统计结果中使用数字键的频率其实很低，但还是感觉用小键盘输手机号、IP 地址的时候很爽，所以最后买了 104 键的（plum 的国产静电容，用了三年手感不错，推荐一下）。

**有时候即使有再多的客观数据支撑，我们最终某些决定还是会靠主观喜好。** 🤦