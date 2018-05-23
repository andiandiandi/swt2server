package game;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import entities.Player;

public class CardGame {

	
	private CardGenerator cardGenerator;
	
	private List<Player> playerList;
	
	private Map<Player,List<Card>> stiche;
	
	public CardGame(List<Player> playerList) {
		this.playerList=playerList;
		stiche = new HashMap<Player,List<Card>>();
		cardGenerator = new CardGenerator();
	}
	

	public void updateCardGame(){
		
	}
	
	private Map<Player,Integer> calculateStiche(){
		return null;
	}
	
	public void shuffle(){
		List<Card> cards = cardGenerator.createCards();
		Collections.shuffle(cards);
		int index = 0;
		List<Card> playerCards = new LinkedList<Card>();
		for(int i=0;i<4;i++){
			playerCards.clear();
			for(int j=0;j<10;j++){
				playerCards.add(cards.get(index++));
			}
			playerList.get(i).setCards(new LinkedList<Card>(playerCards));
		}
	}
	
	public Player calculateWinner(){
		return null;
	}

	
	
}
