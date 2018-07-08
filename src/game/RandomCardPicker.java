package game;

import entities.Card;
import entities.Player;

public class RandomCardPicker {

	private Player player;
	private CardGame cardGame;

	public RandomCardPicker(Player player, CardGame cardGame) {
		this.player = player;
		this.cardGame = cardGame;
	}
	
	public Card getCard(){
		
		for(Card card : player.getCards()){
			if(cardGame.validatePlayedCard(card,player)){
				return player.removeCard(card);
			}
		}
		
		return null;
	}

}
