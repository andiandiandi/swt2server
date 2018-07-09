package game;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import entities.Card;
import entities.GameWinnerObject;
import entities.GamemodeE;
import entities.Player;
import entities.SymbolE;
import entities.WertigkeitE;
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

		for (Player p : playerList) {
			stiche.put(p, new LinkedList());
		}

		gameMode = GameMode.getInstance();
	}


	Map<Player, Integer> getStichePoints() {

		Map<Player, Integer> points = new HashMap<Player, Integer>();

		for (Player temp : playerList)
			points.put(temp, calculatePoints(temp));
		
		return points;
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
				// Hat er ein Schweinchen?
				if (playerCards.contains(new Card(SymbolE.KARO, WertigkeitE.ASS, true))
						&& cards.get(j).getWertigkeit() == WertigkeitE.ASS
						&& cards.get(j).getSymbol() == SymbolE.KARO) {
					cards.get(j).setSchweinchen(true);
					for (Card c : playerCards) {
						if (c.getWertigkeit() == WertigkeitE.ASS && c.getSymbol() == SymbolE.KARO) {
							c.setSchweinchen(true);
						}
					}
				}
				playerCards.add(cards.get(index++));

			}
			playerList.get(i).setCards(new LinkedList<Card>(playerCards));
		}

	}

	public GameWinnerObject calculateWinner() {

		Map<Player, Integer> points = getStichePoints();

		int re = 0;
		int contra = 0;

		if (gameMode.getGameModeE() == GamemodeE.NORMAL) {

			for (Player player : points.keySet()) {
				if (player.isRe()) {
					re += points.get(player);
				} else {
					contra += points.get(player);
				}
			}
			//gamemode
		} else {

		}

		
		GameWinnerObject gwo = new GameWinnerObject();
		
		
		if (re > contra) {
			for (Player player : points.keySet()) {
				if (player.isRe())
					gwo.addWinner(player);
				else
					gwo.addLoser(player);
			}
			gwo.setWinnerScore(re);
			gwo.setLoserScore(contra);
		} else {
			for (Player player : points.keySet()) {
				if (!player.isRe())
					gwo.addWinner(player);
				else
					gwo.addLoser(player);
			}
			gwo.setWinnerScore(contra);
			gwo.setLoserScore(re);
		}

		return gwo;

	}

	public int calculatePoints(Player player) {

		List<Card> cardList = stiche.get(player);

		int sum = 0;

		for (Card card : cardList) {
			sum += card.getWertigkeit().getNumVal();
		}

		return sum;
	}

	public boolean validatePlayedCard(Card card, Player player) {
		return moveValidator.validatePlayedCard(card, player);
	}

	public void resetMoveValidator() {
		moveValidator.reset();
	}

	public void addRoundSpecificCard(Player player, Card card) {
		roundSpecificCards.put(player, card);
	}

	public Player evaluateRound() {

		Player to_return = gameMode.evaluateRound();
		assignStiche(to_return);
		
		return to_return;

	}

	public void assignStiche(Player player) {

		List<Card> temp = new LinkedList<Card>();

		for (Card card : roundSpecificCards.values()) {
			temp.add(card);
		}

		stiche.get(player).addAll(temp);

		roundSpecificCards.clear();

	}

	void setGameMode(CalculationMode mode) {
		gameMode.setCalculationMode(mode);
	}

	public Map<Player, Card> getRoundSpecificCards() {
		return roundSpecificCards;
	}

}
