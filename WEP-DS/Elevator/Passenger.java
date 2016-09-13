/**
 * Represents a passenger on an elevator.
 * @author David Saeva
 */

package Elevator;

public class Passenger {
	
	private String name;		//passenger name
	private int floorIn  = 0;	//floor passenger entered elevator
	private int floorOut = 0;	//floor passenger exits elevator
	private int tempExits = 0;	//times passenger exited temporarily before destination
	
	/**
	 * Class Constructor for Passenger. Creates new Passenger with 
	 * 	property values derived from input.
	 * @param input the String from which Passenger data is derived
	 * @see #getPassengerFromInput(String)
	 */
	public Passenger(String input) {
		getPassengerFromInput(input);
	}
	
	/**
	 * Parses data about a Passenger that will ride the elevator.
	 *  Sets properties with extracted data values.
	 * @param input the String from which Passenger data is derived
	 * @see #setName(String)
	 * @see #setFloorIn(int)
	 * @see #setFloorOut(int)
	 */
	private void getPassengerFromInput(String input) {
		
		StringBuilder newName = new StringBuilder();
		
		for (char c : input.toCharArray()) {	//iterate over each character in the input String
			
			if (Character.isAlphabetic(c)) {	//make name char array from alpha chars
				newName.append(c);
			
			} else if (Character.isDigit(c) && floorIn <= 0) {	//store 1st digit as floorIn
				setFloorIn(Character.getNumericValue(c));
			
			} else if (Character.isDigit(c) && floorIn >= 0 && floorOut <= 0) {	//store 2nd digit as floorOut
				setFloorOut(Character.getNumericValue(c));
			}
		}
		setName(newName.toString());	//stringify name char array and assign to name property
	}
	
	/**
	 * Sets the Passenger's name to the input String.
	 * @param name {@link #name}
	 */
	private void setName(String name){
		this.name = name;
	}
	
	/**
	 * Gets the name of this Passenger.
	 * @return {@link #name}
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the floor the Passenger entered the elevator.
	 * @param floorIn {@link #floorIn}
	 */
	private void setFloorIn(int floorIn) {
		this.floorIn = floorIn;
	}
	
	/**
	 * Gets the floor the Passenger entered the elevator.
	 * @return {@link #floorIn}
	 */
	public int getFloorIn() {
		return floorIn;
	}
	
	/**
	 * Sets the floor this Passenger will exit the elevator.
	 * @param floorOut {@link #floorOut}
	 */
	private void setFloorOut(int floorOut) {
		this.floorOut = floorOut;
	}
	
	/**
	 * Gets the floor this Passenger will exit the elevator.
	 * @return {@link #floorOut}
	 */
	public int getFloorOut() {
		return floorOut;
	}
	
	/**
	 * Adds 1 to the number of times this Passenger temporarily exited the elevator.
	 * {@link #tempExits}
	 */
	public void incrementTempExits() {
		this.tempExits++;
	}
	
	/**
	 * Gets the number of times this Passenger has temporarily exited the elevator.
	 * @return {@link #tempExits}
	 */
	public int getTempExits() {
		return tempExits;
	}
	
}
