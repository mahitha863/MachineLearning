package com.build.network;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.mnist.data.TestSample;

public class Util {
	
	public static ArrayList<TestSample> shuffleSamples(ArrayList<TestSample> sampleSet, int size) {
        if(size > 0 && size <= sampleSet.size()) {
        	ArrayList<TestSample> shuffledSet = new ArrayList<TestSample>();
            Integer[] ids = generateRandValuesinList(0,sampleSet.size() - 1, size);
            for(Integer i:ids) {
            	shuffledSet.add(sampleSet.get(i));
            }
            return shuffledSet;
        } else return sampleSet;
    }
	
	public static Integer[] generateRandValuesinList(int lowerBound, int upperBound, int noOfValues) {

        lowerBound --;

        if(noOfValues > (upperBound-lowerBound)){
            return null;
        }

        Integer[] values = new Integer[noOfValues];
        for(int i = 0; i< noOfValues; i++){
            int n = (int)(Math.random() * (upperBound-lowerBound+1) + lowerBound);
            while(containsValue(values, n)){
                n = (int)(Math.random() * (upperBound-lowerBound+1) + lowerBound);
            }
            values[i] = n;
        }
        return values;
    }
	
	public static boolean containsValue(Integer[] ar, int value){
        for(int i = 0; i < ar.length; i++){
            if(ar[i] != null){
                if(value==ar[i]){
                    return true;
                }
            }
        }
        return false;
    }
	
	public static double[] imageTo1DArray(BufferedImage img) {
        double[] imageGray = new double[28 * 28];
        int w = img.getWidth();
        int h = img.getHeight();
        int index = 0;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                Color color = new Color(img.getRGB(j, i), true);
                int red = (color.getRed());
                int green = (color.getGreen());
                int blue = (color.getBlue());
                double v = 255 - (red + green + blue) / 3d;
                imageGray[index] = v;
                index++;
            }
        }
        return imageGray;
    }
	
	public static ArrayList<TestSample> convertImageToData(String imgFilePath,String labelFilePath,int noOfImages) {
		ArrayList<TestSample> testsamples = new ArrayList<TestSample>();
		try {
			BufferedInputStream imgBufferStream = new BufferedInputStream(new FileInputStream(imgFilePath));
			DataInputStream imgInputStream = new DataInputStream(imgBufferStream);
			imgInputStream.readInt(); // magic number
			int noOfItems = imgInputStream.readInt();
			int rows = imgInputStream.readInt();
			int cols = imgInputStream.readInt();
			
			BufferedInputStream labelBufferStream = new BufferedInputStream(new FileInputStream(labelFilePath));
			DataInputStream labelInputStream = new DataInputStream(labelBufferStream);
			labelInputStream.readInt();  // magic number
	        int numberOfLabels = labelInputStream.readInt();
	        
	        assert noOfItems == numberOfLabels;
	        if(noOfImages==0) noOfImages=noOfItems; 

	        for(int i = 0; i < noOfImages; i++) {
	        	double[] input = new double[28 * 28];
	            double[] output = new double[10];
	            output[labelInputStream.readUnsignedByte()] = 1d;
	            for (int r = 0; r < rows*cols; r++) {
	            	input[r] = imgInputStream.readUnsignedByte()/256d;
	            }
	            TestSample t = new TestSample(input,output);
	            testsamples.add(t);
	            if(i % 100 ==  0){
	            	System.out.println("converted to data: " + i);
	            }
	        }
	        imgInputStream.close();
	        labelInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return testsamples;
	}
}
