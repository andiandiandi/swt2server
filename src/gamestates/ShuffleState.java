package gamestates;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import entities.Player;
import game.Card;
import game.CardGame;

public class ShuffleState extends GameSessionState {

	public ShuffleState(List<Player> playerList, CardGame cardGame) {
		super(playerList, cardGame);
	}

	@Override
	public void execute() {

		cardGame.shuffle();

		JSONArray jsonArr = null;
		JSONObject cards = null;
		JSONObject toSend = null;

		Card card = null;

		for (Player p : playerList) {
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
			try {
				out = new PrintWriter(p.getSocket().getOutputStream(), true);
				out.println(toSend.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
