package com.mnist.data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;
import com.build.network.NeuralNetwork;
import com.build.network.Util;


public class Mnist {
	
	static int[][] confMatrix = new int[10][10];
	static NeuralNetwork network;
	public static double accuracy = 0.0;
	
	public static void trainMnistData(NeuralNetwork net,ArrayList<TestSample> sampleSet, int epochs, int loops, int shuffleSize) {
        for(int e = 0; e < epochs;e++) {
        	for(int i=0;i < loops;i++) {
        		ArrayList<TestSample> shuffledSet = Util.shuffleSamples(sampleSet, shuffleSize);
        		for(int b = 0; b < shuffleSize; b++) {
                    net.trainNetwork(shuffledSet.get(b).getInputData(), shuffledSet.get(b).getTargetOutput(), 0.3);
                }
        	}
        	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>  epoch:  "+ e+ "   <<<<<<<<<<<<<<<<<<<<<<<<<<");
        }
    }
	
	public static void testMnistData(NeuralNetwork net, ArrayList<TestSample> testSampleSet) {
        int noOfcorrect = 0;
        for(int i = 0; i < testSampleSet.size(); i++) {
        	net.feedForward(testSampleSet.get(i).getInputData());
        	double[] calculatedOutput = net.layers[net.layers.length-1].outputs;
        	int highestOutput = highestValueIndex(calculatedOutput);

            int targetOutputHighest = highestValueIndex(testSampleSet.get(i).getTargetOutput());
            if(highestOutput == targetOutputHighest) {
            	noOfcorrect ++ ;
            	confMatrix[targetOutputHighest][targetOutputHighest]++;
            }else {
            	confMatrix[targetOutputHighest][highestOutput]++;
            }
        }
        System.out.println("\nCONFUSION MATRIX:");
        for (int[] row : confMatrix) {
        	System.out.println(Arrays.toString(row)); 
        }
        System.out.println();
        for(int i = 0;i < 10;i++)
        	System.out.println("Precision of " + i + " output =" + precision(i, confMatrix));
        System.out.println();
        for(int i = 0;i < 10;i++)
        	System.out.println("Recall of " + i + " output =" + recall(i, confMatrix));
        
        accuracy = (double)noOfcorrect / (double)testSampleSet.size() * 100;
        
        System.out.println("\nAfter Training the Test output RESULT: " + noOfcorrect + " / " + testSampleSet.size()+ 
        		"  -> " + accuracy +" %");
    }
	
	public static double precision(int digitRow, int[][] confMatrix) {
		double precision = 0;
			double sum=0;
			for(int j=0;j<confMatrix.length;j++) {
				//m(ii)/sum of m(ji)
				sum+=confMatrix[j][digitRow];
			}
			precision = confMatrix[digitRow][digitRow]/sum;
		return precision;
	}
	
	
	public static double recall(int digitRow, int[][] confMatrix) {
		double recall = 0;
			double sum=0;
			for(int j=0;j<confMatrix.length;j++) {
				//m(ii)/sum of m(ij)
				sum+=confMatrix[digitRow][j];
			}
			recall = confMatrix[digitRow][digitRow]/sum;
		return recall;
	}

	
	public static int highestValueIndex(double[] values){
        int index = 0;
        for(int i = 1; i < values.length; i++){
            if(values[i] > values[index]){
                index = i;
            }
        }
        return index;
    }
	
	public static void main(String[] args) {
		trainAndTest();
	}
	
	public static void trainAndTest() {
		network = new NeuralNetwork(new int[] {784, 95, 35, 10});
		String path = new File("").getAbsolutePath();
		String imgFilePath = path + "/src/main/resources/train-images-idx3-ubyte";
		String labelFilePath = path + "/src/main/resources/train-labels-idx1-ubyte";
		ArrayList<TestSample> trainSampleData = Util.convertImageToData(imgFilePath, labelFilePath, 60000); //total of 60000 images
        trainMnistData(network, trainSampleData, 100, 50, 100);
        
        String testImgFilePath = path + "/src/main/resources/t10k-images-idx3-ubyte";
		String testLabelFilePath = path + "/src/main/resources/t10k-labels-idx1-ubyte";
		ArrayList<TestSample> testSampleData = Util.convertImageToData(testImgFilePath, testLabelFilePath, 10000);  //total of 10000 images
		testMnistData(network, testSampleData);
		
	}
	
	public static int testPaintedImage(String fileName) {
		return testImage(network, fileName);
	}
	
	public static int testImage(NeuralNetwork network, String FileName) {
		double[] input = new double[28*28];
		try
        {
          BufferedImage image0 = ImageIO.read(new File(FileName));
          input = Util.imageTo1DArray(image0);
        } 
        catch (IOException e)
        {
        	System.out.println(e);
        }
		network.feedForward(input);
		double[] calculatedOutput = network.layers[network.layers.length-1].outputs;
    	int highestOutput = highestValueIndex(calculatedOutput);
    	System.out.println("calculated as......"+highestOutput);
    	return highestOutput;
	}

}
