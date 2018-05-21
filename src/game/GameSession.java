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
import javafx.event.ActionEvent;
import server.JSONActionsE;
import server.ClientWorker;
import server.JSONEventsE;
import server.Lobby;
import server.ServerThread;

public class GameSession implements Runnable {

	private Lobby lobby;
	private CardGame cardGame;

	private GameSessionState state;

	private List<Player> playerList;

	public GameSession(List<ClientWorker> clientList, Lobby lobby) {

		this.lobby = lobby;

		playerList = new LinkedList<Player>();

		for (ClientWorker cw : clientList) {
			playerList.add(new Player(cw));
		}

		cardGame = new CardGame(playerList);

	}

	@Override
	public void run() {

		notifyGameStart();
		state = new ShuffleState(playerList, cardGame);
		state.execute();

	}

	private void notifyGameStart() {

		JSONObject json = new JSONObject();
		json.put(JSONActionsE.EVENT.name(), JSONEventsE.GAMESTART.name());
		String notification = json.toString();

		for (Player p : playerList) {
			p.sendMessage(notification);
		}
	}

}
