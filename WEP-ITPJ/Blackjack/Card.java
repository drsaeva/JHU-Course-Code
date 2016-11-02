package Blackjack;

/**
 * This Class represents a card and its properties as it exists in a standard 52-card deck. 
 * @author David Saeva
 * @version 1.0
 * @param suit String representation of the card's suit
 * @param name String representation of what the card's face is (i.e. 2, Jack)
 * @param value Int storing the value of the card's face (i.e. 2 = 2, Jack = 10)
 */
public class Card {
	
	private String suit;
	private String name;
	private int value;
	
	
	public Card(String suit, String name) {
		setSuit(suit);
		setName(name);
		setValue(name);
	}
	
	private void setSuit(String suit) {
		this.suit = suit;
	}
	
	public String getSuit() {
		return suit;
	}
	
	private void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	private void setValue(String name) {
		try {
			this.value = Integer.valueOf(name);
		} catch (NumberFormatException e) {}
		if (this.value == 0) {
			if (name.equals("Ace")) {
				this.value = 11;
			} else {
				this.value = 10;
			}
			
			
		}
	}
	
	/**
	 * Returns the int value of the card
	 * @return value Int value specific to the card's name
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Enables the shifting of an Ace's value from 11 to 1 and vice versa, provided this method is
	 * 	called on an Ace and depending on the boolean value of the passed-in arg
	 * @param aceHigh boolean argument for whether a high value of the ace is desired
	 */
	public void changeAceVal(boolean aceHigh) {
		if (this.name.equals("Ace") && aceHigh) {
			this.value = 11;
		} else if (this.name.equals("Ace") && !aceHigh) {
			this.value = 1;
		}
	}
	
	
}
