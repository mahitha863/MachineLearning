package com.build.network;

import java.util.Arrays;

public class NeuralNetwork {
	
	static enum LayerType {I,H,O};
	
	//specifies the input each layer sizes like (4,3,2,1)
	public final int[] topology;
	public Layer[] layers;
	
	public NeuralNetwork(int[] topology) {
		this.topology = topology;
		layers = new Layer[topology.length];
		//create input layer
		layers[0] = new Layer(topology[0], LayerType.I);
		//create hidden layers
		for(int layer = 1;layer < layers.length-1;layer++) {
			layers[layer] = new Layer(topology[layer], LayerType.H, topology[layer-1]);
		}
		//create o/p layer
		layers[layers.length-1] = new Layer(topology[layers.length-1], LayerType.O, 
											topology[layers.length-2]);
	}

	
	public void feedForward(double[] inputs) {
		if(inputs.length != topology[0]) return;
		
		//Start feed forwarding process from hidden layers to out put layer
		//For input layer, output values are same as inputs. It does not require feed forward
		layers[0].outputs = inputs;
		
		for(int i = 1;i < layers.length;i++) {
			for(int neuron = 0;neuron < layers[i].noOfNeurons;neuron++) {	
				Layer curLayer = layers[i];
				Layer prevLayer = layers[i-1];
				double sum = curLayer.bias[neuron];
				for(int prevNeuron = 0;prevNeuron < prevLayer.noOfNeurons; prevNeuron++) {
					sum += prevLayer.outputs[prevNeuron] * curLayer.weights[neuron][prevNeuron];
				}
				curLayer.outputs[neuron] = sigmoid(sum);
				curLayer.outputDerivatives[neuron] = outputDerivative(curLayer.outputs[neuron]);
			}
		}
	}
	
	private double sigmoid(double x) {
		return 1d / (1 + Math.exp(-x));
	}
	
	private double outputDerivative(double x) {
		return x * (1 - x);
	}
	
	
	public void backPropogate(double[] expOutput) {
		//Start back propagate from output layer
		//for output layer neurons
		Layer outputLayer = layers[layers.length-1];
		for(int neuron = 0; neuron < outputLayer.noOfNeurons; neuron ++) {
			outputLayer.outputGradient[neuron] = (outputLayer.outputs[neuron] - expOutput[neuron]) *
					                             outputLayer.outputDerivatives[neuron];
		}
		
		// for inner neurons(i.e except o/p)
		for(int layer = layers.length-2;layer>0;layer--) {
			for(int neuron=0; neuron<layers[layer].noOfNeurons; neuron++) {
				double sum=0;
				for(int nextNeuron = 0; nextNeuron < layers[layer+1].noOfNeurons; nextNeuron++) {
					sum += layers[layer + 1].weights[nextNeuron][neuron] * layers[layer + 1].outputGradient[nextNeuron];
				}
				layers[layer].outputGradient[neuron] = sum * layers[layer].outputDerivatives[neuron];
			}
		}
	}
	
	public void updateWeights(double etaLR) { 
        for(int layer = 1; layer < layers.length; layer++) {
            for(int neuron = 0; neuron < layers[layer].noOfNeurons; neuron++) {

                double delta = - etaLR * layers[layer].outputGradient[neuron];
                layers[layer].bias[neuron] += delta;

                for(int prevNeuron = 0; prevNeuron < layers[layer-1].noOfNeurons; prevNeuron ++) {
                	layers[layer].weights[neuron][prevNeuron] += delta * layers[layer-1].outputs[prevNeuron];
                }
            }
        }	
	}
	
	public void trainNetwork(double[] inputs, double[] expOutput, double etaLR) {  //eta is learning rate
		feedForward(inputs);
		backPropogate(expOutput);
		updateWeights(etaLR);
	}
	
	public static void main(String[] args) {
		
		//Testing XOR operation with our neural netowrk
		int[] topology = {2,2,3,1};
		NeuralNetwork net = new NeuralNetwork(topology);
		
		double[] in1 = new double[] {0,0};
		double[] out1 = new double[] {0};
		
		double[] in2 = new double[] {0,1};
		double[] out2 = new double[] {1};
		
		double[] in3 = new double[] {1,0};
		double[] out3 = new double[] {1};
		
		double[] in4 = new double[] {1,1};
		double[] out4 = new double[] {0};
		
		for(int i=0; i<100000; i++) {
			net.trainNetwork(in1, out1, 0.3);
			net.trainNetwork(in2, out2, 0.3);
			net.trainNetwork(in3, out3, 0.3);
			net.trainNetwork(in4, out4, 0.3);
		}
		
		net.feedForward(in1);
		System.out.println(Arrays.toString(net.layers[net.layers.length-1].outputs));
		
		net.feedForward(in2);
		System.out.println(Arrays.toString(net.layers[net.layers.length-1].outputs));
		
		net.feedForward(in3);
		System.out.println(Arrays.toString(net.layers[net.layers.length-1].outputs));
		
		net.feedForward(in4);
		System.out.println(Arrays.toString(net.layers[net.layers.length-1].outputs));
		
	}

	

}
