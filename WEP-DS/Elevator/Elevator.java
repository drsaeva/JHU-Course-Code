package Elevator;

public class Elevator {
	
	private int currentFloor = 1;												// Current floor the elevator is stopped at
	boolean goingUp = true;															// Whether the elevator is heading upwards
	String[] floorNames = {"1st", "2nd", "3rd", 				// 5-element String array with floor names
			"4th", "5th" };
	
	Stack<Passenger> elev = new Stack<Passenger>(5);		// Stack representing the space within the elevator
	Stack<Passenger> hallway = new Stack<Passenger>(5);	// Stack representing the hallway passengers temporarily
																											//	exit into 
	
	/**
	 * 
	 * @param input 
	 */
	public Elevator(Stack<Passenger> data) {
		while (!data.isEmpty()) {	
			if (!elev.isEmpty()) {
				checkPassFloor(currentFloor);
			}
			
			while (checkIncomingPass(data)) {
						newPassenger(data);
			
			}
			
			elevatorRoute();
		}
		checkPassFloor(currentFloor);
		elevatorRoute();
		System.out.println("Simulation complete.");
	}
	

	
	/**
	 * 
	 * @param elev1 Stack representing the passengers in the elevator
	 * @param input	Utility object for managing file input for simulation
	 */
	public void elevatorRoute () {
			currentFloor+=changeFloor(goingUp);
			setDirection(currentFloor);
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
		+floorNames[currentFloor] + " floor! Going up!\n");
			return 1;	
			
		} else {
			System.out.println(floorNames[currentFloor-1]+" floor! Next stop, " 
		+floorNames[currentFloor-2] + " floor! Going down!\n");
			
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
	private void checkPassFloor(int floor) {
		int currentPass = elev.size();
		int passInHallway = 0;
		if (!elev.isEmpty()) {
			for (int i=0; i<currentPass; i++) {							// Iterate over passengers in elevator
				hallway.push(elev.pop());											// Move passengers into the hallway one at a time

				if (hallway.peek().getFloorOut() == floor) {	// Check floor against floorOut for passenger in hallway
					System.out.println(passengerInfo(						// If this is their floorOut, print their name
							hallway.peek(), true));									//   # of temp exits, and have them leave
					hallway.pop();
				}
				passInHallway = hallway.size();
			
			}
		
			for (int i=0; i<passInHallway; i++) {						// Iterate over passengers in hallway
				elev.push(hallway.pop());											// Move passengers who temporarily exited back into the elevator
				elev.peek().incrementTempExits();							// Increment the tempExit count of each passenger re-entering
			
		
			}
		}
	}
	
	/**
	 * Adds a new passenger to the elevator provided there's space, otherwise prints
	 * 	a message stating that passenger took the stars
	 * @param pass New passenger to wants to enter the elevator
	 * @return
	 */
	private void newPassenger(Stack<Passenger> data) {
		if (elev.size() < elev.getLimit()) {
			elev.push(data.pop());
			System.out.println(passengerInfo(elev.peek(), false));
		} else {
			System.out.println("The elevator is full! " + data.pop().getName() +" is unable to get on and is taking the stairs");
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
	
	private boolean checkIncomingPass(Stack<Passenger> data) {
		try{
			if (currentFloor == data.peek().getFloorIn()) {
				return true;
			}
		} catch (java.util.NoSuchElementException e) {
			System.out.println("Final passenger!");
		}
		return false;
	}
	
	public boolean passInElevator() {
		return elev.isEmpty();
	}

}
