package gamestates;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import game.CardGame;
import utils.Player;

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
	
	protected void writeMessage(Player player,String Message){
		try {
			out = new PrintWriter(player.getSocket().getOutputStream(),true);
			out.println(Message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
