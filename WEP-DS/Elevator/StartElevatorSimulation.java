/*
 * TODO Refactoring:
 * 				- make elevator currentFloor publicly accessible (getter)
 * 				- need to check new data passenger's floor globally
 * 						- current code makes passengers prioritize entry into elevator 1
 * 						- should they prioritize comfort over a specific elevator?
 */

package Elevator;

import java.util.Scanner;

public class StartElevatorSimulation {
	
	public static void main(String[] args) {
		ProcessInputLines input = new ProcessInputLines();
		int n = setInputDataSize();
		Stack<Passenger> temp = new Stack<Passenger>(n); 
		Stack<Passenger> data = new Stack<Passenger>(n);
		
		while (input.inputHasNext()) {
			try {
				temp.push(new Passenger(input.getLineFromInput()));
			} catch (java.lang.IndexOutOfBoundsException e) {
				System.out.println("Stack Overflow!");
				System.exit(1);
			}
		}
		
		int t = temp.size();
		for (int i=0; i<t; i++) {
			data.push(temp.pop());
		}
		
		Elevator elev1 = new Elevator();
		Elevator elev2 = new Elevator();//data);
		
		while (!data.isEmpty()) {
			if (!elev1.elevatorEmpty()) {
				System.out.println("Elevator 1: ");
				elev1.checkPassFloor();
			}
			
			while (!data.isEmpty() && elev1.passengersAtFloor(data.peek())) {
				elev1.incomingPass(data.pop());
			}
			
			elev1.elevatorRoute();
			
			if (!elev2.elevatorEmpty()) {
				System.out.println("Elevator 2: ");
				elev2.checkPassFloor();
			}
			
			while (!data.isEmpty() && elev2.passengersAtFloor(data.peek())) {
				elev2.incomingPass(data.pop());
			}
			
			elev2.elevatorRoute();
		}
		
		while (!elev1.elevatorEmpty()) {
			elev1.checkPassFloor();
			elev1.elevatorRoute();
		}
		System.out.println("Simulation complete!");
		
	}
	
	
	/**
	 * Queries user for the size of the elevator simulation input data. Catches invalid
	 * 	inputs and returns a user-friendly error message.
	 * @return Integer value for the size of the input Stack of passengers
	 */
	private static int setInputDataSize() {
		int n = 0;
		System.out.println("Please provide a value equal to or larger than the "
				+ "size of the passenger data set you'd like to test: ");
		Scanner s = new Scanner(System.in);
		try {
			n = s.nextInt();
			System.out.println("\nValues smaller than the amount of passengers in the data file"
					+ " will result in a stack overflow error. In that scenario, please restart the"
					+ " program and provide a larger value.\n");
		} catch (java.util.InputMismatchException e) {
			System.out.println("\nNon-integer input provided. Please restart the program and "
					+ "provide a number (integer) corresponding with the size of the passenger "
					+ "data set you'd like to test.");
			System.exit(0);
		}
		
		s.close();
		return n;
		
	}
	
} //  E:/test/elevator_input_data.txt
