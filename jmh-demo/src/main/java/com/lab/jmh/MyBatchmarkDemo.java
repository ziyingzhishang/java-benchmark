package com.lab.jmh;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * @author Xudong
 * @program GTMC-COUPON
 * @decription <br>
 *     预热 10 次
 *     每次执行 5s， 执行10000次
 *     一个线程
 *     每个方法一个进程
 *     统计单位（秒）
 *
 * @date 11/25/20 5:55 PM
 */
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 10)
@Measurement(iterations = 10, time = 5, timeUnit = TimeUnit.SECONDS)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.SECONDS)
public class MyBatchmarkDemo {

	@Benchmark
	public static void testBefore() {
		// 优化之前
		System.out.println("====优化前======");
	}

	@Benchmark
	public static void testAfter() {
		// 优化之后
		System.out.println("-----优化后-----");
	}

	public static void main(String[] args) throws RunnerException {
		Options options = new OptionsBuilder()
			.include(MyBatchmarkDemo.class.getSimpleName())
			.build();
		new Runner(options).run();
	}
}
