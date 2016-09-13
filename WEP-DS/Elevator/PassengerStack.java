package Elevator;

public class PassengerStack {

/**
 * Class: PassengerStack
 * Variables: currentPassengers, passengerLimit, front
 * Methods: PassengerStack, push, pop, peek, numberOfPassengers, isEmpty
 * 
 * Implementation of a stack that holds passengers entering/leaving an elevator
 * 
 * @author David Saeva
 */
	
	private Passenger[] currentPassengers;	//instantiated Passenger stack
	private final int passengerLimit = 5;	//maximum capacity of the elevator
	private int front;	//index of the Passenger closest to the elevator door in the Passenger stack
	
	
	/**
	 * Constructor elevator
	 * Instantiates new empty Passenger stack representing the 
	 * 	passengers in the elevator
	 */
	public PassengerStack() {
		currentPassengers = new Passenger[passengerLimit];
		front = -1;
	}
	
	/**
	 * Method push
	 * @param passenger is added to the front of the queue inside the elevator
	 */
	public void push(Passenger passenger) {
		if (front > passengerLimit - 1) {
			System.out.println("The elevator is already at maximum capacity!");
			System.exit(1);
		}
		
		currentPassengers[++front] = passenger;
	}
	
	
	/**
	 * Method pop
	 * @return Passenger currently in the front of the elevator and removes them
	 * 	from the Passenger stack
	 * Prints error message if no passenger is inside and forces simulation exit
	 */
	public Passenger pop() {
		if (isEmpty()) {
			System.out.println("There are no passengers to leave the elevator!");
			System.exit(1);
		}
		
		return currentPassengers[front--];
	
	}
	
	/**
	 * Method peek
	 * @return Passenger currently in the front of the elevator without removing
	 * Prints error message if no passenger is inside and forces simulation exit
	 */
	public Passenger peek() {
		if (isEmpty()) {
			System.out.println("There are no passengers in the elevator!");
			System.exit(1);
		}
		
		return currentPassengers[front];
	
	}
	
	/**
	 * Method numberOfPassengers
	 * @return current amount of Passengers in the elevator
	 */
	public int numberOfPassengers() {
		return front+1;
	}
	
	/**
	 * Method isEmpty
	 * @return true/false assessment of the elevator being empty
	 */
	public boolean isEmpty() {
		if (front == -1) {
			return true;
		}
		
		return false;
	}
	
}
