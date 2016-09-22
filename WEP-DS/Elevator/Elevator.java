package Elevator;

public class Elevator {
	
	private int currentFloor = 1;
	ProcessInputLines input = new ProcessInputLines();
	int[] buttonsPressed = new int[5];
	
	private void elevatorRoute (Stack<Passenger> elev1) {
		if (currentFloor == 1) {
			if (elev1.isEmpty() || elev1.size() < elev1.getLimit()) {
				elev1.push(new Passenger(input.getLineFromInput()));
				buttonsPressed[elev1.peek().getFloorOut()-1] = 1;
				
			}
			
		}
		
	}
	
	private void checkPassFloor(Stack<Passenger> elev, int floor) {
		Stack<Passenger> hallway = new Stack(5);
		
		for (int i=0; i<elev.size(); i++) {							//iterate over passengers in elevator
			hallway.push(elev.pop());											//move passengers into the hallway one at a time
			
			if (hallway.peek().getFloorOut() == floor) {	//check floor against floorOut for passenger in hallway
				hallway.pop();															//if this is their floorOut, have them leave
			}
			
		}
		
		for (int i=0; i<elev.size(); i++) {							//iterate over passengers in hallway
			elev.push(hallway.pop());											//move passengers who temporarily exited back into the elevator
			elev.peek().incrementTempExits();							//increment the tempExit count of each passenger re-entering
		
		}
	}
		
	private String printPassengerInfo(Passenger pass, boolean reEntry) {
		
		String info = new String(pass.getName()+" has entered the elevator.");
		
		if (reEntry) {
			info.concat(" They have temporarily exited the elevator "+pass.getTempExits()+" times.");
		} else {
			info.concat(" They will be exiting on floor "+pass.getFloorOut());
		}
		
		return info;
	}
}
