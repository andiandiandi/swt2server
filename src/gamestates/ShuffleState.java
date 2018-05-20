package gamestates;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import entities.Player;
import game.Card;
import game.CardGame;
import server.ClientWorker;

public class ShuffleState extends GameSessionState {

	public ShuffleState(Map<Player,ClientWorker> playerList, CardGame cardGame) {
		super(playerList, cardGame);
	}

	@Override
	public void execute() {

		cardGame.shuffle();

		JSONArray jsonArr = null;
		JSONObject cards = null;
		JSONObject toSend = null;

		Card card = null;

		for (Player p : playerList.keySet()) {
			jsonArr = new JSONArray();
			toSend = new JSONObject();

			toSend.put("call", "shuffle");

			for (int i = 0; i < 10; i++) {
				cards = new JSONObject();
				card = p.getCards().get(i);
				cards.put("wertigkeit", card.getWertigkeit().name());
				cards.put("symbol", card.getSymbol().name());
				jsonArr.put(cards);
			}

			toSend.put("cards", jsonArr);
			
			playerList.get(p).getWriter().println(toSend.toString());
			playerList.get(p).getWriter().flush();
			
		}

	}

}
