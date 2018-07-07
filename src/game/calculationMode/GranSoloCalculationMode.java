package game.calculationMode;

import java.util.Iterator;
import java.util.Map;

import entities.Player;
import entities.WertigkeitE;
import game.Card;

/**
 * Dame und Bube ist Trumpf, rest wird eingereiht
 * 
 * @author dominic
 *
 */
public class GranSoloCalculationMode extends CalculationMode {

	private Map<Player, Card> roundSpecificCards;

	public GranSoloCalculationMode(Map<Player, Card> roundSpecificCards) {
		this.roundSpecificCards = roundSpecificCards;
	}

	@Override
	public Player evaluateRound() {
		// TODO Update CardGame nur Dame und Bube Trumpf
		Iterator<Player> it = roundSpecificCards.keySet().iterator();

		winner = it.next();
		winnerCard = roundSpecificCards.get(winner);
		while (it.hasNext()) {
			Player temp = it.next();
			Card tempCard = roundSpecificCards.get(temp);
			// WinnerCard = dame && temp = Dame
			if (winnerCard.getWertigkeit() == WertigkeitE.DAME && tempCard.getWertigkeit() == WertigkeitE.DAME) {
				if (tempCard.getSymbol().ordinal() > winnerCard.getSymbol().ordinal()) {
					setWinner(temp, tempCard);
				}
			} else if (winnerCard.getWertigkeit() == WertigkeitE.BUBE && tempCard.getWertigkeit() == WertigkeitE.BUBE) {
				// Bube und Bube
				if (tempCard.getSymbol().ordinal() > winnerCard.getSymbol().ordinal()) {
					setWinner(temp, tempCard);
				}
			} else if (winnerCard.getWertigkeit() == WertigkeitE.BUBE && tempCard.getWertigkeit() == WertigkeitE.DAME) {
				// Winner = Bube, tempCard = DAME
				setWinner(temp, tempCard);
			} else if (winnerCard.getWertigkeit() != WertigkeitE.DAME && winnerCard.getWertigkeit() != WertigkeitE.BUBE
					&& winnerCard.getSymbol() == tempCard.getSymbol()) {
				// Gleiche Symbole aka Farbe? winner != DAME/BUBE
				if (winnerCard.getWertigkeit().ordinal() < tempCard.getWertigkeit().ordinal()) {
					setWinner(temp, tempCard);
				}
			}
		}
		return winner;
	}
}
