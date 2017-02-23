package assignment1;

import java.util.Scanner;

public class inToPost {
	
	public static void main(String[] args) {
		String input = "";
		String output = "";
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
			getIn.close();
			
			if (validInfixFormat(input)) {
				System.out.println("In-fix statement: " + input);
				output = convertToPost(input);
				System.out.println("Converted post-fix statement: " + output);
				System.out.println("Result of calculating the post-fix statement: " + evaluatePostfix(output));
			} else {
				System.exit(0);
			}
			
		}
		
	}
	
	public static int evaluatePostfix(String post){
		String[] postArr = post.split("");
	    Stack<Integer> results = new Stack<Integer>(postArr.length);
	    int operand = -1;
	    for (int i = 0; i < postArr.length; i++) {
	    	try {
				operand = Integer.valueOf(postArr[i]);
			} catch (java.lang.NumberFormatException e) {}
	    	
	        if (operators(postArr[i]) < 0) {
	            int calc = applyOperator(results.pop(), results.pop(), postArr[i]);
	            results.push(calc);
	        }
	        
	        else if ( operand >= 0 ) {
	            results.push(operand);
	        }
	    }
	    
	    return results.pop();
	}
	
	/**
	 * 
	 */
	
	private static String convertToPost(String input) {
		String[] chars = input.split("");
		Stack<String> temp = new Stack<String>(input.length());
		String output = "";
		
		for (int i=0; i<chars.length; i++) {
			int op = operators(chars[i]);							// operators int return value
			int par = parentheses(chars[i]);						// parentheses int return value
			int operand = -1;										//	initial operand value, passed-in values will always be 0-9 and non-negative
			
			try {													// attempt conversion of next token in chars array to int, catch exception in the event it is an operator
				operand = Integer.valueOf(chars[i]);
			} catch (java.lang.NumberFormatException e) {}
			
			if (operand >= 0) {										// if token operand, append to output string
				output+=chars[i];
			
			} else if (par > 0) {									// if token left brace, push onto stack
				temp.push(chars[i]);
				
			} else if ( op < 0 ) {									// if token operator, stack has >1 operator and the top is not a left brace, append until left brace visible
				while (temp.size() > 0 && !temp.peek().equals("(")) {
					if (operatorPrecedence(temp.peek(), chars[i])) {
						output+=temp.pop();
					} else {
						break;
					}
				}
				temp.push(chars[i]);									// push token operator to stack

			} else if (par < 0) {
				while (temp.size() > 0 && !temp.peek().equals("(")) {	// append non-left parentheses operators to the post-fix string
					output+=temp.pop();
				} 
				
				if (temp.size() > 0 ) {									// pop the remaining left parentheses
					temp.pop();
				}
	
			} 
			op=0;														// reset condition ints
			par=0;
			operand=-1;
		}
		
		while (temp.size() > 0 ) {										// append remaining operators
			output+=temp.pop();
		}
		return output;
	}
	
	/**
	 * Checks input string from right to left for proper in-fix notation using a counter. Invalid character inputs
	 * 	and invalid in-fix formatting (equal or more operators than operands) produce error messages.
	 * @param input String to be tested for valid in-fix notation
	 * @return true if in-fix notation is valid, false otherwise
	 */
	private static boolean validInfixFormat(String input) {
		String[] chars = input.split("");
		int count = 0;										// counter, incremented when an operand is found, decremented when an operator is found
		int pairedParans = 0;								// special counter for braces, see parantheses() method, should never be greater than 1 or less than 0
		for (int i=0; i<chars.length; i++) {
			try {											// try to convert next token to int - if successful, the count will increment
				Integer.valueOf(chars[i]);
				count++;
				
			} catch (java.lang.NumberFormatException e) {	// catch non-numerics, examine if they are operators/braces and adjust counters based on method return values
				if (isValidChar(chars[i])) {	
					count+=operators(chars[i]);	
					pairedParans+=parentheses(chars[i]);
					
				} else {									// if not a brace or operator in-fix statement is invalid
					System.out.println("Invalid character found in in-fix input statement. Please restart with"
							+ "a proper in-fix statement.");
					return false;
				}
			}
			
			if (count < 0 || count > 1) {	// if counter drops below 0 or over 1 due to adjacent operators/operands 
				System.out.println("In-fix input statement contains an invalid amount of numbers and operators."
						+ "Please restart with a proper in-fix statement.");
				return false;
			}
		}
		
		if (count == 1 && pairedParans == 0) {	// if last non-brace character is an operand and all braces are paired
			return true;
		} else{
			System.out.println("Invalid character found in in-fix input statement. Please restart with"
					+ "a proper in-fix statement.");
			return false;
		}
	}
	
	/**
	 * Compares an operator currently on top of the operator stack with an operator that may be
	 * 	added to the stack. If the current operator on top of the stack has a lower precedence
	 * 	(ie: - has a lower precedence than / in the order of operations), then this method returns
	 * 	false. Otherwise, it returns true.
	 * 
	 * @param top	operator on top of the stack
	 * @param other	operator that would be added to the top of the stack
	 * @return boolean whether the operator on top is of higher precedence or not
	 */
	private static boolean operatorPrecedence(String top, String other) {
	    if (top.equals("+") && other.equals("*")) // + has lower precedence than *
	        return false;
	    
	    if (top.equals("-") && other.equals("*")) // - has lower precedence than *
	        return false;
	    
	    if (top.equals("+") && other.equals("/")) // + has lower precedence than /
	        return false;
	    
	    if (top.equals("-") && other.equals("/")) // - has lower precedence than /
	        return false;

	    return true;	// for all other cases for these four operators, the comparison is of equal or higher precedence
	}
	
	/**
	 * Checks input String for equivalency with a brace or operator. Returns true if the string is a brace or operator,
	 * 	false if otherwise
	 * @param a input String
	 * @return boolean corresponding with whether the String is a brace/operator or otherwise
	 */
	private static boolean isValidChar(String a) {
		if (a.equals("+") || a.equals("-") || a.equals("*") || a.equals("/") || a.equals("(") || a.equals(")")) 
			return true;
		return false;
	}
	
	/**
	 * Checks input string to see if it corresponds with either a left or right brace. Returns
	 * 	+1 for a left brace, -1 for a right brace, or 0 otherwise.
	 * @param a input String
	 * @return int value corresponding with the String's identity
	 */
	private static int parentheses(String a) {
		if (a.equals("(")) 
			return 1;
		if (a.equals(")")) 
			return -1;
		return 0;
	}
	
	/**
	 * Checks input string to see if it is equivalent to one of the four operator
	 * 	characters valid for this assignment. If so, returns a -1 for adjusting the count
	 * 	in the 'validIfixFormat' method as well as for control loops in later methods. 
	 * @param a String potentially representing an operator
	 * @return int result depending on the String
	 */
	private static int operators(String a) {
		if (a.equals("+") || a.equals("-") || a.equals("*") || a.equals("/")) 
			return -1;
		return 0;
	}

	/**
	 * Performs an operation on two operands using a passed-in operator
	 * @param operand1	the first operand in the operation from left to right
	 * @param operand2	the second operand
	 * @param operator	the operator
	 * @return	int result of the operation
	 */
	private static int applyOperator(int operand1, int operand2, String operator) {
	    switch (operator) {
	        case "+":
	            return operand2 + operand1;
	        case "-":
	            return operand2 - operand1;
	        case "*":
	            return operand2 * operand1;
	        case "/":
	            return operand2 / operand1;
	        default:
	            return -1;
	    }
	}
	
}
