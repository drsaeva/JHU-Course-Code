package blackjackGame;

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
	
	/**
	 * Constructor to generate a new card object based on a passed-in suit and name.
	 * 	Suits provide no function aside from arbitrarily helping to set the maximum
	 * 	number of a give card name (ie 2, Jack) in the deck to 4.
	 * @param suit	String describing the suit of the card
	 * @param name	String describing the face of the card (ie 2, Jack)
	 */
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
	
	/**
	 * Sets the value of a given card based on the name of that card.
	 * 	If the card's name is able to be parsed into integer format, the
	 * 	value is determined from that. If the card is an Ace, it defaults 
	 *  to 11. Otherwise its value is set to 10.
	 * @param name String representation of the card's name (i.e. Jack)
	 */
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
	public void highAceVal(boolean aceHigh) {
		if (this.name.equals("Ace") && aceHigh) {
			this.value = 11;
		} else if (this.name.equals("Ace") && !aceHigh) {
			this.value = 1;
		}
	}
	
	
}
