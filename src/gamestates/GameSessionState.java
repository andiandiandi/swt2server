package gamestates;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Map;

import entities.Player;
import game.CardGame;
import server.ClientWorker;

public abstract class GameSessionState{
	
	protected Map<Player,ClientWorker> playerList;
	protected CardGame cardGame;

	
	public GameSessionState(Map<Player,ClientWorker> playerList,CardGame cardGame) {
		this.playerList=playerList;
		this.cardGame=cardGame;
	}
	
	public abstract void execute();
	
	

}
