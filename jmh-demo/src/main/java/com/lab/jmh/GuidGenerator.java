package com.lab.jmh;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
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

	private static final List<String> dictionary;
	private static final char[] lowerChars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	private static final char[] upperChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	private static final char[] numberChars = "0123456789".toCharArray();

	private static int index = 0;

	static {
//		dictionary = new char[4096];
		LinkedList<String> number = new LinkedList<String>();
		char[] dictChars = new char[10000];
		buildCombination(dictChars, upperChars, numberChars);
		buildCombination(dictChars, numberChars, upperChars);

		buildCombination(dictChars, numberChars, lowerChars);
		buildCombination(dictChars, lowerChars, numberChars);

		buildCombination(dictChars, upperChars, lowerChars);
		buildCombination(dictChars, lowerChars, upperChars);

		dictionary = Lists.newLinkedList();
		int index = 0;
		for (int i = 0; i < dictChars.length; i += 2) {
			if (i > 8191) {
				break;
			}
			StringBuffer temp = new StringBuffer();
			dictionary.add(temp.append(dictChars[i]).append(dictChars[i+1]).toString());
		}
	}

	private static void buildCombination(char[] dictChars,
										 char[] upperChars,
										 char[] numberChars) {
		for (int i = 0; i < upperChars.length; i++) {
			for (int j = 0; j < numberChars.length; j++) {
				dictChars[index++] = upperChars[i];
				dictChars[index++] = numberChars[j];
			}
		}
	}

	/**
	 * 将哈夫曼编码进行分割，在进行二次编码
	 *
	 * @param huffmanCode
	 * @return
	 */
	public static String huffmanCodeEncode(String huffmanCode) {
		List<String> string = Splitter.fixedLength(12).splitToList(huffmanCode);
		StringBuffer encodeString = new StringBuffer();
		for (int i = 0; i < string.size(); i++) {
			int charIndex = Integer.parseInt(string.get(i), 2);
			encodeString.append(dictionary.get(charIndex-1));
		}
		return encodeString.toString();
	}

	public static String huffmanCodeDecode(String encodeHuffmanCode) {
		List<String> spliterCode = Splitter.fixedLength(2).splitToList(encodeHuffmanCode);
		for (int i = 0; i < spliterCode.size(); i++) {
			int index = dictionary.indexOf(spliterCode.get(i));

		}
	}
}