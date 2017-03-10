/**
 * Main Class
 * @author David Saeva
 * 
 * Contains all controlling logic necessary to parse input data in order to create and calculate the determinant 
 * 	of a square matrix. Provides checking and handling for non-square matrix inputs, as well as non-numeric inputs.
 */

package determ;

import java.io.File;
import java.util.Scanner;

public class Main {
	
	public static String badInput = "\nPlease enter a square matrix, with an initial header line "
			+ "providing the number of rows and columns followed by\nthe contents of the matrix, one row at a "
			+ "time. Use spaces as delimiters between elements. Non-integer numbers are\nacceptable, and "
			+ "non-numerical entries will result in an invalid matrix and program termination.";
	
	public static void main(String[] args) {
		Scanner readIn;
		boolean goodMatrix = true;
		double[][] newmat;
		Matrix m;
		
		if (args.length < 1) {									// initialize Scanner by file path from args or by user entry if path not provided/bad
			System.out.println("No input file path provided.");
			System.out.println(badInput);
			readIn = new Scanner(System.in);
		} else {
			try{ 					
				readIn = new Scanner(new File(args[0]));		// try to read in the file path
			} catch (java.io.FileNotFoundException e) {			// if it fails catch the exception and provide feedback, then instead request user entry
				System.out.println("File cannot be opened from the path provided, or a file does not exist with that name.");
				System.out.println(badInput);
				readIn = new Scanner(System.in);
			}
		}
		
		String[] dimLine = readIn.nextLine().split(" ");		// split first line on whitespace, should contain matrix dimensions
		goodMatrix = checkInputLine(dimLine, true);
		if (!goodMatrix) System.exit(0);						// kill program if the header line is bad
		
		int[] dims = new int[2];
		dims[0] = Integer.parseInt(dimLine[0]);
		dims[1] = Integer.parseInt(dimLine[1]);
		
		if (dims[0] != dims[1]) {								// check header line to make sure this is a square matrix, provide feedback if not
			goodMatrix = false;
			System.out.println("\nThe provided matrix header line descibes a matrix with an unequal number of rows and columns.\n"
					+ "The determinant can only be found for square (equal rows/colums) matrices.\n"
					+ "Please restart with a square matrix that only contains numeric elements.");
		}
		if (!goodMatrix) System.exit(0);						// kill program if the header line shows this isn't a square matrix
		
		newmat = new double[dims[0]][dims[1]];					// otherwise make a new square matrix of the order provided
		
		for (int i=0; i<dims[0]; i++) {							// read over the input lines for the square matrix, checking them to make sure elements are numeric
			String[] nextLine = readIn.nextLine().split(" ");
			goodMatrix = checkInputLine(nextLine, false);
			if (!goodMatrix) System.exit(0);					// if not, kill the program
			
			for (int j=0; j<dims[0]; j++) {
				newmat[i][j] = Double.parseDouble(nextLine[j]);	// if they are, add the values to the double array that will populate the Matrix object
			}
			
		}
		
		m = new Matrix(dims[0], dims[1], newmat);				// instantiate the Matrix object and then print it out, then get the determinant and print it out
		System.out.println("\nThe matrix you've provided is:\n");
		readIn.close();
		
		for (int a=0; a<m.getRows(); a++) {
			for (int b=0; b<m.getCols(); b++) {
				System.out.print(m.getArray()[a][b] +" ");
			}
			System.out.println();
		}
		
		System.out.println("\nThe determinant of this matrix is: " + m.getDet());
		
	}
	
	
	/**
	 * Checks the current input line (String array) for numeric-only characters by attempting to parse values. Catches thrown
	 * 	NumberFormatExceptions (e.g. when attempting to parse alphabetical characters) and returns a user-friendly error message
	 * 	before returning false. Also provides a message and returns false for headers longer than 2 elements.
	 * @param input	String array containing elements to be examined
	 * @param isHeader boolean describing whether the input String array is the header line for the matrix
	 * @return	true if the String array contains only numeric elements and (for headers) the correct number of elements  
	 * 			false otherwise
	 */
	public static boolean checkInputLine(String[] input, boolean isHeader) {
		if (isHeader) {
			if (input.length > 2 || input.length < 2) {	// if the header line isn't exactly two elements it shouldn't be parsed to provide row/col numbers, so return false
				System.out.println("\nInvalid header line. Header line should contain two elements each representing the number of rows"
						+ " and columns, respectively.\nPlease restart with a square matrix that only contains numeric elements.");
				return false;
			}
		}
		try {
			for (int j=0; j<input.length; j++) {
				int i = -12345;							// arbitrary int value for header elements
				double d = 99.91919;					// arbitrary double value for matrix elements
				if (isHeader) {
					i = Integer.parseInt(input[j]);
				} else {
					d = Double.parseDouble(input[j]);
				}
				
				if (i != -12345 | d != 99.91919) {		// if the attempt at parsing the input String element changed the arbitrary value, return true
					return true;
				} 
			}
				
		} catch (java.lang.NumberFormatException e) {	// catch the exception when attempting to parse a non-numeric character, provide feedback, and return false
			System.out.print("\nInvalid character detected in the ");
			if (isHeader) System.out.print("header");
			if (!isHeader) System.out.print("current matrix");
			System.out.println(" line. The determinant of the provided matrix cannot be found.\n"
					+ "Please restart with a square matrix that only contains numeric elements.");
		}
		return false;
	}
	
}
