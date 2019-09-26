package com.mnist.data;

public class TestSample {
	
	private double[] inputData;
	private double[] targetOutput;
	
	public TestSample(double[] inputData, double[] targetOutput) {
		this.inputData = inputData;
		this.targetOutput = targetOutput;
	}

	public double[] getInputData() {
		return inputData;
	}

	public void setInputData(double[] inputData) {
		this.inputData = inputData;
	}

	public double[] getTargetOutput() {
		return targetOutput;
	}

	public void setTargetOutput(double[] targetOutput) {
		this.targetOutput = targetOutput;
	}

}
