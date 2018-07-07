package game;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import entities.Player;
import game.calculationMode.CalculationMode;

public class CardGame {

	private CardGenerator cardGenerator;

	private List<Player> playerList;

	private Map<Player, List<Card>> stiche;

	private Map<Player, Card> roundSpecificCards;

	private MoveValidator moveValidator;

	private GameMode gameMode;

	public CardGame(List<Player> playerList) {
		this.playerList = playerList;
		stiche = new HashMap<Player, List<Card>>();
		cardGenerator = new CardGenerator();
		roundSpecificCards = new HashMap<Player, Card>();
		moveValidator = new MoveValidator();

		gameMode = GameMode.getInstance();
	}

	public void updateCardGame() {
			
	}

	Map<Player, Integer> getStiche() {
		
		HashMap<Player,Integer> temp = new HashMap<Player,Integer>();
		int i=1;
		
		for(Player p : playerList)
			temp.put(p, 100+i++);
		
		return temp;
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
		return moveValidator.validatePlayedCard(card, player);
	}
	
	public void resetMoveValidator(){
		moveValidator.reset();
	}

	public void addRoundSpecificCard(Player player, Card card) {
		roundSpecificCards.put(player, card);
	}

	public Player evaluateRound() {

		Player to_return = gameMode.evaluateRound();
		roundSpecificCards.clear();
		return to_return;

	}

	void setGameMode(CalculationMode mode) {
		gameMode.setCalculationMode(mode);
	}

	public Map<Player, Card> getRoundSpecificCards() {
		return roundSpecificCards;
	}

}
