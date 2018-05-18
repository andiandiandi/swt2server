package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import entities.Player;
import gamestates.GameSessionState;
import gamestates.ShuffleState;
import server.ClientWorker;
import server.ServerThread;

public class GameSession implements Runnable {

	private int maxRoundTime;
	private List<Player> playerList;

	private ServerThread serverThread;

	private CardGame cardGame;
	private PrintWriter out;
	private BufferedReader in;
	private GameSessionState state;
	
	public GameSession(List<ClientWorker> clientList, ServerThread serverThread) {
		this.serverThread = serverThread;
		playerList = new LinkedList<Player>();

		out = null;
		in = null;

		for (ClientWorker cw : clientList) {
			Player p = new Player(cw.getUser());
			playerList.add(p);
		}

		cardGame = new CardGame(playerList);
	}

	@Override
	public void run() {

		//shuffle
		state = new ShuffleState(playerList,cardGame);
		state.execute();
		
		

		while (true) {

			
			
			

		}

	}

	
	
}
