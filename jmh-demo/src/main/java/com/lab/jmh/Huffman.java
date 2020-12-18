package com.lab.jmh;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import jdk.nashorn.internal.ir.IfNode;

/**
 * @author Xudong
 * @program java-benchmark
 * @decription <br>
 * @date 2020/12/11 5:56 下午
 */
class Huffman {

	private String str;        // 最初用于压缩的字符串
	private HNode root;        // 哈夫曼二叉树的根节点
	private boolean flag;    // 最新的字符是否已经存在的标签
	private LinkedList<CharData> charList;    // 存储不同字符的队列 相同字符存在同一位置
	private LinkedList<HNode> NodeList;        // 存储节点的队列

	private static Huffman huff = null;
	private static final String data = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_|";

	private class CharData {

		int num = 1; // 字符个数
		char c; // 字符

		public CharData(char ch) {
			c = ch;
		}
	}

	private class HNode {

		public String code = "";    // 节点的哈夫曼编码
		public String data = "";    // 节点的数据
		public int count;            // 节点的权值
		public HNode lChild;
		public HNode rChild;

		public HNode() {
		}

		public HNode(String data, int count) {
			this.data = data;
			this.count = count;
		}

		public HNode(int count, HNode lChild, HNode rChild) {
			this.count = count;
			this.lChild = lChild;
			this.rChild = rChild;
		}

		public HNode(String data, int count, HNode lChild, HNode rChild) {
			this.data = data;
			this.count = count;
			this.lChild = lChild;
			this.rChild = rChild;
		}

		public String getCode() {
			return code;
		}

		public boolean isLeaf() {
			return lChild == null && rChild == null;
		}
	}

	public static Huffman getInstance() {
		if (ObjectUtil.isNull(huff)) {
			// 创建哈弗曼对象
			synchronized (Huffman.class) {
				if (ObjectUtil.isNull(huff)) {
					huff = new Huffman();
					// 构造树
					huff.creatHfmTree(data);

					// 显示字符的哈夫曼编码
					huff.output();
				}
			}
		}
		return huff;
	}

	/**
	 * 构建哈夫曼树
	 *
	 * @param str
	 */
	public void creatHfmTree(String str) {
		this.str = str;

		NodeList = new LinkedList<HNode>();
		charList = new LinkedList<CharData>();

		// 1.统计字符串中字符以及字符的出现次数
		// 以CharData类来统计出现的字符和个数
		getCharNum(str);

		// 2.根据第一步的结构，创建节点
		creatNodes();

		// 3.对节点权值升序排序
		sort(NodeList);

		// 4.取出权值最小的两个节点，生成一个新的父节点
		// 5.删除权值最小的两个节点，将父节点存放到列表中
		creatTree();

		// 6.重复第四五步，就是那个while循环
		// 7.将最后的一个节点赋给根节点
		root = NodeList.get(0);
	}

	/**
	 * 统计出现的字符及其频率
	 *
	 * @param str
	 */
	private void getCharNum(String str) {

		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i); // 从给定的字符串中取出字符
			flag = true;

			for (int j = 0; j < charList.size(); j++) {
				CharData data = charList.get(j);

				if (ch == data.c) {
					// 字符对象链表中有相同字符则将个数加1
					data.num++;
					flag = false;
					break;
				}
			}

			if (flag) {
				// 字符对象链表中没有相同字符则创建新对象加如链表
				charList.add(new CharData(ch));
			}
		}
	}

	/**
	 * 将出现的字符创建成单个的结点对象
	 */
	private void creatNodes() {

		for (int i = 0; i < charList.size(); i++) {
			String data = charList.get(i).c + "";
			int count = charList.get(i).num;

			HNode node = new HNode(data, count); // 创建节点对象
			NodeList.add(node); // 加入到节点链表
		}

	}

	/**
	 * 构建哈夫曼树
	 */
	private void creatTree() {

		while (NodeList.size() > 1) {// 当节点数目大于一时
			// 4.取出权值最小的两个节点，生成一个新的父节点
			// 5.删除权值最小的两个节点，将父节点存放到列表中
			HNode left = NodeList.poll();
			HNode right = NodeList.poll();

			// 在构建哈夫曼树时设置各个结点的哈夫曼编码
			left.code = "0";
			right.code = "1";
			setCode(left);
			setCode(right);

			int parentWeight = left.count + right.count;// 父节点权值等于子节点权值之和
			HNode parent = new HNode(parentWeight, left, right);

			NodeList.addFirst(parent); // 将父节点置于首位
			sort(NodeList); // 重新排序，避免新节点权值大于链表首个结点的权值
		}
	}

	/**
	 * 升序排序
	 *
	 * @param nodelist
	 */
	private void sort(LinkedList<HNode> nodelist) {
		for (int i = 0; i < nodelist.size() - 1; i++) {
			for (int j = i + 1; j < nodelist.size(); j++) {
				HNode temp;
				if (nodelist.get(i).count > nodelist.get(j).count) {
					temp = nodelist.get(i);
					nodelist.set(i, nodelist.get(j));
					nodelist.set(j, temp);
				}

			}
		}

	}

	/**
	 * 设置结点的哈夫曼编码
	 *
	 * @param root
	 */
	private void setCode(HNode root) {

		if (root.lChild != null) {
			root.lChild.code = root.code + "0";
			setCode(root.lChild);
		}

		if (root.rChild != null) {
			root.rChild.code = root.code + "1";
			setCode(root.rChild);
		}
	}

	/**
	 * 遍历
	 *
	 * @param node 节点
	 */
	private void output(HNode node) {

		if (node.lChild == null && node.rChild == null) {
			System.out.println(node.data + ": " + node.code);
		}
		if (node.lChild != null) {
			output(node.lChild);
		}
		if (node.rChild != null) {
			output(node.rChild);
		}
	}

	/**
	 * 输出结果字符的哈夫曼编码
	 */
	public void output() {
		output(root);
	}

	/**
	 * 编码
	 *
	 * @param str
	 * @return
	 */
	public String toHufmCode(String str) {
		// 增加结束标记
		String targetStr = str + "_";

		String hfmCodeStr = "";
		for (int i = 0; i < targetStr.length(); i++) {
			List<HNode> searchList = search(root);
			String c = targetStr.charAt(i) + "";
			for (HNode hNode : searchList) {
				if (hNode.lChild == null && hNode.rChild == null) {
					if (c.equals(hNode.data)) {
						hfmCodeStr += hNode.getCode();
					}
				}
			}
		}

		return hfmCodeStr;
	}

