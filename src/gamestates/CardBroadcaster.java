package gamestates;

import java.util.List;

import org.json.JSONObject;

import entities.CardE;
import entities.Player;
import game.Card;
import server.JSONActionsE;
import server.JSONEventsE;
import server.JSONIngameAttributes;

public class CardBroadcaster {

	public void broadcast(List<Player> playerList, Player player, Card card) {

		for (Player temp_player : playerList) {
			if (temp_player != player) {
				notifyPlayer(temp_player, card, player.getUsername());
			}
		}

	}

	private void notifyPlayer(Player player, Card card, String playedBy) {

		JSONObject json = new JSONObject();
		JSONObject json_card = new JSONObject();
		
		json_card.put(CardE.WERTIGKEIT.name(), card.getWertigkeit());
		json_card.put(CardE.SYMBOL.name(), card.getSymbol());
		json_card.put(CardE.TRUMPF.name(), card.isTrumpf());
		
		
		json.put(JSONActionsE.EVENT.name(), JSONEventsE.CARDBROADCAST.name());
		json.put(JSONIngameAttributes.CARD.name(), json_card);
		
		json.put(JSONIngameAttributes.PLAYEDBY.name(), playedBy);
		
		String notification = json.toString();

		player.sendMessage(notification);
		
	}
}
