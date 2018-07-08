package entities;

import java.util.HashMap;
import java.util.Map;

public class GameWinnerObject {

	private Map<Player, Integer> winner;
	private Map<Player, Integer> loser;
	
	public GameWinnerObject() {
		winner = new HashMap<Player,Integer>();
		loser = new HashMap<Player,Integer>();
	}

	public void addWinner(Player player, Integer score) {
		winner.put(player, score);
	}

	public void addLoser(Player player, Integer score) {
		loser.put(player, score);
	}

	public Map<Player, Integer> getWinner() {
		return winner;
	}

	public Map<Player, Integer> getLoser() {
		return loser;
	}

}
