package blackjackGame;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a generic participant in a Blackjack game, providing basic 
 * 	functionality useful to both a player and a dealer.
 * 
 * @author David Saeva
 * @
 * @param stay Boolean representing whether the participant wants to stay, ending their hand
 * @param hand List containing the Card objects in the participant's hand
 */
public abstract class Participant {
	
	public boolean wonHand = false;
	public boolean stay = false;
	public List<Card> hand;
	
	public Participant() {}
	
	public void getNewHand(Deck d) {
		hand = new ArrayList<Card>();
		hand.add(d.dealCard());
		hand.add(d.dealCard());
	}
	
	public int getHandValue() {
		int handVal = 0;
		for (Card c : hand) {
			handVal+= c.getValue();
		}
		return handVal;
	}
	
	public void getHandContents() {
		for (Card c : hand) {
			System.out.println(c.getName());
		}
	}
	
	/**
	 * Check to see if a given player's hand contains any Aces. If their hand value is
	 * 	over 21 and they contain an ace, change each ace's value from 11 to 1 until 
	 * 	their hand's value is no longer greater than 21.
	 */
	public void lowAceIfOver21() {
		for (Card c : hand) {
			if (getHandValue() > 21 && c.getName().equals("Ace")) {
				c.highAceVal(false);
			}		
		}	
	}

}
