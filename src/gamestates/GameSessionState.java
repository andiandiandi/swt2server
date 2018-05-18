package gamestates;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.json.JSONObject;

import entities.Player;
import game.CardGame;

public abstract class GameSessionState{
	
	protected List<Player> playerList;
	protected CardGame cardGame;
	protected PrintWriter out;
	protected BufferedReader in;
	
	public GameSessionState(List<Player> playerList,CardGame cardGame) {
		this.playerList=playerList;
		this.cardGame=cardGame;
	}
	
	public abstract void execute();
	
	

}
