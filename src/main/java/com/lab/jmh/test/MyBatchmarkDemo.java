package com.lab.jmh.test;

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
 * @date 11/25/20 5:55 PM
 */
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 10)
@Measurement(iterations = 10000, time = 5, timeUnit = TimeUnit.SECONDS)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.SECONDS)
public class MyBatchmarkDemo {

	@Benchmark
	public static void testBefore() {
		// 优化之前
	}

	@Benchmark
	public static void testAfter() {
		// 优化之后
	}

	public static void main(String[] args) throws RunnerException {
		Options options = new OptionsBuilder()
			.include(MyBatchmarkDemo.class.getSimpleName())
			.build();
		new Runner(options).run();
	}
}
