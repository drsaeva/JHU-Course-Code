/**
 * Class: Passenger
 * Properties: name, floorIn, floorOut
 * Methods: getPassengersFromInput, parameter setters/getters
 * 
 * Implementation of passenger class for elevator passengers
 * 
 * @author David Saeva
 */

package Elevator;

public class Passenger {
	
	private String name;	//passenger name
	private int floorIn;	//floor passenger entered elevator
	private int floorOut;	//floor passenger exited elevator
	
	public Passenger(String input) {
		getPassengerFromInput(input);
	}
	
	/**
	 * Method getPassengerFromInput 
	 * @param input
	 * Parses values for passenger name, floor entered, and floor exited from data line in input file
	 * Sets values of passenger object to those parsed values
	 */
	private void getPassengerFromInput(String input) {
		
		StringBuilder charsInName = new StringBuilder();
		floorIn = 0;
		floorOut = 0;
		
		for (char c : input.toCharArray()) {	//iterate over each character in the input String
			
			if (Character.isAlphabetic(c)) {	//make name char array from alpha chars
				charsInName.append(c);
			
			} else if (Character.isDigit(c) && floorIn <= 0) {	//store 1st digit as floorIn
				setFloorIn(Character.getNumericValue(c));
			
			} else if (Character.isDigit(c) && floorIn >= 0 && floorOut <= 0) {	//store 2nd digit as floorOut
				setFloorOut(Character.getNumericValue(c));
			}
		}
		setName(charsInName.toString());	//stringify name char array and assign to name
	}
	
	/**
	 * Method setName
	 * @param inputName
	 */
	private void setName(String inputName){
		name = inputName;
	}
	
	/**
	 * Method getName
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method setFloorIn
	 * @param h
	 */
	private void setFloorIn(int h) {
		floorIn = h;
	}
	
	/**
	 * Method getFloorIn
	 * @return floorIn
	 */
	public int getFloorIn() {
		return floorIn;
	}
	
	/**
	 * Method setFloorOut
	 * @param i
	 */
	private void setFloorOut(int i) {
		floorOut = i;
	}
	
	/**
	 * Method getFloorOut
	 * @return floorOut
	 */
	public int getFloorOut() {
		return floorOut;
	}
	
}
