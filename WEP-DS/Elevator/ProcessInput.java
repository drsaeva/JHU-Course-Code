package Elevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class ProcessInput:
 * 
 * Acquires data from the input file. Requests the file's path from the user and provides an error when
 * 
 * @author David Saeva
 *
 */

public class ProcessInputLines {
	
	private String filePath;
	Scanner getFile;
	Scanner readFile;

	
	public ProcessInputLines() {
		
		System.out.println("Please provide the path to the input file: ");	//request file input from user
		
		getFile = new Scanner(System.in);
		try {
			while(getFile.hasNext()) {
				filePath = getFile.nextLine();
			}
			
			File inputFile = new File(filePath);
			readFile = new Scanner(inputFile);
			
		} catch (FileNotFoundException e) {
			System.out.println("The file path provided is invalid. Please try again.");
			
		}
	}
	
	/**
	 * Method checkFileHeader
	 * @param input
	 * Checks string/line for header comment (double forward slash), returns true if present
	 */
	
	private boolean checkFileHeader (String input) {
		if (input.contains("//")) {
			return true;
		}
		return false;
	}
	
	public String getLineFromInput() {
		while (readFile.hasNext()) {
			if (!readFile.nextLine().contains("/")) {
				return readFile.nextLine();
			}
		}
		String invalid = new String("");
		return invalid;
	}
	
	public void closeFile() {
		getFile.close();
		readFile.close();
	}
}
