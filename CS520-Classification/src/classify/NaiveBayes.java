package classify;

import java.io.FileNotFoundException;
import java.util.List;

public class NaiveBayes {
	
	// Declare prior probability values. Assumed to be 0.5 as both classes have 5000 rows.
	static final double priorProbA = 0.5;
	static final double priorProbB = 0.5;
	
	static double[] prob1sGivenA;
	static double[] prob1sGivenB;
	
	// Use each column as a feature vector and find probability of getting 1 in each column that goes from 1 to 5000
	public static void start(String text1, String text2, int rows, int cols) throws FileNotFoundException {
		prob1sGivenA = train(text1, rows, cols);
		prob1sGivenB = train(text2, rows, cols);
	}
	
	// Read the files and find out likelihood given class
	public static double[] train(String text, int rows, int cols) throws FileNotFoundException {
		double[] prob1sGivenClass = new double[cols];
		int[][] data = new int[rows][cols];
		List<Instance> dataSet = Files.readData(text);
		int count = 0;
		
		for (int i = 0; i < dataSet.size(); i++) {
			for(int j = 0; j < cols; j++) {
				data[i][j] = dataSet.get(i).data[j];
			}
		}
		
		for (int k = 0; k < cols; k++) {
			for (int l = 0; l < dataSet.size(); l++) {
				System.out.print(data[l][k]);
				if(data[l][k] == 1) {
					count++;
				}
			}
			prob1sGivenClass[k] = (double) count / (double) rows;
			count = 0;
		}
		return prob1sGivenClass;
	}
	
	// test Naive Bayes using formula, P(A|features) = Likelihood(features|Class) * Prior(Class) / Evidence(features)
	public static void test(int[] testArray) {
		double likelihood1sGivenB;
		double likelihood1sGivenA;
		if (testArray[0] == 0) {
			likelihood1sGivenA = 1 - prob1sGivenA[0];
			likelihood1sGivenB = 1 - prob1sGivenB[0];
		}
		else {
			likelihood1sGivenA = prob1sGivenA[0];
			likelihood1sGivenB = prob1sGivenB[0];
		}
		for (int i = 1; i < testArray.length; i++) {
			if (testArray[i] == 0) {
				likelihood1sGivenA *= 1 - prob1sGivenA[0];
				likelihood1sGivenB *= 1 - prob1sGivenB[0];
			}
			else {
				likelihood1sGivenA *= prob1sGivenA[0];
				likelihood1sGivenB *= prob1sGivenB[0];
			}
		}
		double probBGivenData = (priorProbB * likelihood1sGivenB) / ((priorProbA * likelihood1sGivenA) + (priorProbB * likelihood1sGivenB));
		
		if (probBGivenData >= 0.5) {
			System.out.println("Class 1 with probability " + probBGivenData);
		}
		else {
			System.out.println("Class 0 with probability " + (1 - probBGivenData));
		}
	}
}