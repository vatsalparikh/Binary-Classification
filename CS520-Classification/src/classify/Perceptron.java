package classify;

import java.util.List;
import java.util.Arrays;
import java.io.FileNotFoundException;

public class Perceptron {
	
	// Declaring and initializing learning rate, bias, iterations, weights
	static double learningRate = 0.001;
	static double bias = 0;
	static int ITERATIONS = 300;
	static double[] weight;

	public static void start(String text, int col) throws FileNotFoundException {
		weight = new double[col];
		for (int i = 0; i < weight.length; i++) {
			weight[i] = 0;
		}
		List<Instance> instances = Files.readData(text);
		train(instances);
	}
	
	// perceptron learning rule
	public static void train(List<Instance> instances) {
		for (int n = 0; n < ITERATIONS; n++) {
			for(int i = 0; i < instances.size(); i++) {
				int[] data = instances.get(i).data;
				int predict = classify(data);
				int label = instances.get(i).label;
				for (int j=0; j<weight.length; j++) {
					weight[j] = weight[j] + learningRate * (label - predict) * data[j];
				}
				bias = bias + learningRate * (label - predict);
			}
		}
		System.out.println(Arrays.toString(weight));
		System.out.println(bias);
	}
	
	public static int classify(int[] input) {
		double sum = 0.0;
		for (int i=0; i < weight.length;i++)  {
			sum += weight[i] * input[i] + bias;
		}
		return step(sum);
	}
	
	public static int step(double sum) {
		if (sum < 0) {
			return 0;
		}
		else {
			return 1;
		}
	}
}