package lab2;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class IOControl {
	private File inputFile;
	private File outputFile;
	Scanner i;
	PrintWriter o;
	
	public IOControl(String input, String output) {
		inputFile = new File(input);
		outputFile = new File(output);
		
		try {
			i = new Scanner(inputFile);
		} catch (FileNotFoundException e) {
			System.out.println("Bad input file path.");
		}
		
		try {
			o = new PrintWriter(outputFile);
		} catch (FileNotFoundException e) {
			System.out.println("Bad output file path.");
		}
		
	}
	
	/**
	 * Takes input line, splits on whitespace, and returns the resulting String array
	 * @return String[] generated from input line
	 */
	public String[] inputLineArr() {
		String[] line = {};
		
		if (i.hasNext()) {
			line = i.nextLine().split(" ");
		}
		return line;
	}
	
	/**
	 * Public access for close() methods for Scanner/PrintWriter
	 */
	public void closeIO() {
		i.close();
		o.close();
	}
	
}
