package com.lab.jmh.test;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.OutputTimeUnit;

/**
 * @author Xudong
 * @program GTMC-COUPON
 * @decription <br>
 * @date 11/26/20 11:33 AM
 */
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class MapStructJmh {

	@Benchmark
	public void testBeanUtilsCoupon() {

	}

	@Benchmark
	public void testMapStuctCoupon() {

	}

	public static void main(String[] args) {

	}
}
