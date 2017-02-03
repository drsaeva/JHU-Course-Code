package inToPost;

import java.util.Scanner;

public class IntToPost {
	
	public static void main(String[] args) {
		String input = "";
		boolean badInput = true;
		
		if (args.length > 1) {
			System.out.println("Input argument array is too long. Please provide a single in-fix statement "
					+ "in the correct format.");
		
		} else if (args.length != 0) {
			input = args[0];
		
		} else if (args.length == 0) {
			System.out.println("No input argument for in-fix statement provided. Please provide one now: ");
			Scanner getIn = new Scanner(System.in);
			
			while (badInput) {
				
				if (getIn.hasNext()) {
					input = getIn.nextLine();
				}
				
				if (input.length() < 2) {
					System.out.println("Length of in-fix input is invalid. Please provide another statement.");
				} else {
					badInput = false;
				}
				
			}
			
			if (goodInfixFormat(input)) {
				System.out.println("Great!");
			} else {
				System.out.println("FAILURE");
			}
		}
		
		
	}
	
	/**
	 * Checks input string from right to left for proper in-fix notation using a counter. Invalid character inputs
	 * 	and invalid in-fix formatting (equal or more operators than operands) produce error messages.
	 * @param input
	 * @return
	 */
	private static boolean goodInfixFormat(String input) {
		String[] chars = input.split("");
		int count = 0;
		for (int i=chars.length-1; i>-1; i--) {
			try {
				Integer.valueOf(chars[i]);
				count++;
				//System.out.println(count);
				
			} catch (java.lang.NumberFormatException e) {
				if (isOperator(chars[i])) {
					System.out.println("Operator!");
					count--;
					count--;
					count++;
					//System.out.println(count);
				} else {
					System.out.println("Invalid character found in in-fix input statement. Please restart with"
							+ "a proper in-fix statement.");
					return false;
				}
			}
			
			if (count < 0) {
				System.out.println("In-fix input statement contains an invalid amount of numbers and operators."
						+ "Please restart with a proper in-fix statement.");
				return false;
			}
			
		}
		
		if (count == 1) {
			return true;
		} else{
			return false;
		}
	}
	
	private static boolean isOperator(String a) {
		if (a.equals("+") || a.equals("-") || a.equals("*") || a.equals("/") || a.equals("(") || a.equals(")")) {
			return true;
		} else {
			return false;
		}
	}
}
