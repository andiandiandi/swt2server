package game.calculationMode;

import entities.Card;
import entities.Player;

public abstract class CalculationMode {
	
	protected Player winner;
	protected Card winnerCard;

	public abstract Player evaluateRound();
	
	public void setWinner(Player winner, Card winnerCard) {
		this.winner = winner;
		this.winnerCard = winnerCard;
	}; 
	
	public Player getWinner() {
		return winner;
	}
	public Card getWinnercard() {
		return winnerCard;
	}
}
