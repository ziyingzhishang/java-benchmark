package com.lab.jmh;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterOutputStream;

/**
 * @author Xudong
 * @program java-benchmark
 * @decription <br>
 * @date 2020/12/18 3:33 下午
 */
public class StringZipper {

	public static String compress(String data) {
		if (data == null || data.length() == 0) {
			return data;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = null;
		try {
			gzip = new GZIPOutputStream(out);
			gzip.write(data.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (gzip != null) {
				try {
					gzip.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return new sun.misc.BASE64Encoder().encode(out.toByteArray());
	}

	public static String decompress(String compressedStr) {
		if (compressedStr == null) {
			return null;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = null;
		GZIPInputStream ginzip = null;
		byte[] compressed = null;
		String decompressed = null;
		try {
			compressed = new sun.misc.BASE64Decoder().decodeBuffer(compressedStr);
			in = new ByteArrayInputStream(compressed);
			ginzip = new GZIPInputStream(in);

			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = ginzip.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ginzip != null) {
				try {
					ginzip.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			try {
				out.close();
			} catch (IOException e) {
			}
		}
		return decompressed;
	}

	public static void main(String[] args) throws IOException {
		StringBuilder sb = new StringBuilder();
//		for (int i = 0; i < 10; i++) {
		sb.append("ZMYMPZZRQRYNZYMYZXZMYMPZZRQRYNZYMYZXZMYMPZZRQRYNZYMYZXZMYMPZZRQRYNZYMYZXZMYMPZZRQRYNZYMYZXZMYMPZZRQRYNZYMYZXZMYMPZZRQRYNZYMYZXZMYMPZZRQRYNZYMYZXZMYMPZZRQRYNZYMYZXZMYMPZZRQRYNZYMYZXZMYMPZZRQRYNZYMYZXZMYMPZZRQRYNZYMYZXZMYMPZZRQRYNZYMYZXZMYMPZZRQRYNZYMYZXZMYMPZZRQRYNZYMYZXZMYMPZZRQRYNZYMYZXZMYMPZZRQRYNZYMYZXZMYMPZZRQRYNZYMYZXZMYMPZZRQRYNZYMYZX_w");
//		}
		String str = sb.toString();
		System.out.println("原长度：" + str.length());

//		System.out.println("压缩后长度：" + StringZipper.compress(str).length());
		String compress = StringZipper.compress(str);
		System.out.println("压缩后内容：" + compress);
		System.out.println("解压后内容：" + StringZipper.decompress(compress));
	}
}
