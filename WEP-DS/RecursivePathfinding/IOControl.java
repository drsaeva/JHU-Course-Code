package lab2;

import java.io.File;
import java.util.Scanner;

public class IOControl {
	private File inputFile;
	private File outputFile;
	Scanner s;
	
	public IOControl(String input, String output) {
		try {
			s = new Scanner(inputFile);
		} catch (java.io.FileNotFoundException e) {
			System.out.println("Bad input file path.");
		}
		
		if (s.hasNext()) {
			
		}
	}
	
	public String[] inputLine() {
		String[] line = s.nextLine().split(" ");
		return line;
	}
	
	private void getInputFile(String input) {
		inputFile = new File(input);
	}
	
	private void getOutputFile(String output) {
		outputFile = new File(output);
	}
}
