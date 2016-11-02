package blackjackGame;
import java.util.Scanner;

/**
 * Class representing a human player in a Blackjack game. Extends the basic functionality
 * 	provided by the Participant abstract class. Provides methods in order to determine how 
 * 	much money the player wants to play Blackjack with, how much they wish to bet on a hand,
 *  whether they want to hit or stay, and getting/adjusting the money pool's size.
 *  
 * @author  David Saeva
 * @param queryHitStay String representing a question to the player on whether to hit or stay 
 * @param stayOrHit String array containing the valid answers to the question above
 * @param pool int value of the player's money pool for Blackjack, adjusted throughout play
 * @param bet int value of the player's bet for the current hand of Blackjack
 */
public class Player extends Participant {
	
	private static String queryHitStay = "Would you like to Hit or Stay (H/S)? ";
	private static String[] stayOrHit = {"S", "H"};
	private int pool = 0;
	public int bet = 0;
	
	/**
	 * Constructor for the player. Calls the setPool() method, initiating the associated
	 * 	query found in the method in order to set a positive value of pool
	 */
	public Player() {
		setPool();
	}
	
	/**
	 * Queries the user as to whether they'd like to hit or stay, calling
	 * 	the static method getUserInput() from the Utility class with the 
	 *	given arguments. Deals a new card if the user Hits, otherwise changes
	 *	the boolean value of stay for the player to true.
	 * @param d The Deck of Cards with which the Blackjack game is being played
	 */
	public void hitOrStay(Deck d) {
		boolean query = Utility.getUserInput(queryHitStay, stayOrHit);
		if (query) {
			this.stay = query;
		} else {
			hand.add(d.dealCard());
		}
	}
	
	/**
	 * Query the user for the amount of money they'd like to bet on this hand
	 * 	of Blackjack. Makes a new scanner, requests user input, and parses the 
	 * 	next integer value provided, handling exceptions for bad inputs along 
	 * 	with denying non-positive inputs or inputs greater than the size of the
	 * 	player's pool.
	 */
	public void makeBet() {
		System.out.println("Please place your bet for the hand: ");
		Scanner t = new Scanner(System.in);
		bet = 0;
		while (bet < 1 || bet > pool) {
			//while (t.hasNext()) {
				//t.nextLine();
				try {
					bet = t.nextInt();
				} catch(java.util.InputMismatchException e) {
					System.out.println("Please place a bet in numerical, whole dollars.");
					t.nextLine();
				}
				if (bet != 0) {
					break;
				}
			//}
			
			/*if (bet > 1 && bet <= pool) {
				break;
			} else {*/
				System.out.println("Invalid bet - you must bet at least $1, "
						+ "and cannot bet more than your current amount of money."
						+ " Please make another bet: ");
			//}
			
		}
		
		t.close();
		pool -= bet;
	}
	
	/**
	 * Query the user for the amount of money they'd like to play Blackjack with.
	 * 	Makes a new scanner, requests user input, and parses the next integer value 
	 * 	provided, handling exceptions for bad inputs along with denying non-positive inputs.
	 */
	private void setPool() {
		System.out.println("How large of a betting pool would you like to play with?");
		Scanner s = new Scanner(System.in);
		while (s.hasNext()) {
			try {
				pool = s.nextInt();
			} catch(java.util.InputMismatchException e) {
				System.out.println("Please provide an amount in numerical, whole dollars.");
				s.nextLine();
				}
				if (pool > 0) {
					s.nextLine();
					break;
				} else {
					System.out.println("You must start with a pool of $1 or greater. Please "
							+ "provide another amount: ");
					s.nextLine();
				}
			}
		s.close();
	}
	
	/**
	 * Gets the current size of the player's money pool
	 * @return pool Int value for the player's money pool
	 */
	public int getPool() {
		return pool;
	}
	
	/**
	 * Adds a players winnings to the pool in the event they win the hand,
	 * 	equal to their original bet plus that value again.
	 */
	public void addWinnings() {
		pool += (2*bet);
	}
	
	/**
	 * Returns the player's bet to the pool in the event that the hand is a push.
	 */
	public void isPush() {
		pool += bet;
	}
	
}
