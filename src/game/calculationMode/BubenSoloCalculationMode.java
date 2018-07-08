package game.calculationMode;

import java.util.Iterator;
import java.util.Map;

import entities.Player;
import entities.WertigkeitE;
import game.Card;

/**
 * Nur Bube ist Trumpf, rest wird eingereiht
 * 
 * @author dominic
 *
 */
public class BubenSoloCalculationMode extends CalculationMode {

	private Map<Player, Card> roundSpecificCards;

	public BubenSoloCalculationMode(Map<Player, Card> roundSpecificCards) {
		this.roundSpecificCards = roundSpecificCards;
	}

	/**
	 * Wertet das Spiel aus Wenn die zweite Farbe
	 */
	@Override
	public Player evaluateRound() {
		// TODO Update CardGame nur Bube trumpf
		Iterator<Player> it = roundSpecificCards.keySet().iterator();

		winner = it.next();
		winnerCard = roundSpecificCards.get(winner);
		while (it.hasNext()) {
			Player temp = it.next();
			Card tempCard = roundSpecificCards.get(temp);
			// WinnerCard = Bube && temp = Bube
			if (winnerCard.getWertigkeit() == WertigkeitE.BUBE && tempCard.getWertigkeit() == WertigkeitE.BUBE) {
				if (tempCard.getSymbol().ordinal() > winnerCard.getSymbol().ordinal()) {
					setWinner(temp, tempCard);
				}
			} else if (winnerCard.getWertigkeit() != WertigkeitE.BUBE && tempCard.getWertigkeit() == WertigkeitE.BUBE) {
				// Winner != bube, tempCard = bube
				setWinner(temp, tempCard);
			} else if (winnerCard.getWertigkeit() != WertigkeitE.BUBE
					&& winnerCard.getSymbol() == tempCard.getSymbol()) {
				// Gleiche Symbole aka Farbe? winner != bube
				if (winnerCard.getWertigkeit().ordinal() < tempCard.getWertigkeit().ordinal()) {
					setWinner(temp, tempCard);
				}
			}
		}
		return winner;
	}
}
