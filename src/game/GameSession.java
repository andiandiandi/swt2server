package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import entities.CardE;
import entities.Player;
import entities.PlayerComparator;
import gamestates.GameSessionState;
import gamestates.PlayerOrderState;
import gamestates.ShuffleState;
import javafx.event.ActionEvent;
import server.JSONActionsE;
import server.ClientWorker;
import server.JSONEventsE;
import server.JSONIngameAttributes;
import server.Lobby;
import server.ServerThread;

public class GameSession implements Runnable {

	// private Lobby lobby;
	private CardGame cardGame;

	private GameSessionState state;

	private List<Player> playerList;

	public GameSession(List<ClientWorker> clientList) {

		// this.lobby = lobby;

		playerList = new LinkedList<Player>();

		for (int i = 0; i < clientList.size(); i++) {
			playerList.add(new Player(clientList.get(i)));
		}

		cardGame = new CardGame(playerList);

	}

	@Override
	public void run() {

		notifyGameStart();

		// shuffle
		state = new ShuffleState(playerList, cardGame);
		state.execute();

		// reihenfolge der züge dem spieler mitteilen
		state = new PlayerOrderState(playerList, cardGame);
		state.execute();

		while (true) {

			for (Player player : playerList) {
				getNextMove(player);
			}

		}

	}

	private void getNextMove(Player player) {
		JSONObject json = new JSONObject();
		json.put(JSONActionsE.EVENT.name(), JSONEventsE.MAKEMOVE.name());
		json.put(JSONEventsE.MAKEMOVE.name(), "gogo");
		String notification = json.toString();

		player.sendMessage(notification);

		boolean again = true;
		
		do {
			String move = player.readMessage();
			JSONObject json2 = new JSONObject(move);
			String action = json2.getString(JSONActionsE.EVENT.name());
			if (action.equals(JSONEventsE.MAKEMOVE.name())) {
				JSONObject card = (JSONObject) json2.get(JSONIngameAttributes.CARD.name());
				System.out.println(card.get(CardE.WERTIGKEIT.name()));
				System.out.println(card.get(CardE.SYMBOL.name()));
				again = false;
			}
		} while (again);

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
