# java-benchmark

#### java 基准测试

JMH (Java Microbenchmark Hardness) 微基准测试，专门用于代码微基准测试的工具套件；主要用于基于方法层面的微基准测试
精度可达到纳秒级别。

> 使用场景：当定位到热点方法时，希望进一步优化性能，就可以使用微基准测试；<br>
> 预热处理：在微基准测试中我们希望看到的程序执行效率是 JVM 直接执行机器码的速度；因此，在执行真正的微基准测试之前需要对代码进行预热
> （先执行几次，JVM 就会生成机器码），这也就是 JMH 提供的"预热处理"。

#### 项目结构
｜- openjdk-officia  // OpenJDK 官方的测试 demo 供参考
｜- jmh-demo         // 自己在平时学习时加入的基准测试，包含 JMH 使用式样；（字符串拼接，列表，序列化等）
｜- mapperstruct     // 针对mapStruct 框架的对象映射做测试；
｜- spring-framework // 收录 spring-framework 官方的基准测试，也加入自己在开发过程中遇到的相关的测试；
｜- java-coroutines  // java 协程相关的基准测试

#### 使用说明
> 在 IDEA 中执行基准测试时，使用 `Run` 的方式，不能使用 `Debug`；<br>
> 执行报错，最好执行一次 `mvn clean` 再进行尝试；
> JMH 在编译的时候会对循环进行优化，如果发现某个变量没有被使用会被优化掉:
> ```java 
>   public class MyBenchmark {
>    
>        @Benchmark
>        public void testMethod() {
>            int a = 1;
>            int b = 2;
>            int sum = a + b;       // Dead Code
>        }
>    
>    }
> 
>  改为 return sum ， 这样就不会被编译器优化掉，从而测出比较准确的结果；
> ```

##### 基础概念
* OPS 每秒操作量，衡量性能的重要指标；
* QPS 每秒查询量，对每次执行的时间进行升序排列，取总数的 99% 的最大执行时间作为 T99，表示 99% 的请求响应不超过某个值；

##### 常用注解
* BenchmarkMode 使用模式；
    * Mode.Throughput : 吞吐量；
    * Mode.AverageTime: 响应时间；
    * Mode.SampleTime: 采样时间；
    * Mode.All: 表示统计以上所有指标；
* Warmup: 预热， 默认预热1次；
* Measurement: 配置执行次数（Iterator）和执行时间/每次（time）
* Thread：同时多少个线程执行；
* Fork： 多个单独进程，分别测试每个方法；（每个方法多少进程）
* OutputTimeUnit：统计结果的时间单元；

* State 对象的生命周期：`@State(Scope.Benchmark)` 
> 在类上使用，State 中注明某些变量是 Thread 生效或者整个测试都生效 Benchmark
```java
@State(Scope.Benchmark)
public class MyBenchmarkStateSimple {
  AreaService areaService = new AreaService();
  PreferAreaService perferAreaService = new PreferAreaService();
  List<Area> data = buildData(20);
  //忽略其他代码
}
```


#### 运行结果
```bash 
Result "com.lab.jmh.test.MyBatchmarkDemo.testBefore":
  144330.689 ±(99.9%) 3986.539 ops/s [Average]
  (min, avg, max) = (139887.020, 144330.689, 147419.834), stdev = 2636.848
  CI (99.9%): [140344.150, 148317.228] (assumes normal distribution)


# Run complete. Total time: 00:05:01

REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
experiments, perform baseline and negative tests that provide experimental control, make sure
the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
Do not assume the numbers tell you what you want them to tell.

Benchmark                    Mode  Cnt       Score       Error  Units
MyBatchmarkDemo.testAfter   thrpt   10  125335.652 ± 25823.147  ops/s
MyBatchmarkDemo.testBefore  thrpt   10  144330.689 ±  3986.539  ops/s
```
> stdev： 标准差，反应数值相对于平均值的离散程度；<br>
> CI（Corfindence interval）：置信区间，这里相当于处理能力的一个估计区间；在统计学中，置信区间反应了当前样本的某个总体
> 参数的区间估计；<br>

#### 相关文章
[JMH - Java Microbenchmark Harness](http://tutorials.jenkov.com/java-performance/jmh.html)

[JMH - OpenJDK](http://openjdk.java.net/projects/code-tools/jmh/)

[JMH 插件](https://github.com/artyushov/idea-jmh-plugin)

[Java性能优化-掌握JMH](https://my.oschina.net/xiandafu/blog/3067186)

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request

