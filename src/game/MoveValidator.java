package game;

import entities.Player;

public class MoveValidator {

	private Card compareCard;
	private TrumpfState state;

	public MoveValidator() {
		compareCard = null;
		state = new NormalTrumpfState();
	}

	public boolean validatePlayedCard(Card card,Player player) {

		
		if(compareCard == null){
			compareCard = new Card(card.getSymbol(),card.getWertigkeit(),card.isTrumpf());
			return true;
		}
		
		return state.validate(card, compareCard, player.getCards());
		
		
	}
	
	public void reset(){
		compareCard = null;
	}

}
