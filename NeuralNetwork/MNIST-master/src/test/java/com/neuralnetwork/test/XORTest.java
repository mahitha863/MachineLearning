package com.neuralnetwork.test;

import static org.junit.Assert.*;
import org.junit.Test;
import com.build.network.NeuralNetwork;

public class XORTest {

	@Test
	public void testXOR1() {
		int[] topology = {2,2,3,1};
		NeuralNetwork net = new NeuralNetwork(topology);
		
		double[] in1 = new double[] {0,0};
		double[] out1 = new double[] {0};
		
		for(int i=0; i<100000; i++) {
			net.trainNetwork(in1, out1, 0.3);
		}
		
		net.feedForward(in1);
		double cal_result[] = net.layers[net.layers.length-1].outputs;
		for(double i:cal_result) {
			assertEquals(0.0, i, 0.1);
		}
		
	}
	
	@Test
	public void TestXOR2() {
		int[] topology = {2,2,3,1};
		NeuralNetwork net = new NeuralNetwork(topology);
		
		double[] in1 = new double[] {0,1};
		double[] out1 = new double[] {1};
		
		for(int i=0; i<100000; i++) {
			net.trainNetwork(in1, out1, 0.3);
		}
		
		net.feedForward(in1);
		double cal_result[] = net.layers[net.layers.length-1].outputs;
		for(double i:cal_result) {
			assertEquals(1.0, i, 0.1);
		}
	}
	
	@Test
	public void TestXOR3() {
		int[] topology = {2,2,3,1};
		NeuralNetwork net = new NeuralNetwork(topology);
		
		double[] in1 = new double[] {1,0};
		double[] out1 = new double[] {1};
		
		for(int i=0; i<100000; i++) {
			net.trainNetwork(in1, out1, 0.3);
		}
		
		net.feedForward(in1);
		double cal_result[] = net.layers[net.layers.length-1].outputs;
		for(double i:cal_result) {
			assertEquals(1.0, i, 0.1);
		}
	}
	
	@Test
	public void TestXOR4() {
		int[] topology = {2,2,3,1};
		NeuralNetwork net = new NeuralNetwork(topology);
		
		double[] in1 = new double[] {1,1};
		double[] out1 = new double[] {0};
		
		for(int i=0; i<100000; i++) {
			net.trainNetwork(in1, out1, 0.3);
		}
		
		net.feedForward(in1);
		double cal_result[] = net.layers[net.layers.length-1].outputs;
		for(double i:cal_result) {
			assertEquals(0.0, i, 0.1);
		}
	}

}
