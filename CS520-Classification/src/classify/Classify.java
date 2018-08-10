package classify;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Classify {

	public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
	
		// Initialize the number of rows, columns, probabilities, and class labels
		int rows = 5000;
		int cols = 100;
		double prob1 = 0.5;
		double prob2 = 0.7;
		int label1 = 0;
		int label2 = 1;
		
		// Create text files for class A, B and C where C file contains combination of class A and class B data
		String text1 = "C:\\Users\\vatsa\\Desktop\\classA.txt";
		String text2 = "C:\\Users\\vatsa\\Desktop\\classB.txt";
		String text3 = "C:\\Users\\vatsa\\Desktop\\classC.txt";
		
		// Generate data and put data flush data into files
		Files.createFile(text1, rows, cols, prob1, label1);
		Files.createFile(text2, rows, cols, prob2, label2);
		Files.mergeFiles(text1, text2, text3);
		
		// Train classifiers on training data
		Perceptron.start(text3, cols);
		LogisticRegression.start(text3, cols);
		NaiveBayes.start(text1, text2, rows, cols);
		
		// test
		int[] x0 = {0,0,1,1,0,1,1,1,1,0,0,1,0,1,0,1,0,0,1,1,1,0,0,0,0,0,1,0,0,1,1,0,1,0,1,0,1,0,0,0,1,1,1,1,1,1,0,1,1,1,0,0,0,0,0,1,1,0,0,1,1,0,0,1,0,1,1,0,0,1,1,1,0,1,1,0,0,1,1,0,1,1,1,0,0,0,1,1,0,1,0,0,0,0,0,1,0,1,1,1};
		int[] x1 = {0,0,1,0,0,0,0,1,1,0,0,1,0,1,0,1,0,0,1,0,1,0,1,0,0,0,0,0,0,0,0,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,1,0,0,0,1,1,0,1,0,0,0,0,0,0,0,0,0,0,1,0,1,0,1,0,0,0,1,0,0,1,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0};
		System.out.println("Perceptron: Class " + Perceptron.classify(x0));
		System.out.println("Logistic Regression: Class 1 " + LogisticRegression.classify(x0));
		System.out.print("Naive Bayes: ");
		NaiveBayes.test(x0);
	}
}