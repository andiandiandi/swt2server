package game;

import java.util.List;

import server.ServerThread;
import utils.Player;

public class GameSession implements Runnable{

	private int maxRoundTime;
	private List<Player> playerList;
	
	private ServerThread serverThread;
	
	private CardGame cardGame;
	
	public GameSession(List<Player> playerList,ServerThread serverThread) {
		this.playerList=playerList;
		this.serverThread=serverThread;
		cardGame = new CardGame(playerList);
	}

	@Override
	public void run() {

		cardGame.shuffle();
		
		
		
		
	}
	
	
	
	
}
