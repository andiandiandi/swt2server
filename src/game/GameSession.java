package game;

import java.util.LinkedList;
import java.util.List;

import utils.Player;

public class GameSession {

	private int maxRoundTime;
	private List<Player> playerList;
	
	
	private CardGame cardGame;
	
	public GameSession() {
		playerList = new LinkedList<Player>();
		cardGame = new CardGame(playerList);
	}
	
	
	
	
}
