# java-benchmark

#### java 基准测试介绍
{**以下是关于 JAVA 基准测试的一些基本概念**}

JMH (Java Microbenchmark Hardness) 微基准测试，专门用于代码微基准测试的工具套件；主要用于基于方法层面的微基准测试
精度可达到纳秒级别。

> 使用场景：当定位到热点方法时，希望进一步优化性能，就可以使用微基准测试；<br>
> 预热处理：在微基准测试中我们希望看到的程序执行效率是 JVM 直接执行机器码的速度；因此，在执行真正的微基准测试之前需要对代码进行预热
> （先执行几次，JVM 就会生成机器码），这也就是 JMH 提供的"预热处理"。

#### 项目结构
｜- officia  // OpenJDK 官方的测试 demo 供参考
｜- test     // 自己在平时学习时加入的基准测试；（字符串拼接，列表，序列化等）

#### 使用说明
> 在 IDEA 中执行基准测试时，使用 `Run` 的方式，不能使用 `Debug`；<br>
> 执行报错，最好执行一次 `mvn clean` 再进行尝试；

##### 基础概念
* OPS 每秒操作量，衡量性能的重要指标；
* QPS 每秒查询量，对每次执行的时间进行升序排列，取总数的 99% 的最大执行时间作为 T99，表示 99% 的请求响应不超过某个值；

##### 常用注解
* BenchmarkMode 使用模式；
    * Mode.Throughput : 吞吐量；
    * Mode.AverageTime: 响应时间；
    * Mode.SampleTime: 采样时间了；
    * Mode.All: 表示统计以上所有指标；
* Warmup: 预热， 默认预热1次；
* Measurement: 配置执行次数（Iterator）和执行时间/每次（time）
* Thread：同时多少个线程执行；
* Fork： 多个单独进程，分别测试每个方法；（每个方法多少进程）
* OutputTimeUnit：统计结果的时间单元；


#### 相关文章
[JMH - Java Microbenchmark Harness](http://tutorials.jenkov.com/java-performance/jmh.html)

[JMH - OpenJDK](http://openjdk.java.net/projects/code-tools/jmh/)

[JMH 插件](https://github.com/artyushov/idea-jmh-plugin)

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request

