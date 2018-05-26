package game;

public class CardDeck {

	
	private Card lastPlayedCard;
	
	
	public CardDeck() {
		lastPlayedCard = null;
	}

	public boolean validatePlayedCard(Card card) {
		
		if(card == null)
			return false;
		else
			return true;
		
	}
	
}
