package Elevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ProcessInput {
	private String filePath;
	
	
	public ProcessInput() {
		
		System.out.println("Please provide the path to the input file: ");	//request file input from
		
		Scanner getFile = new Scanner(System.in);
		try {
			while(getFile.hasNext()) {
				filePath = getFile.nextLine();
			}
			
			File inputFile = new File(filePath);
			Scanner readFile = new Scanner(inputFile);
			
			while (readFile.hasNext()) {
				if (!readFile.nextLine().contains("/")) {
					
				}
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("The file path provided is invalid. Please try again.");
			
		}
	}
	
	private void getPassengerFromLine(String[] input) {
		
		for (int index = 0; index < input.length; index ++) {
			String name = "";
			
			//i
			if (input[index] != " ") {
				name.concat(input[index]);
			
			} else if (input[index] == " ") {
				
				
				
			}
			
			
			
		}
		
	}
	
}
