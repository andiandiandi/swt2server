package game;

import java.util.Iterator;
import java.util.Map;

import entities.Player;
import entities.SymbolE;

public class NormalCalculationMode extends CalculationMode {

	private Map<Player, Card> roundSpecificCards;

	public NormalCalculationMode(Map<Player, Card> roundSpecificCards) {
		this.roundSpecificCards = roundSpecificCards;
	}

	/**
	 * 1. Trumpf H10 > D > B > > KA Ass > KA 10 > KA K 2. Fehl Ass > 10 > K
	 * 
	 * @return
	 */
	@Override
	public Player evaluateRound() {

		Iterator<Player> it = roundSpecificCards.keySet().iterator();

		Player winner = it.next();
		Card winnerCard = roundSpecificCards.get(winner);
		while (it.hasNext()) {
			Player temp = it.next();
			Card tempCard = roundSpecificCards.get(temp);
			// Trumpf schlägt Fehl
			if (tempCard.isTrumpf() && !winnerCard.isTrumpf()) {
				winner = temp;
				winnerCard = tempCard;
			}
			if (winnerCard.isTrumpf() && tempCard.isTrumpf()) {
				// Wenn die Trümpfe verschieden sind
				if (!winnerCard.equals(tempCard)) {
					switch (compareTrumpfSymbole(winnerCard, tempCard)) {
					case -1:
						break;
					case 0:
						break;
					case 1:
						winner = temp;
						winnerCard = tempCard;
						break;
					}
				}
			}
		}
		return roundSpecificCards.keySet().iterator().next();
	}
	/**
	 * Welcher Trumpf ist stärker?
	 * @param a 1. Karte
	 * @param b 2. Karte
	 * @return 0 = gleich, -1 = Karte a, 1 = Karte b
	 */
	private int compareTrumpfSymbole(Card a, Card b) {
		if(a.getWertigkeit() == b.getWertigkeit()) {
			if(a.getSymbol().ordinal() > b.getSymbol().ordinal()) {
				return -1;
			} else {
				return 1;
			}
		}
		return 0;
	}

}
