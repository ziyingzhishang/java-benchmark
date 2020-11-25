# java-benchmark

#### java 基准测试介绍
{**以下是关于 JAVA 基准测试的一些基本概念**}

JMH (Java Microbenchmark Hardness) 微基准测试；
> 在微基准测试中我们希望看到的程序执行效率是 JVM 直接执行机器码的速度；因此，在执行真正的微基准测试之前需要对代码进行预热
> （先执行几次，JVM 就会生成机器码），这也就是 JMH 提供的"预热处理"。

#### 项目结构
｜- officia  // OpenJDK 官方的测试 demo 供参考
｜- test     // 自己在平时学习时加入的基准测试； 

#### 使用说明
> 在 IDEA 中执行基准测试时，使用 `Run` 的方式，不能使用 `Debug`；
> 执行报错，最好执行一次 `mvn clean` 再进行尝试；

#### 相关文章
[JMH - Java Microbenchmark Harness](http://tutorials.jenkov.com/java-performance/jmh.html)
[JMH - OpenJDK](http://openjdk.java.net/projects/code-tools/jmh/)

[JMH 插件](https://github.com/artyushov/idea-jmh-plugin)

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request

