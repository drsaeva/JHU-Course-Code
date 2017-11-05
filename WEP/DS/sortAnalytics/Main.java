package sortAnalytics;
/**
 * Main Class
 * @author David R. Saeva
 * 
 * Provides controlling logic for the sort analytics class.
 */
import java.io.*;
import java.util.*;

public class Main {
	private static HashMap<String, HashMap<Integer, Long[]>> mergeSortTimes, quickSortTimes;		// nested data structures for storing algorithm run times
	private static String[] listOrders = {"R", "A", "D"};											// array of test file order types 
	private static Integer[] listLengths = {5, 10, 20, 50};											// array of test file lengths, in 1000's of lines
	private static int numRuns = 10;																// number of trial runs per test file, default = 10
	private static boolean windows = true;															// whether the current OS is windows
	
	public static void main(String[] args) {
		int[] data;
		String[] inputFiles = new String[12];
		String listPath, dirPath;
		Scanner query = null;
		if (args.length == 0) {																		// if there are no arguments, prompt the user to provide the 
																									//   path to the input 
			query = new Scanner(System.in);
			System.out.println("Please provide the path to the text file containing the list of test files that the sort"
				+ " algoriths will be tested on.\nNote - the data in this file must correspond to the formatting described"
				+ " in the README. If it does not, this program cannot run!!\nIf you are on Windows, the path provided"
				+ " must use two backslashes per directory separation instead of one - 'C:\\\\asdf...', not 'C:\\asdf....' "
				+ "\n\nProvide the path below: \n");	
			listPath = query.nextLine();
		} else {
			listPath = args[0];																		// arg 1, if exists, should always be the input file
		}
		
		if (args.length <= 1) {																		// if there's only 1 argument or less, prompt for the number of trials
			System.out.println("Please provide the number of test runs you'd like to perform for each sorting algorithm, per file.\n"
				+ " Any input that is less than 10, more than 200, a non-numeric input, or non-integer input will result in the"
				+ " default number of runs (10) being used.\n");
			try {
				String numIn = query.nextLine();
				int in = Integer.parseInt(numIn);
				if (in <= 200 && in >= 10) numRuns = in;
			} catch(NumberFormatException e) {
				System.out.println("Non-numeric input provided. The default number of runs will be used.");
			}
		} else {
			try {
				int in = Integer.parseInt(args[1]);													// arg 2, if exists, should always be the number of trials
				if (in <= 200 && in >= 10) numRuns = in;
			} catch(NumberFormatException e) {
				System.out.println("Non-numeric input provided. The default number of runs will be used.");
			}
		}
		
		if (args.length <= 2) {																		// if there's only 2 arguments or less, prompt for the test file directory path
			System.out.println("Please provide the path to the directory that hosts the input files the sort algorithms are to"
					+ " be tested on. If the host directory is the same\ndirectory in which the first file (which contained the"
					+ " list of input files), please enter 'Same'. Provide the path below: \n");
			dirPath = query.nextLine();
			if (dirPath.equals("Same")) {
				String[] tempPath = listPath.split("\\\\");
				if (tempPath.length >=1) {
					windows = true;
				} else {
					tempPath = listPath.split("/");
					if (tempPath.length <= 1) {
						System.out.println("You have not provided a valid file path. Please restart and provide a valid file path.");
						System.exit(0);
					} else{
						windows = false;
					}
				}
				
				String[] dir = new String[tempPath.length-1];
				for (int i=0; i<dir.length; i++) {
					dir[i] = tempPath[i];
				}
				dirPath = makePathFromArray(dir, windows);
			}
		} else {																					// arg 3 should always be the test file dir path
			dirPath = args[2];
		}
		
		Scanner parseTestfileInfo = null;
		
		try {
			parseTestfileInfo = new Scanner(new File(listPath));									// read the input file in line by line, store in array
		} catch (FileNotFoundException e) {
			System.out.println("The path provided for the file containing test file names could not be read or is otherwise"
					+ " invalid! Please restart and provide a valid path to this file.");
			System.exit(0);
		}
		
		try {
			for (int i=0; i<inputFiles.length; i++) {
				inputFiles[i] = parseTestfileInfo.nextLine();
			}
			parseTestfileInfo.close();
		} catch (NullPointerException e) {
			System.out.println("The file you provided is empty or otherwise does not contain a number of lines matching"
					+ " the number of required test files for testing the sorting algorithms. Please check to make sure "
					+ "you have provided the correct file and, if necessary, edit the file so that it contains  the correct"
					+ " format and number of test files.");
			parseTestfileInfo.close();
			System.exit(0);
		}
		
		if (badInputFileFormatting(inputFiles)) System.exit(0);										// if the input file is formatted improperly, kill the program. see readme for formatting
		Scanner read = null;
		quickSortTimes = initHMaps(numRuns);
		mergeSortTimes = initHMaps(numRuns);
		Stack<Integer> inStack;
		int count = 0;
		for (String infile : inputFiles) {															// read in each test file from the input file lines
			inStack = new Stack<Integer>(60000);													// instantiate new stack for each test file, lines from test file will be passed in
			String[] currentFile = infile.split(" ");
			String currentPath = dirPath;
			int intline = 0;
			try {
				 currentPath+=currentFile[2];
				 count++;
				 System.out.println("File "+ count+ "'s path is: " + currentPath);
				 read = new Scanner(new File(currentPath));
			} catch(FileNotFoundException e) {
				System.out.println("Invalid file path - the file name and directory combination you've provided"
						+ " cannot be opened.\nPlease ensure that the test file you've specified in your input file"
						+ " exists in the directory you've specified via runtime argument or standard input.");
				System.exit(0);
			}
			
			while (read.hasNext()) {
				
				try {
					intline = Integer.valueOf(read.nextLine());
				} catch (NumberFormatException e) {
					System.out.println("Line number " + count + " in " + currentFile[2] + " contains a non-numeric character."
							+ "\nPlease restart with a file that contains only 1 integer per line");
				}
				
				inStack.push(intline);																
				
			}
			
			if (inStack.size() > 1) {
				data = new int[inStack.size()];
				for (int j=0; j<inStack.size(); j++) {
					data[j] = inStack.pop();														// remove lines from temp stack and place in data array
				}
			} else {
				data = new int[1];
			}	
			
		
			for (int c=0; c<numRuns; c++) {
				int[] temp = data;																	// copy data array into temp array for sorting, quicksort and place time into DS
				quickSortTimes.get(currentFile[0]).get(Integer.parseInt(currentFile[1]))[c] = Quicksort.sort(temp);
				
				temp = data;																		// renew temp array, mergesort and place time into DS
				mergeSortTimes.get(currentFile[0]).get(Integer.parseInt(currentFile[1]))[c] = Mergesort.sort(temp);
	
			}
			
			System.out.println("Quicksort " + currentFile[0] + " " + currentFile[1] +"000 lines: "+ 
					avgTime(quickSortTimes.get(currentFile[0]).get(Integer.parseInt(currentFile[1]))) +" nanoseconds" ); 
			System.out.println("Mergesort " + currentFile[0] + " " + currentFile[1] +"000 lines: "+ 
					avgTime(mergeSortTimes.get(currentFile[0]).get(Integer.parseInt(currentFile[1]))) +" nanoseconds" ); 
	}
	}

	
	/**
	 * Determines the average of an array of the Long type. Generates average runtimes for sort algorithm timing.
	 * 	Uncoment the middle print line if you want to see individual run time results from the passed-in array 
	 * 	printed to standard out.
	 * @param sortResults Long[] array containing runtimes for the specified sort algorithm, data ordering, and 
	 * 	number of integers
	 * @return long average of the values in the array
	 */
	private static long avgTime(Long[] sortResults) {
		long sum = 0;
		for (long l : sortResults) {
			//System.out.println(l);
			sum+=l;
		}
		return sum/sortResults.length;
	}
	
