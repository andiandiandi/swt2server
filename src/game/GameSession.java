package game;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import entities.Player;
import game.calculationMode.CalculationMode;
import gamestates.CardExchangeState;
import gamestates.GameModeState;
import gamestates.GameSessionState;
import gamestates.PlayerOrderState;
import gamestates.ShuffleState;
import server.ClientWorker;
import server.JSONActionsE;
import server.JSONEventsE;
import storage.Database;

public class GameSession implements Runnable {

	private CardGame cardGame;
	
	private GameSessionState state;

	private List<Player> playerList;

	private volatile boolean gameEnded = false;

	public GameSession(List<ClientWorker> clientList) {

		
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
		
		state = new GameModeState(playerList, cardGame);
		state.execute();

		CalculationMode gameMode = ((GameModeState)state).getGameMode();
		cardGame.setGameMode(gameMode);
		
		
		
		state = new CardExchangeState(playerList, cardGame);

		
		while (!gameEnded) {

			// equals 1 round
			if(((CardExchangeState) state).getRound()<=2){
				state.execute();				
			}else
				gameEnded = true;

			
		}

		// spielauswertung (wer hat gewonnen)
			//stiche auswerten etc.
			//persistieren
			persistPunktestand();
		
		
	}

	
	private void persistPunktestand(){
		
		System.out.println("persisting now");
		Map<Player,Integer> stiche = cardGame.getStiche();
		
		Database.getInstance().persist(stiche);
		
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
