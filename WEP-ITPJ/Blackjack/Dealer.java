package blackjackGame;

public class Dealer extends Participant {

	public Dealer() {

	}
	
	public void dealerPlays(Deck d) {
		while (getHandValue() < 17) {
			hand.add(d.dealCard());
			visibleDealerHand();
		}
	}
	
	/**
	 * Gets the contents and value of the dealer's hand when one card is face down 
	 * @return 
	 */
	public void visibleDealerHand() {
		
		int handVal = 0;
		System.out.println("Dealer's hand: \n(Face-down)");
		for (int i=1; i<hand.size(); i++) {
			System.out.println(hand.get(i));
			handVal += hand.get(i).getValue();
		}
		
		System.out.println("Dealer's visible total: " + handVal);;
	}
	
}