	/**
	 * Properly instantiates a generic nested HashMap/HashMap/Long[] structure to be used to store
	 * 	run times for a specified sort type. Returns the generic data structure for referencing by
	 * 	the actual data structure reference for the specific sort algorithm
	 * @param numRuns int describing the number of test runs to be performed on each file
	 * @return nested data structure reference for sort algorithm run time storage
	 */
	private static HashMap<String, HashMap<Integer, Long[]>> initHMaps(int numRuns) {
		HashMap<String, HashMap<Integer, Long[]>> sortTimes = new HashMap<String, HashMap<Integer, Long[]>>();
		for (String s : listOrders) {
			sortTimes.put(s, new HashMap<Integer, Long[]>());
			for (Integer l : listLengths) 
				sortTimes.get(s).put(l, new Long[numRuns]);
		}
		return sortTimes;
	}
	
	/**
	 * 
	 * @param inputFile
	 * @return
	 */
	private static boolean badInputFileFormatting(String[] inputFile) {
		for (int i=0; i<inputFile.length; i++) {
			String[] current = inputFile[i].split(" ");
			if (current.length != 3) return true;
			if (!current[0].equals("R")  && current[0].equals("A") && current[0].equals("D"))
				return true;
			try {
				int size = Integer.parseInt(current[1]);
				if (size != 5  && size != 10 && size != 20 && size != 20 && size != 50)
					return true;
			} catch (NumberFormatException e) {
				System.out.println("At line " + i + " in your input file, you have either provided a non-numeric" +
						" or invalid (not 5 10 20 or 50) value for the number of thousands of lines in that input file");
				return true;
			}
			String[] filename = current[2].split("[.]");
			if (filename.length != 2) {
				System.out.println("Test file listed on line " + (i+1) + " of your input file does not contain a file extension.");
				return true;
			}
			if (!filename[1].equals("txt")) {
				System.out.println("Test file listed on line " + (i+1) + " of your input file does not have a '.txt' file extension.");
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Simple method that reproduces a string representation of a directory path from the passed-in 
	 * 	String array. Takes a boolean arg for OS, which determines whether backslashes or forward 
	 * 	slashes should be used in concatenating path elements
	 * @param arr String[] array containing file path elements
	 * @param windows boolean whether the current operating system is Windows
	 * @return String representation of a directory path
	 */
	private static String makePathFromArray(String[] arr, boolean windows) {
		String path = "";
		for (String s : arr) {
			path+=s;
			if (windows) {
				path+="\\\\";
			} else {
				path+="/";
			}
		}
		
		return path;
	}
	
}
