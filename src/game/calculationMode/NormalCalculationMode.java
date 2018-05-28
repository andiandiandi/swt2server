package game.calculationMode;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import entities.Player;
import entities.SymbolE;
import entities.WertigkeitE;
import game.Card;

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

		Iterator<Player> it = new TreeSet(roundSpecificCards.keySet()).iterator();

		winner = it.next();
		winnerCard = roundSpecificCards.get(winner);
		while (it.hasNext()) {
			System.out.println(winner.getUsername());
			Player temp = it.next();
			Card tempCard = roundSpecificCards.get(temp);
			// Trumpf schlägt Fehl
			if (tempCard.isTrumpf() && !winnerCard.isTrumpf()) {
				setWinner(temp, tempCard);
			}
			if (winnerCard.isTrumpf() && tempCard.isTrumpf()) {
				// Wenn die Trümpfe verschieden sind
				if (!winnerCard.equals(tempCard)) {
					if (compareTrumpfSymbole(winnerCard, tempCard) == 1) {
						setWinner(temp, tempCard);
					}
				}
			}
			// Beides Fehl: Wenn tempCard Symbol gleich, dann überprüfen, sonst bleibt der
			// winner
			if (!winnerCard.isTrumpf() && !tempCard.isTrumpf()) {
				if (tempCard.getSymbol() == winnerCard.getSymbol()) {
					if (tempCard.getWertigkeit().getNumVal() > winnerCard.getWertigkeit().getNumVal()) {
						setWinner(temp, tempCard);
					}
				}
			}
		}
		
		return winner;
	}

	/**
	 * Welcher Trumpf ist stärker?
	 * 
	 * @param a
	 *            1. Karte
	 * @param b
	 *            2. Karte
	 * @return 0 = gleich, -1 = Karte a, 1 = Karte b
	 */
	private int compareTrumpfSymbole(Card a, Card b) {
		if (a.getWertigkeit() == b.getWertigkeit()) {
			if (a.getSymbol().ordinal() > b.getSymbol().ordinal()) {
				return -1;
			} else if (a.getSymbol().ordinal() < b.getSymbol().ordinal()) {
				return 1;
			} else
				return 0;
		}
		int[][] valuesTrumpf = new int[4][12];
		// Dame = 20 Bube = 10 H10 = 100 KaroAss=3 Karo10=2 KaroK = 1
		// Kreuz = 3, Pik = 2 Herz = 1 Karo = 0
		for (int i = 0; i <= 3; i++) {
			valuesTrumpf[i][WertigkeitE.BUBE.getNumVal()] = 10 + i;
			valuesTrumpf[i][WertigkeitE.DAME.getNumVal()] = 20 + i;
		}
		valuesTrumpf[SymbolE.HERZ.ordinal()][WertigkeitE.ZEHN.getNumVal()] = 100;
		valuesTrumpf[SymbolE.KARO.ordinal()][WertigkeitE.KOENIG.getNumVal()] = 1;
		valuesTrumpf[SymbolE.KARO.ordinal()][WertigkeitE.ZEHN.getNumVal()] = 2;
		// Schweinchen höchster Trumpf
		if(a.isSchweinchen() || b.isSchweinchen()) {
			valuesTrumpf[SymbolE.KARO.ordinal()][WertigkeitE.ASS.getNumVal()] = 150;
		} else {
			valuesTrumpf[SymbolE.KARO.ordinal()][WertigkeitE.ASS.getNumVal()] = 3;
		}
		
		if (valuesTrumpf[a.getSymbol().ordinal()][a.getWertigkeit().getNumVal()] < valuesTrumpf[b.getSymbol().ordinal()][b
				.getWertigkeit().getNumVal()]) {
			return 1;
		} else if (valuesTrumpf[a.getSymbol().ordinal()][a.getWertigkeit()
				.getNumVal()] > valuesTrumpf[b.getSymbol().ordinal()][b.getWertigkeit().getNumVal()]) {

			return -1;
		}

		return 0;
	}

}
