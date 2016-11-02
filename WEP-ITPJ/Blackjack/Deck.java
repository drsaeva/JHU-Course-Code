package Blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

	public static String[] suits = {"Hearts", "Diamonds", "Spades", "Clubs"};
	public static String[] cardsInSuit = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
	public List<Card> deckOfCards;
	
	public Deck() {
		deckOfCards = new ArrayList<Card>();
		
		for (int i=1; i<52; i++) {
			deckOfCards.add(new Card(suits[(i-1)/13], cardsInSuit[(i%13)]));
		}
		
		Collections.shuffle(deckOfCards);
	}	
 
	public Card dealCard() {
		return deckOfCards.remove(0);
	}
	
}
