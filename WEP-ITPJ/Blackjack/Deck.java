package blackjackGame;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class representing a Deck of Cards, encapsulating a List representation of
 *  those objects. It contains a method allowing access to the remove() method for
 *  said list. The constructor properly generates a deck containing each card in each 
 *  suit, and shuffles it for use.
 *  
 * @author dsaeva
 * @param suits String array containing the 4 suits found in a given deck
 * @param cardsInSuit String array containing the names of the 13 cards found in each suit
 * @param deckOfCards List of Card objects in a given deck.
 */
public class Deck {

	public static String[] suits = {"Hearts", "Diamonds", "Spades", "Clubs"};
	public static String[] cardsInSuit = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
	private List<Card> deckOfCards;
	
	/**
	 * Constructor for the Deck, instantiating deckOfCards as an ArrayList to represent
	 * 	the resizable nature of a true deck of Cards that shrinks with the removal of
	 *  cards. Iterates 52 times over the cardsInSuits String array for each suit in the suits
	 *  String array, adding a new Card object to the ArrayList for each iteration. Deck is
	 *  shuffled via the Collections.shuffle() method.
	 */
	public Deck() {
		deckOfCards = new ArrayList<Card>();
		
		for (int i=1; i<52; i++) {
			deckOfCards.add(new Card(suits[(i-1)/13], cardsInSuit[(i%13)]));
		}
		
		Collections.shuffle(deckOfCards);
	}	
 
	/**
	 * Deals a card from the deck, permanently removing it from the deck
	 * @return Card representing the card on top of the deck
	 */
	public Card dealCard() {
		return deckOfCards.remove(0);
	}
	
}
