package com.lab.jmh;

import java.util.concurrent.TimeUnit;
import javax.xml.transform.Source;
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
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.SECONDS)
public class MyBatchmarkDemo {

	private static final Huffman huff = Huffman.getInstance();

	@Benchmark
	public static void encodeHufman() {
		// 优化之前
		System.out.println("==== 哈夫曼编码 ======");
		huff.toHufmCode("14043119890510401X");
	}

	@Benchmark
	public static void decodeHufman() {
		// 优化之后
		System.out.println("===== 哈夫曼解码 ======");
		// 将目标字符串利用生成好的哈夫曼编码生成对应的二进制编码
		String hufmanCode = "000010001000000000010000011100001000010110010110000110010000000010100001000000001000000000001001111";
		// 将上述二进制编码再翻译成字符串
		huff.CodeToString(hufmanCode);
	}

	public static void main(String[] args) throws RunnerException {
//		Options options = new OptionsBuilder()
//			.include(MyBatchmarkDemo.class.getSimpleName())
//			.build();
//		new Runner(options).run();

		String targetString = "14043119890510401X";
		System.out.println("编码：" + targetString);
		String sss = huff.toHufmCode(targetString);
		System.out.println(sss);
		String hufmCode = GuidGenerator.huffmanCodeEncode(sss);
		System.out.println("编码结果：" + hufmCode);
		System.out.println("解码：" + huff.CodeToString(hufmCode));

//		String hufmCode = huff.toHufmCode("14043119890510401X");
//		System.out.println("编码:" + hufmCode);
//
//		// 将上述二进制编码再翻译成字符串
//		System.out.println("解码：" + huff.CodeToString(hufmCode));

		// 000010001000000000010000011100001000010110010110000110010000000010100001000000001000000000001001111

	}
}
