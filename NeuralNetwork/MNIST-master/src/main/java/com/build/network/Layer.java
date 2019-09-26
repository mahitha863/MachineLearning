package com.build.network;

import com.build.network.NeuralNetwork.LayerType;

public class Layer {
	
	public LayerType layerType;
	public int noOfNeurons;
	
	public double[] outputs;
	public double[] outputGradient;
	public double[] outputDerivatives;
	public double[][] weights;
	public double[] bias;
	
	public Layer(int layerSize,LayerType layerType) {
		this.layerType = layerType;
		this.noOfNeurons = layerSize;
		this.outputs = new double[noOfNeurons];
		this.bias = createDoubleArray(noOfNeurons, -0.5, 0.7, true);
		this.outputGradient = new double[noOfNeurons];
		this.outputDerivatives = new double[noOfNeurons];
	}
	
	public Layer(int layerSize, LayerType layerType, int prevLayerSize) {
		this.layerType = layerType;
		this.noOfNeurons = layerSize;
		this.outputs = new double[noOfNeurons];
		this.bias = createDoubleArray(noOfNeurons, -0.5, 0.7, true);
		this.outputGradient = new double[noOfNeurons];
		this.outputDerivatives = new double[noOfNeurons];
		
		this.weights = createWeights2DArray(layerSize, prevLayerSize, -1, 1, true);
		
	}
	
	public double[] createDoubleArray(int size,double lowerBound,double upperBound, boolean isRandom) {
		if(size < 1)
			return null;
		double[] array = new double[size];
		for(int i=0; i < size;i++) {
			double r = (isRandom == true ) ? Math.random() : 1.0;
			array[i] = r * (upperBound-lowerBound) + lowerBound;
		}
			
		return array;
	}
	
	public double[][] createWeights2DArray(int layerSize,int prevLayerSize,double lowerBound,double upperBound,boolean isRandom) {
		if(layerSize<1 || prevLayerSize<1) 
			return null;
		double[][] array = new double[layerSize][prevLayerSize];
		for(int i=0;i<layerSize;i++)
			array[i]=createDoubleArray(prevLayerSize, lowerBound, upperBound, isRandom);
		return array;
	}

}
