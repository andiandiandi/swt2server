package game.calculationMode;

import entities.Player;
import game.Card;

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
