package com.neuralnetwork.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.mnist.data.Mnist;

public class ImageProcessingTest {
	
	@BeforeClass
	public static void trainNN() {
		Mnist.trainAndTest();
	}
	
	@Test
	public void testImage0() {
		int calculated_result = Mnist.testPaintedImage("images/digit0.png");
		assertEquals(0.0, calculated_result, 0);
	}
	
//	@Test
//	public void testImage2() {
//		int calculated_result = Mnist.testPaintedImage("images/digit2.png");
//	    assertEquals(2.0, calculated_result, 0);
//	}
	
	@Test
	public void testImage3() {
		int calculated_result = Mnist.testPaintedImage("images/digit3.png");
		assertEquals(3.0, calculated_result, 0);
	}
	
//	@Test
//	public void testImage5() {
//		int calculated_result = Mnist.testPaintedImage("images/digit5.png");
//		assertEquals(5.0, calculated_result, 0);
//	}
	
	@Test
	public void testImage6() {
		int calculated_result = Mnist.testPaintedImage("images/digit6.png");
		assertEquals(6.0, calculated_result, 0);
	}
	
//	@Test
//	public void testImage7() {
//		int calculated_result = Mnist.testPaintedImage("images/digit7.png");
//		assertEquals(7.0, calculated_result, 0);
//	}
	
	@Test
	public void testImage8() {
		int calculated_result = Mnist.testPaintedImage("images/digit8.png");
		assertEquals(8.0, calculated_result, 0);
	}

}
