package classify;

import java.util.List;
import java.util.Arrays;
import java.io.FileNotFoundException;

public class LogisticRegression {
	
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
		// logistic regression training rule
		public static void train(List<Instance> instances) {
			for (int n = 0; n < ITERATIONS; n++) {
				for(int i = 0; i < instances.size(); i++) {
					int[] data = instances.get(i).data;
					double predict = classify(data);
					int label = instances.get(i).label;
					for (int j=0; j<weight.length; j++) {
						weight[j] = weight[j] + learningRate * (label - predict) * data[j];
					}
					bias = bias + learningRate * (label - predict);
				}
			}
			System.out.println(Arrays.toString(weight));
		}
		
		public static double classify(int[] input) {
			double sum = 0.0;
			for (int i=0; i < weight.length;i++)  {
				sum += weight[i] * input[i] + bias;
			}
			return sigmoid(sum);
		}
		
		private static double sigmoid(double sum) {
			return 1.0 / (1.0 + Math.exp(-(sum)));
		}
}
