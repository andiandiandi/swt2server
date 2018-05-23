package gamestates;

import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import entities.Player;
import entities.PlayerComparator;
import game.CardGame;
import server.JSONActionsE;
import server.JSONEventsE;
import server.JSONIngameAttributes;

public class PlayerOrderState extends GameSessionState {

	public PlayerOrderState(List<Player> playerList, CardGame cardGame) {
		super(playerList, cardGame);
		setOrder();
	}

	private void setOrder() {
		// zurzeit: wer zuerst gequed hat, fängt an
		for (int i = 0; i < playerList.size(); i++)
			playerList.get(i).setOrder(i+1);
		Collections.sort(playerList, new PlayerComparator());
	}

	@Override
	public void execute() {

		//build order in json
		JSONObject order = new JSONObject();
		JSONObject toSend = new JSONObject();

		toSend.put(JSONActionsE.EVENT.name(), JSONEventsE.MOVEORDER.name());

		for (Player p : playerList) {

			order.put(p.getOrder() + "", p.getUsername());

		}
		
		toSend.put(JSONIngameAttributes.ORDER.name(), order);

		//send order to players
		for (Player p : playerList) {
			p.sendMessage(toSend.toString());
		}

	}

}
