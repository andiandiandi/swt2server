package entities;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GameWinnerObject {

	private List<Player> winner;
	private List<Player> loser;
	
	private int score_winner = 0;
	private int score_loser = 0;
	
	public GameWinnerObject() {
		winner = new LinkedList<Player>();
		loser = new LinkedList<Player>();
	}

	public void addWinner(Player player) {
		winner.add(player);
	}

	public void addLoser(Player player) {
		loser.add(player);
	}

	public List<Player> getWinner() {
		return winner;
	}

	public List<Player> getLoser() {
		return loser;
	}
	
	public void setWinnerScore(int score){
		score_winner = score;
	}
	
	public void setLoserScore(int score){
		score_loser = score;
	}
	
	public int getWinnerScore(){
		return score_winner;
	}
	
	public int getLoserScore(){
		return score_loser;
	}

}