package com.lab.jmh;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Striped;
import java.util.LinkedList;
import java.util.List;
import java.util.Spliterators;

/**
 * @author Xudong
 * @program java-benchmark
 * @decription <br> 针对产生
 * @date 2020/12/17 8:13 下午
 */
public class GuidGenerator {

	final static char[] digits = {
		'0', '1', '2', '3', '4', '5',
		'6', '7', '8', '9', 'a', 'b',
		'c', 'd', 'e', 'f', 'g', 'h',
		'i', 'j', 'k', 'l', 'm', 'n',
		'o', 'p', 'q', 'r', 's', 't',
		'u', 'v', 'w', 'x', 'y', 'z',
		'A', 'B', 'C', 'D', 'E', 'F',
		'G', 'H', 'I', 'J', 'K', 'L',
		'M', 'N', 'O', 'P', 'Q', 'R',
		'S', 'T', 'U', 'V', 'W', 'X',
		'Y', 'Z', '-', '_'
	};

	private static void buildCombination(LinkedList<Character> dictChars,
										 char[] upperChars,
										 char[] numberChars) {
		for (int i = 0; i < upperChars.length; i++) {
			for (int j = 0; j < numberChars.length; j++) {
				dictChars.add(upperChars[i]);
				dictChars.add(numberChars[j]);
			}
		}
	}

	/**
	 * 十进制转换为二进制
	 *
	 * @param de
	 * @return
	 */
	public static String decimal2Binary(int de) {
		String numstr = "";
		while (de > 0) {
			int res = de % 2; //除2 取余数作为二进制数
			numstr = res + numstr;
			de = de / 2;
		}
		return numstr;
	}

	/**
	 * 将整数转换为 64 进制
	 *
	 * @param i
	 * @return
	 */
	public static String to64HexString(int i) {
		return toUnsignedString0(i, 6);
	}

	public static String toBinaryString(int i) {
		return toUnsignedString0(i, 1);
	}

	private static String toUnsignedString0(int val, int shift) {
		// assert shift > 0 && shift <=5 : "Illegal shift value";
		int mag = Integer.SIZE - Integer.numberOfLeadingZeros(val);
		int chars = Math.max(((mag + (shift - 1)) / shift), 1);
		char[] buf = new char[chars];

		formatUnsignedInt(val, shift, buf, 0, chars);

		// Use special constructor which takes over "buf".
		return new String(buf);
	}

	static int formatUnsignedInt(int val, int shift, char[] buf, int offset, int len) {
		int charPos = len;
		int radix = 1 << shift;
		int mask = radix - 1;
		do {
			buf[offset + --charPos] = GuidGenerator.digits[val & mask];
			val >>>= shift;
		} while (val != 0 && charPos > 0);

		return charPos;
	}

	/**
	 * 将哈夫曼编码进行分割，在进行二次编码
	 *
	 * @param huffmanCode
	 * @return
	 */
	public static String huffmanCodeEncode(String huffmanCode) {
		String targetHuffmanCode = Strings
			.padEnd(huffmanCode, huffmanCode.length() + (6 - huffmanCode.length() % 6), '0');
		List<String> string = Splitter.fixedLength(6).splitToList(targetHuffmanCode);
		StringBuffer encodeString = new StringBuffer();
		for (int i = 0; i < string.size(); i++) {
			int charIndex = Integer.parseInt(string.get(i), 2);
			encodeString.append(to64HexString(charIndex));
		}
		return encodeString.toString();
	}

	/**
	 * 对哈夫曼二次编码的结果进行解码
	 *
	 * @param encodeHuffmanCode
	 * @return
	 */
	public static String huffmanCodeDecode(String encodeHuffmanCode) {
		char[] targetChars = encodeHuffmanCode.toCharArray();
		StringBuffer decodeBinaryStr = new StringBuffer();
		for (int i = 0; i < targetChars.length; i++) {
			int number = index(targetChars[i]);
			String binaryString = Strings.padStart(toBinaryString(number), 6, '0');
			if (i == targetChars.length - 1) {
				binaryString = binaryString.substring(0, binaryString.lastIndexOf("1") + 1);
			}
			decodeBinaryStr.append(binaryString);
		}
		return decodeBinaryStr.toString();
	}

	private static int index(char c) {
		for (int i = 0; i < digits.length; i++) {
			if (digits[i] == c) {
				return i;
			}
		}
		return -1;
	}
}