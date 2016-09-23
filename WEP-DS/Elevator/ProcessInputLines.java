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
			if(getFile.hasNext()) {
				filePath = getFile.nextLine();
			}
			
			File inputFile = new File(filePath);
			readFile = new Scanner(inputFile);
			if (readFile.hasNextLine()) {
				System.out.println("file read!");
				
				if (!checkFileHeader(readFile.nextLine())) {
					System.out.println("No header found in this file - this file "
							+ "is invalid for this simulation.");
					System.exit(1);
				}
			} else {
				System.out.println("file read error");
			}
		} catch (FileNotFoundException e) {
			System.out.println("The file path provided is invalid. Please try again.");
			
		}
	}
	
	/**
	 * States whether string/line has header comment (double forward slash)
	 * @param input String/line
	 * @return true if '//' is found in line
	 * 				 false otherwise
	 */
	private boolean checkFileHeader (String input) {
		if (input.contains("//")) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getLineFromInput() {
		while (readFile.hasNext()) {
			String line = readFile.nextLine();
			if (!checkFileHeader(line)) {
				return line;
			}
		}
		String invalid = new String("");
		return invalid;
	}
	
	public boolean inputHasNext() {
		if (readFile.hasNext()) {
			return true;
		} else {
			return false;
		}
	}
	
	public void closeFile() {
		getFile.close();
		readFile.close();
	}
}
