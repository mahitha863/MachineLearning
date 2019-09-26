package com.neuralnetwork.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.util.ArrayList;
import org.junit.Test;
import com.mnist.data.TestSample;
import junit.framework.Assert;
import com.mnist.data.Mnist;
import com.build.network.Layer;
import com.build.network.NeuralNetwork;
import com.build.network.Util;

public class DataProcessTest {

	@Test
	public void convertImageToDataTest() {
		try {
			int trainDataSamplesCount = 30000;
			String path = new File("").getAbsolutePath();
			String imgFilePath = path + "/src/main/resources/train-images-idx3-ubyte";
			String labelFilePath = path + "/src/main/resources/train-labels-idx1-ubyte";
			ArrayList<TestSample> trainSampleData = Util.convertImageToData(imgFilePath, labelFilePath,
					trainDataSamplesCount); // total of 60000 images
			assertNotNull(trainSampleData);
			assertTrue("Training data count mismatch", (trainSampleData.size() == trainDataSamplesCount));
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testMnistAccuracy() {
		NeuralNetwork network = new NeuralNetwork(new int[] {784, 200, 100, 10});
		String path = new File("").getAbsolutePath();
		String imgFilePath = path + "/src/main/resources/train-images-idx3-ubyte";
		String labelFilePath = path + "/src/main/resources/train-labels-idx1-ubyte";
		ArrayList<TestSample> trainSampleData = Util.convertImageToData(imgFilePath, labelFilePath, 60000); //total of 60000 images
        Mnist.trainMnistData(network, trainSampleData, 100, 50, 100);
        
        String testImgFilePath = path + "/src/main/resources/t10k-images-idx3-ubyte";
		String testLabelFilePath = path + "/src/main/resources/t10k-labels-idx1-ubyte";
		ArrayList<TestSample> testSampleData = Util.convertImageToData(testImgFilePath, testLabelFilePath, 10000);  //total of 10000 images
		Mnist.testMnistData(network, testSampleData);
		
        double cal_accuracy = Mnist.accuracy;
		assertEquals(90.0, cal_accuracy, 10.0);
        
	}

	@Test
	public void highestValueIndexTest() {
		try {
			int index = Mnist.highestValueIndex(new double[] { 1.0, 2.0, 3.0 });
			assertEquals(2, index, 0);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void recallTest() {
		try {
			double d = Mnist.recall(1, new int[][] { { 0, 0 }, { 1, 1 } });
			assertEquals(0.5, d, 0);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void precisionTest() {
		try {
			double d = Mnist.precision(1, new int[][] { { 0, 0 }, { 1, 1 } });
			assertEquals(1, d, 0);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void weights2DArrayTestWithWeight1() {
		try {
			Layer layer = new Layer(0, null);
			double d[][] = layer.createWeights2DArray(2, 2, -1, 1, false);

			for (double[] i : d) {
				for (double j : i) {
					assertEquals(1.0, j, 0);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void doubleArrayTestWithWeight1() {
		try {
			Layer layer = new Layer(0, null);
			double d[] = layer.createDoubleArray(2, 0.0, 1.0, false);

			for (double j : d) {
				assertEquals(1.0, j, 0);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

}
