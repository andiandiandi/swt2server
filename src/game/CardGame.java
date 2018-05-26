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

	private Map<Player, List<Card>> stiche;

	private Map<Card, Player> roundSpecificCards;

	private CardDeck cardDeck;

	public CardGame(List<Player> playerList) {
		this.playerList = playerList;
		stiche = new HashMap<Player, List<Card>>();
		cardGenerator = new CardGenerator();
		roundSpecificCards = new HashMap<Card, Player>();
		cardDeck = new CardDeck();
	}

	public void updateCardGame() {

	}

	private Map<Player, Integer> calculateStiche() {
		return null;
	}

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

	public boolean validatePlayedCard(Card card) {
		return cardDeck.validatePlayedCard(card);
	}

	public void addRoundSpecificCard(Player player, Card card) {
		roundSpecificCards.put(card, player);
	}

	public Player evaluateRound() {

		Player to_return = null;

		Card temp = null;

		for (Card card : roundSpecificCards.keySet()) {
			// code to evaluate winner
			temp = card;
			break;
		}
		// example: player 1 always wins
		to_return = roundSpecificCards.get(temp);

		return to_return;
	}

}
