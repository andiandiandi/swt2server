package game.calculationMode;

import java.util.Iterator;
import java.util.Map;

import entities.Card;
import entities.Player;
import entities.WertigkeitE;

/**
 * Nur Dame ist Trumpf, rest wird eingereiht
 * 
 * @author dominic
 *
 */
public class DamenSoloCalculationMode extends CalculationMode {

	private Map<Player, Card> roundSpecificCards;

	public DamenSoloCalculationMode(Map<Player, Card> roundSpecificCards) {
		this.roundSpecificCards = roundSpecificCards;
	}

	/**
	 * Wertet das Spiel aus Wenn die zweite Farbe
	 */
	@Override
	public Player evaluateRound() {
		// TODO Update CardGame nur Dame trumpf
		Iterator<Player> it = roundSpecificCards.keySet().iterator();

		winner = it.next();
		winnerCard = roundSpecificCards.get(winner);
		while (it.hasNext()) {
			Player temp = it.next();
			Card tempCard = roundSpecificCards.get(temp);
			// WinnerCard = Bube && temp = Dame
			if (winnerCard.getWertigkeit() == WertigkeitE.DAME && tempCard.getWertigkeit() == WertigkeitE.DAME) {
				if (tempCard.getSymbol().ordinal() > winnerCard.getSymbol().ordinal()) {
					setWinner(temp, tempCard);
				}
			} else if (winnerCard.getWertigkeit() != WertigkeitE.DAME && tempCard.getWertigkeit() == WertigkeitE.DAME) {
				// Winner != DAME, tempCard = DAME
				setWinner(temp, tempCard);
			} else if (winnerCard.getWertigkeit() != WertigkeitE.DAME
					&& winnerCard.getSymbol() == tempCard.getSymbol()) {
				// Gleiche Symbole aka Farbe? winner != DAME
				if (winnerCard.getWertigkeit().ordinal() < tempCard.getWertigkeit().ordinal()) {
					setWinner(temp, tempCard);
				}
			}
		}
		return winner;
	}
}
