package game;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import entities.Player;

public class CardGame {

	private CardGenerator cardGenerator;

	private List<Player> playerList;

	private Map<Player, List<Card>> stiche;

	private Map<Player,Card> roundSpecificCards;

	private MoveValidator cardDeck;

	public CardGame(List<Player> playerList) {
		this.playerList = playerList;
		stiche = new HashMap<Player, List<Card>>();
		cardGenerator = new CardGenerator();
		roundSpecificCards = new HashMap<Player,Card>();
		cardDeck = new MoveValidator();
	}

	public void updateCardGame() {

	}

	private Map<Player, Integer> calculateStiche() {
		return null;
	}

	/**
	 * Mischt das Kartenspiel
	 */
	public void shuffle() {
		List<Card> cards = cardGenerator.createCards();
		Collections.shuffle(cards);
		int index = 0;
		List<Card> playerCards = new LinkedList<Card>();
		for (int i = 0; i < 4; i++) {
			playerCards.clear();
			for (int j = 0; j < 10; j++) {
				playerCards.add(cards.get(index++));
			}
			playerList.get(i).setCards(new LinkedList<Card>(playerCards));
		}
	}

	public Player calculateWinner() {
		return null;
	}

	public boolean validatePlayedCard(Card card, Player player) {
		return cardDeck.validatePlayedCard(card, player);
	}

	public void addRoundSpecificCard(Player player, Card card) {
		roundSpecificCards.put(player,card);
	}
	/**
	 * 1. Trumpf H10 > B > D > > KA Ass > KA 10 > KA K 
	 * 2. Fehl  Ass > 10 > K
	 * @return
	 */
	public Player evaluateRound() {

		Iterator<Player> it = roundSpecificCards.keySet().iterator();
		
		Player winner = it.next();
		
//		while(it.hasNext()){
//			Player temp = it.next();
//			if(roundSpecificCards.get(winner). < roundSpecificCards.get(temp)) {
//				winner = temp;
//			}
//		}
		
		
		
		
		
		return null;
	}

}