//	/**
//	 *
//	 * @param root 哈夫曼树根节点
//	 * @param c 需要生成编码的字符
//	 */
//	private void search(HNode root, String c) {
//		if (root.lChild == null && root.rChild == null) {
//			if (c.equals(root.data)) {
//				// 找到字符，将其哈夫曼编码拼接到最终返回二进制字符串的后面
//				if (ObjectUtil.isNull(hfmCodeStr.get())) {
//					hfmCodeStr.set(root.code);
//				} else {
//					hfmCodeStr.set(hfmCodeStr.get() + root.code);
//				}
//			}
//		}
//		if (root.lChild != null) {
//			search(root.lChild, c);
//		}
//		if (root.rChild != null) {
//			search(root.rChild, c);
//		}
//	}

	/**
	 * 将
	 *
	 * @param root
	 * @return
	 */
	private List<HNode> search(HNode root) {
		Queue<HNode> queue = new ArrayDeque<HNode>();
		List<HNode> list = new ArrayList<HNode>();

		if (root != null) {
			//将根元素加入“队列”
			queue.offer(root);
		}

		while (!queue.isEmpty()) {
			//将该队列的“队尾”元素加入到list中
			list.add(queue.peek());
			HNode p = queue.poll();

			//如果左子节点不为null，将它加入到队列
			if (p.lChild != null) {
				queue.offer(p.lChild);
			}

			//如果右子节点不为null，将它加入到队列
			if (p.rChild != null) {
				queue.offer(p.rChild);
			}
		}

		return list;
	}

	/**
	 * 解码
	 *
	 * @param codeStr
	 * @return
	 */
	public String CodeToString(String codeStr) {
		StringBuffer hfmDecodeStr = new StringBuffer();
		boolean target = false;
		int start = 0;
		int end = 1;

		List<HNode> searchList = search(root);
		char[] codeArray = codeStr.toCharArray();
		LinkedList<Character> codeCharList = new LinkedList<Character>();
		for (int i = 0; i < codeArray.length; i++) {
			codeCharList.add(codeArray[i]);
		}

		while (codeCharList.size() > 0) {
			HNode node = root;
			do {
				Character c = codeCharList.removeFirst();
				if (c.charValue() == '0') {
					node = node.lChild;
				} else {
					node = node.rChild;
				}
			} while (!node.isLeaf());

			hfmDecodeStr.append(node.data);
		}

//		while (end <= codeStr.length()) {
//			target = false;
//			String s = codeStr.substring(start, end);
////			matchCode(root, s); // 解码
//			if (root.lChild == null && root.rChild == null) {
//				if (s.equals(root.code)) {
//					// 找到对应的字符，拼接到解码字符穿后
//					hfmDecodeStr += root.data;
//					target = true; // 标志置为true
//				}
//			}
//			if (root.lChild != null) {
//				matchCode(root.lChild, s);
//			}
//			if (root.rChild != null) {
//				matchCode(root.rChild, s);
//			}
//
//			// 每解码一个字符，start向后移
//			if (target) {
//				start = end;
//			}
//			end++;
//		}
		String preDecodeStr = hfmDecodeStr.toString();
		return preDecodeStr.substring(0, preDecodeStr.lastIndexOf('_'));
	}

//	/**
//	 * 匹配字符哈夫曼编码，找到对应的字符
//	 * @param root 哈夫曼树根节点
//	 * @param code 需要解码的二进制字符串
//	 */
//	private void matchCode(HNode root, String code){
//		if (root.lChild == null && root.rChild == null) {
//			if (code.equals(root.code)) {
//				// 找到对应的字符，拼接到解码字符穿后
//				if (ObjectUtil.isNull(hfmDecodeStr.get())) {
//					hfmDecodeStr.set(root.data);
//				} else {
//					hfmDecodeStr.set(hfmDecodeStr.get() + root.data);
//				}
//				target.set(true); // 标志置为true
//			}
//		}
//		if (root.lChild != null) {
//			matchCode(root.lChild, code);
//		}
//		if (root.rChild != null) {
//			matchCode(root.rChild, code);
//		}
//	}
}
