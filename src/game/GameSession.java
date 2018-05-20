package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import entities.Player;
import gamestates.GameSessionState;
import gamestates.ShuffleState;
import server.ClientWorker;
import server.ServerThread;

public class GameSession implements Runnable {

	private int maxRoundTime;

	private ServerThread serverThread;

	private CardGame cardGame;
	private PrintWriter out;
	private BufferedReader in;
	private GameSessionState state;

	private Map<Player,ClientWorker> playerMap;
	
	public GameSession(List<ClientWorker> clientList, ServerThread serverThread) {
		this.serverThread = serverThread;
		
		playerMap = new HashMap<Player,ClientWorker>();

		out = null;
		in = null;

		for (ClientWorker cw : clientList) {
			Player p = new Player(cw.getUser());
			playerMap.put(p, cw);
		}

		cardGame = new CardGame(new LinkedList<Player>(playerMap.keySet()));
	}

	@Override
	public void run() {

		for(ClientWorker p : playerMap.values()){
			JSONObject json = new JSONObject();
			json.put("event","gameStarted");
			p.getWriter().println(json.toString());
			p.getWriter().flush();
		}
		
		// shufflen
		state = new ShuffleState(playerMap, cardGame);
		state.execute();

		// anfragen ob jemand solo/hochzeit etc. spielen will

		while (true) {

			// frage spieler nacheinander nach ihrem zug

		}

	}

}
