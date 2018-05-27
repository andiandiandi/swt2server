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

import javax.smartcardio.CardException;

import org.json.JSONObject;

import entities.CardE;
import entities.Player;
import entities.PlayerComparator;
import gamestates.CardExchangeState;
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

	private CardGame cardGame;
	private GameMode gameMode;
	
	private GameSessionState state;

	private List<Player> playerList;

	private volatile boolean gameEnded = false;

	public GameSession(List<ClientWorker> clientList) {

		gameMode = GameMode.getInstance();
		
		playerList = new LinkedList<Player>();

		for (int i = 0; i < clientList.size(); i++) {
			playerList.add(new Player(clientList.get(i)));
		}

		cardGame = new CardGame(playerList);

	}

	@Override
	public void run() {

		notifyGameStart();
		gameMode.setCalculationMode(new NormalCalculationMode());
		
		
		// shuffle
		state = new ShuffleState(playerList, cardGame);
		state.execute();

		// reihenfolge der züge dem spieler mitteilen
		state = new PlayerOrderState(playerList, cardGame);
		state.execute();

		state = new CardExchangeState(playerList, cardGame);

		//checkForFlushes();
		
		while (!gameEnded) {

			// equals 1 round
			state.execute();

		}

		// spielauswertung (wer hat gewonnen)

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
