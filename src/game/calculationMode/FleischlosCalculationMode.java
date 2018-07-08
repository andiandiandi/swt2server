package game.calculationMode;

import java.util.Iterator;
import java.util.Map;

import entities.Card;
import entities.Player;

/**
 * Keine Trümpfe oder Trumpffarben
 * 
 * @author dominic
 *
 */
public class FleischlosCalculationMode extends CalculationMode {

	private Map<Player, Card> roundSpecificCards;

	public FleischlosCalculationMode(Map<Player, Card> roundSpecificCards) {
		this.roundSpecificCards = roundSpecificCards;
	}

	@Override
	public Player evaluateRound() {
		// TODO Update CardGame
		Iterator<Player> it = roundSpecificCards.keySet().iterator();

		winner = it.next();
		winnerCard = roundSpecificCards.get(winner);
		while (it.hasNext()) {
			Player temp = it.next();
			Card tempCard = roundSpecificCards.get(temp);
			if (winnerCard.getSymbol() == tempCard.getSymbol()) {
				if (winnerCard.getWertigkeit().ordinal() < tempCard.getWertigkeit().ordinal()) {
					setWinner(temp, tempCard);
				}
			}
		}
		return winner;
	}
}
