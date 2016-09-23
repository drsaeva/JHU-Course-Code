/*
 * TODO needs to handle input indirectly from ProcessInputLines in a string-by-string fashion
 * 				PIL should locally assign a line to a string
 * 			needs code to assess floorButtons bool values and perform stops or skip floors
 * 				ie - 2nd floor, going up, floorButton[2] = F (no one to 3rd) & floorButton[3] = T
 * 			re-examine 'at each floor' reqs in Lab1 document
 * 			see elevatorRoute method
 */
package Elevator;

public class Elevator {
	
	private int currentFloor = 1;												// Current floor the elevator is stopped at
	private int numPassengers = 0;											// Total number of passengers who used this elevator
	boolean goingUp = true;															// Whether the elevator is heading upwards
	boolean[] floorButtons = new boolean[5];					// 5-element boolean array in which each element represents
																											// 		a button in the elevator for a specific floor
	String[] floorNames = {"1st", "2nd", "3rd", 				// 5-element String array with floor names
			"4th", "5th" };
	
	/**
	 * 
	 * @param input 
	 */
	public Elevator(ProcessInputLines input) {
		Stack<Passenger> elev = new Stack<Passenger>(5);
		Stack<Passenger> hallway = new Stack<Passenger>(5);
		System.out.println("This is a new elevator!");
		elevatorRoute(input);
		
		
	}
	
	/**
	 * 
	 * @param elev1 Stack representing the passengers in the elevator
	 * @param input	Utility object for managing file input for simulation
	 */
	// TODO needs to keep running so long as there are passengers present in the hallway or on the elevator
	// 		  should not rely on ProcessInputLines in order to allow for multiple elevators
	public void elevatorRoute (ProcessInputLines input) {
		while (input.inputHasNext()) {
		//for (int i=1; i<50; i++) {
			//System.out.println(input.getLineFromInput());
			currentFloor+=changeFloor(goingUp);
			setDirection(currentFloor);
		}
	}
	
	/**
	 * Returns an int value corresponding to the vector of the elevator and
	 * 	prints a message to the user describing it.
	 * 
	 * @param goingUp Whether the elevator is going up
	 * @return Integer value of +1 or -1 indicating the vector of the elevator 
	 */
	private int changeFloor(boolean goingUp) {
		if (goingUp) {
			System.out.println(floorNames[currentFloor-1]+" floor! Next stop, " 
		+floorNames[currentFloor] + " floor! Going up!");
			return 1;	
			
		} else {
			System.out.println(floorNames[currentFloor-1]+" floor! Next stop, " 
		+floorNames[currentFloor-2] + " floor! Going down!");
			
			return -1;
		}
	}
	
	/**
	 * Sets the direction the elevator is heading in dependent on its location
	 * @param f Current floor the elevator is residing at
	 * {@link #goingUp} boolean Whether the elevator is heading upwards
	 */
	private void setDirection (int f) {
		if (f == 1) {
			goingUp = true;
			
		} else if (f == 5) {
			goingUp = false;
		}
	}
	
	/**
	 * 
	 * @param elev Stack representing the passengers in the elevator
	 * @param hallway Hallway into which passengers exit the elevator
	 * @param floor 
	 */
	private void checkPassFloor(Stack<Passenger> elev, Stack<Passenger> hallway, int floor) {
		for (int i=0; i<elev.size(); i++) {							// Iterate over passengers in elevator
			hallway.push(elev.pop());											// Move passengers into the hallway one at a time
			System.out.println(passengerInfo(hallway.peek(), true));
			
			if (hallway.peek().getFloorOut() == floor) {	// Check floor against floorOut for passenger in hallway
				hallway.pop();															// If this is their floorOut, have them leave
			}
			
		}
		
		for (int i=0; i<elev.size(); i++) {							// Iterate over passengers in hallway
			elev.push(hallway.pop());											// Move passengers who temporarily exited back into the elevator
			elev.peek().incrementTempExits();							// Increment the tempExit count of each passenger re-entering
			System.out.println(passengerInfo(elev.peek(), false));
		
		}
	}
	
	private void newPassengers(Stack<Passenger> elev, ProcessInputLines input) {
		if (elev.isEmpty() || elev.size() < elev.getLimit()) {
			//elev.push(new Passenger(input.getLineFromInput()));
			buttonsPressed[elev.peek().getFloorOut()-1] = true;
		}
		
	}
	
	/**
	 * Returns a String describing a passenger that is entering or leaving the elevator
	 * 
	 * @param pass	Passenger object for a passenger entering/leaving the elevator
	 * @param exit Boolean for whether a passenger is exiting the elevator
	 * @return String describing a passenger exiting including the number of temporary 
	 * 						exits, or a passenger entering and the floor they will exit on
	 */
	private String passengerInfo(Passenger pass, boolean exit) {
		if (exit) {
			return pass.getName()+" has exited the elevator. They have "
					+ "temporarily exited "+pass.getTempExits()+" time(s).";
		} else {
			return pass.getName()+" has entered the elevator. They will "
					+ "be exiting on floor "+pass.getFloorOut();
		}
	}
	
	public int getNumPassengers() {
		return numPassengers;
	}
	
}
