package classify;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Files {
	
	//Generates a row of binary string with given probability and row length
	public static void createFile(String text, int rows, int cols, double prob, int label) throws FileNotFoundException {
		writer(text);
		prob = prob * cols;
		int probInt = (int) prob;
		int[] colLength = new int[cols + 1];
		for (int l = 0; l < rows; l++) {
			for (int i = 0; i < probInt; i++) {
				colLength[i] = 0;
			}
			for(int j = probInt; j < cols; j++) {
				colLength[j] = 1;
			}
			shuffle(colLength);
			colLength[cols] = label; // extended array to store labels for classA or classB in same file
			for(int k = 0; k < colLength.length; k++) {
				System.out.print(colLength[k]);
			}
			System.out.println();
		}
		System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
	}
	
	// Prevents output being displayed to console, then takes that output and 
	// mirrors it to the file. Closes print stream when all of the output is in file 
	public static void writer(String text) throws FileNotFoundException {
		FileOutputStream fos = new FileOutputStream(text);
		PrintStream ps = new PrintStream(fos);
		System.setOut(ps);
	}
	
	//Randomly shuffles integer array
	public static void shuffle(int[] array) {
		Random rand = new Random();
		int index, temp;
		for (int i = 0; i < array.length; i++) {
			index = rand.nextInt(array.length);
			temp = array[index];
			array[index] = array[i];
			array[i] = temp;
		}
	}
		
	public static void mergeFiles(String text1, String text2, String text3) throws Exception {
			PrintWriter pw = new PrintWriter(text3);
			BufferedReader br1 = new BufferedReader(new FileReader(text1));
			BufferedReader br2 = new BufferedReader(new FileReader(text2));
			String line1 = br1.readLine();
			String line2 = br2.readLine();
			
			while (line1 != null || line2 != null) {
				pw.println(line1);
				line1 = br1.readLine();
				pw.println(line2);
				line2 = br2.readLine();
			}
			
			pw.flush();
			br1.close();
			br2.close();
			pw.close();
	}
	
	public static List<Instance> readData(String file) throws FileNotFoundException {
		List<Instance> dataSet = new ArrayList<Instance>();
		Scanner scan = null;
		try {
			scan = new Scanner(new File(file));
			while(scan.hasNextLine()) {
				String line = scan.nextLine();
				int[] data = new int[line.length() - 1];
				int i = 0;
				for (i=0; i<line.length()-1; i++) {
					data[i] = line.charAt(i) - '0';
				}
				int label = line.charAt(line.length() - 1) - '0';
				Instance instance = new Instance(data, label);
				dataSet.add(instance);
			}
		} finally {
			if (scan != null) {
				scan.close();
			}
		}
		return dataSet;
	}	
	
	// Reads a text file by taking String as address of the file location and displays
	//  the output to console
	public static void reader(String text) throws Exception {
		BufferedReader fileReader = null;
		try {
			FileInputStream byteStream = new FileInputStream(text);
			fileReader = new BufferedReader(new InputStreamReader(byteStream, "UTF8"));
			String line;
			while((line = fileReader.readLine()) != null) {
				System.out.println(line);
			}
		} finally {
			if(fileReader != null) {
				fileReader.close();
			}
		}
	}
}
