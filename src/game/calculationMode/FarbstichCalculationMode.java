package game.calculationMode;

import java.util.Iterator;
import java.util.Map;

import entities.Player;
import entities.SymbolE;
import game.Card;
/**
 * Reiner Farbstich, Trumpffarbe wird im Konstruktor festgelegt
 * @author dominic
 *
 */
public class FarbstichCalculationMode extends CalculationMode {

	private Map<Player, Card> roundSpecificCards;
	private SymbolE farbe;
	// TODO Update CardGame
	
	public FarbstichCalculationMode(SymbolE farbe, Map<Player, Card> roundSpecificCards) {
		this.roundSpecificCards = roundSpecificCards;
		this.farbe = farbe;
	}

	/**
	 * Auswerten nach Farbe (nur die gewählte Farbe ist trumpf), Keine Trümpfe
	 */
	@Override
	public Player evaluateRound() {
		Iterator<Player> it = roundSpecificCards.keySet().iterator();

		winner = it.next();
		winnerCard = roundSpecificCards.get(winner);
		while (it.hasNext()) {
			Player temp = it.next();
			Card tempCard = roundSpecificCards.get(temp);
			// Beides Trumpffarbe?
			if (winnerCard.getSymbol() == farbe && tempCard.getSymbol() == farbe) {
				// TempCard > WinnerCard
				if (winnerCard.getWertigkeit().ordinal() < tempCard.getWertigkeit().ordinal()) {
					setWinner(temp, tempCard);
				}
			} else if (winnerCard.getSymbol() != farbe && tempCard.getSymbol() == farbe) {
				// Winner keine Trumpffarbe, Temp Trumpffarbe
				setWinner(temp, tempCard);
			}
			// Beide Fehl
			if (winnerCard.getSymbol() == tempCard.getSymbol()) {
				// TempCard > WinnerCard
				if (winnerCard.getWertigkeit().ordinal() < tempCard.getWertigkeit().ordinal()) {
					setWinner(temp, tempCard);
				}
			}
		}
		return winner;
	}
}
