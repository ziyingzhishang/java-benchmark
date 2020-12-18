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

	private static final String targetString = "14043119890510401X14043119890510401X14043119890510401X"
		+ "14043119890510401X14043119890510401X14043119890510401X14043119890510401X"
		+ "14043119890510401X14043119890510401X14043119890510401X14043119890510401X"
		+ "14043119890510401X14043119890510401X14043119890510401X14043119890510401X"
		+ "14043119890510401X14043119890510401X14043119890510401X14043119890510401X";

	@Benchmark
	public static void encodeHufman() {
		// 优化之前
//		System.out.println("==== 哈夫曼编码 ======");
		huff.toHufmCode(targetString);
	}

	@Benchmark
	public static void decodeHufman() {
		// 优化之后
//		System.out.println("===== 哈夫曼解码 ======");
		// 将目标字符串利用生成好的哈夫曼编码生成对应的二进制编码
		String hufmanCode = "1111011100001111001100001100111111011111011101011101001101011111001100011111011111001100001111001111011110111111011100001111001100001100111111011111011101011101001101011111001100011111011111001100001111001111011110111111111";
		// 将上述二进制编码再翻译成字符串
		huff.CodeToString(hufmanCode);
	}

	@Benchmark
	public static void encodeHuffmanCode() {
		String huffmanCode = huff.toHufmCode(targetString);
		GuidGenerator.huffmanCodeEncode(huffmanCode);
	}

	@Benchmark
	public static void decodeHuffmanCode() {
		// ZMYMPZZRQRYNZYMYZXZMYMPZZRQRYNZYMYZX_w
		String decodeHuffmanCode = GuidGenerator.huffmanCodeDecode("ZMYMPZZRQRYNZYMYZXZMYMPZZRQRYNZYMYZXZMYMPZZRQRYNZYMYZXZMYMPZZRQRYNZYMYZXZMYMPZZRQRYNZYMYZXZMYMPZZRQRYNZYMYZXZMYMPZZRQRYNZYMYZXZMYMPZZRQRYNZYMYZXZMYMPZZRQRYNZYMYZXZMYMPZZRQRYNZYMYZXZMYMPZZRQRYNZYMYZXZMYMPZZRQRYNZYMYZXZMYMPZZRQRYNZYMYZXZMYMPZZRQRYNZYMYZXZMYMPZZRQRYNZYMYZXZMYMPZZRQRYNZYMYZXZMYMPZZRQRYNZYMYZXZMYMPZZRQRYNZYMYZXZMYMPZZRQRYNZYMYZX_w");
		huff.CodeToString(decodeHuffmanCode);
	}

	@Benchmark
	public static void zipEncodeHuffmanCode() {
		String huffmanCode = huff.toHufmCode(targetString);
		String encodeHuffmanCode = GuidGenerator.huffmanCodeEncode(huffmanCode);
		StringZipper.compress(encodeHuffmanCode);
	}

	@Benchmark
	public static void unzipEncodeHuffmanCode() {
		String zipEncodeHuffmanCode = "H4sIAAAAAAAAAIvyjfQNiIoKCgyK9IuK9I2MiogaFaFYJL4cAGTgrflYAQAA";
		String unzipEncodeHuffmanCode = StringZipper.decompress(zipEncodeHuffmanCode);
		String decodeHuffmanCode = GuidGenerator.huffmanCodeDecode(unzipEncodeHuffmanCode);
		huff.CodeToString(decodeHuffmanCode);
	}

	public static void main(String[] args) throws RunnerException {
		Options options = new OptionsBuilder()
			.include(MyBatchmarkDemo.class.getSimpleName())
			.build();
		new Runner(options).run();

//		String targetString = "14043119890510401X14043119890510401X14043119890510401X"
//			+ "14043119890510401X14043119890510401X14043119890510401X14043119890510401X"
//			+ "14043119890510401X14043119890510401X14043119890510401X14043119890510401X"
//			+ "14043119890510401X14043119890510401X14043119890510401X14043119890510401X"
//			+ "14043119890510401X14043119890510401X14043119890510401X14043119890510401X";
//		System.out.println("目标编码：" + targetString);
//		String huffmanCode = huff.toHufmCode(targetString);
//		System.out.println("编码结果: " + huffmanCode);
//		String encodeHhuffmanCode = GuidGenerator.huffmanCodeEncode(huffmanCode);
//		System.out.println("二次编码结果：" + encodeHhuffmanCode);
//		String decodeHuffmanCode = GuidGenerator.huffmanCodeDecode(encodeHhuffmanCode);
//		System.out.println("二次编码解码: "+ decodeHuffmanCode);
//		System.out.println("哈夫曼解码：" + huff.CodeToString(decodeHuffmanCode));
	}
}
