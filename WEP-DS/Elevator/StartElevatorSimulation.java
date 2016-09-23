package Elevator;

public class StartElevatorSimulation {
	
	public static void main(String[] args) {
		ProcessInputLines input = new ProcessInputLines();
		Elevator elev1 = new Elevator(input);
		Elevator elev2 = new Elevator(input);
	}
	
}
