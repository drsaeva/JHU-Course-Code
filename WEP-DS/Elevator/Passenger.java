/**
 * Class: Passenger
 * Properties: name, floorIn, floorOut, tempExits
 * Methods: getPassengersFromInput, parameter setters/getters
 * 
 * Implementation of passenger class for elevator passengers
 * 
 * @author David Saeva
 */

package Elevator;

public class Passenger {
	
	private String name;		//passenger name
	private int floorIn  = 0;	//floor passenger entered elevator
	private int floorOut = 0;	//floor passenger exits elevator
	private int tempExits = 0;	//times passenger exited temporarily before destination
	
	/**
	 * Constructor Passenger
	 * @param input
	 * Instantiates new Passenger object with property values derived from input String
	 */
	public Passenger(String input) {
		getPassengerFromInput(input);
	}
	
	/**
	 * Method getPassengerFromInput 
	 * @param input
	 * Parses class property values from input string and calls respective setters
	 */
	private void getPassengerFromInput(String input) {
		
		StringBuilder charsInName = new StringBuilder();
		
		for (char c : input.toCharArray()) {	//iterate over each character in the input String
			
			if (Character.isAlphabetic(c)) {	//make name char array from alpha chars
				charsInName.append(c);
			
			} else if (Character.isDigit(c) && floorIn <= 0) {	//store 1st digit as floorIn
				setFloorIn(Character.getNumericValue(c));
			
			} else if (Character.isDigit(c) && floorIn >= 0 && floorOut <= 0) {	//store 2nd digit as floorOut
				setFloorOut(Character.getNumericValue(c));
			}
		}
		setName(charsInName.toString());	//stringify name char array and assign to name property
	}
	
	/**
	 * Method setName
	 * @param inputName
	 * Sets passenger property for name from input String
	 */
	private void setName(String inputName){
		name = inputName;
	}
	
	/**
	 * Method getName
	 * @return name
	 * Returns passenger property for name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Method setFloorIn
	 * @param h
	 * Sets passenger property for floor entered from input int
	 */
	private void setFloorIn(int h) {
		floorIn = h;
	}
	
	/**
	 * Method getFloorIn
	 * @return floorIn
	 * Returns passenger property for floor entered
	 */
	public int getFloorIn() {
		return floorIn;
	}
	
	/**
	 * Method setFloorOut
	 * @param i
	 * Sets passenger property for floor exited from input int
	 */
	private void setFloorOut(int i) {
		floorOut = i;
	}
	
	/**
	 * Method getFloorOut
	 * @return floorOut
	 * Returns passenger property for floor exited
	 */
	public int getFloorOut() {
		return floorOut;
	}
	
	/**
	 * Method incrementTempExits
	 * Adds 1 to the tempExits counter for passenger
	 */
	public void incrementTempExits() {
		tempExits++;
	}
	
	/**
	 * Method getTempExits
	 * @return tempExits
	 * Returns current number of times passenger has temporarily exited
	 */
	public int getTempExits() {
		return tempExits;
	}
	
}
