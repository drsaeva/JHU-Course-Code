package Elevator;

public class Passenger {
	
	private String name;
	private int floorIn;
	private int floorOut;
	
	public Passenger(String name, int floorIn, int floorOut) {
		setName(name);
		setFloorIn(floorIn);
		setFloorOut(floorOut);
	}
	
	private void setName(String input){
		name = input;
	}
	
	public String getName() {
		return name;
	}

	private void setFloorIn(int input) {
		floorIn = input;
	}
	
	public int getFloorIn() {
		return floorIn;
	}
	
	private void setFloorOut(int input) {
		floorOut = input;
	}
	
	public int getFloorOut() {
		return floorOut;
	}
	
}
