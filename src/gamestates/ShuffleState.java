package gamestates;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import entities.CardE;
import entities.Player;
import game.Card;
import game.CardGame;
import server.JSONActionsE;
import server.JSONAttributesE;
import server.ClientWorker;
import server.JSONEventsE;

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

			toSend.put(JSONActionsE.EVENT.name(), JSONEventsE.SHUFFLE.name());

			for (int i = 0; i < 10; i++) {
				cards = new JSONObject();
				card = p.getCards().get(i);
				cards.put(CardE.WERTIGKEIT.name(), card.getWertigkeit().name());
				cards.put(CardE.SYMBOL.name(), card.getSymbol().name());
				cards.put(CardE.TRUMPF.name(), card.isTrumpf());
				
				jsonArr.put(cards);
			}

			toSend.put(JSONAttributesE.CARDS.name(), jsonArr);

			p.sendMessage(toSend.toString());
		}

	}

}
