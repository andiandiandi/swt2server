package game;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.json.JSONObject;

import entities.Card;
import entities.CardE;
import entities.Player;
import entities.SymbolE;
import entities.WertigkeitE;
import server.JSONActionsE;
import server.JSONAttributesE;
import server.JSONEventsE;
import server.JSONIngameAttributes;

public class MoveCoordinator {

	private CardGame cardGame;
	private RandomCardPicker randomCardPicker;
	private JSONIngameAttributes feedback;
	private volatile boolean valid;

	public MoveCoordinator(CardGame cardGame) {

		this.cardGame = cardGame;

	}

	public Card getMove(Player player) {

		Card card = null;
		valid = false;
		feedback = null;

		notifyPlayerToMove(player, JSONIngameAttributes.GETMOVE);
		card = getCardFromPlayer(player);
		valid = testCardForValidity(card,player);

		if (!valid) {

			do {
				notifyPlayerToMove(player, JSONIngameAttributes.INVALID);
				card = getCardFromPlayer(player);
				valid = testCardForValidity(card, player);
				System.out.println(card + " tested for valid: " + valid);
			} while (!valid);

		}

		if(valid)
			feedback = JSONIngameAttributes.VALID;
		
		player.removeCard(card);
		sendResponseToPlayer(player, card, feedback);

		return card;

	}

	private void sendResponseToPlayer(Player player, Card card, JSONIngameAttributes attribute) {
		JSONObject json = new JSONObject();
		JSONObject jsonCard = new JSONObject();

		jsonCard.put(CardE.WERTIGKEIT.name(), card.getWertigkeit());
		jsonCard.put(CardE.SYMBOL.name(), card.getSymbol());
		jsonCard.put(CardE.TRUMPF.name(), card.isTrumpf());

		json.put(JSONActionsE.EVENT.name(), JSONEventsE.MAKEMOVE.name());
		json.put(JSONEventsE.MAKEMOVE.name(), attribute.name());
		json.put(JSONIngameAttributes.CARD.name(), jsonCard);
		String notification = json.toString();

		player.sendMessage(notification);
	}

	private boolean testCardForValidity(Card card, Player player) {
		return cardGame.validatePlayedCard(card,player);
	}

	private void notifyPlayerToMove(Player player, JSONIngameAttributes attribute) {

		JSONObject json = new JSONObject();
		json.put(JSONActionsE.EVENT.name(), JSONEventsE.MAKEMOVE.name());
		json.put(JSONEventsE.MAKEMOVE.name(), attribute.name());
		String notification = json.toString();

		player.sendMessage(notification);

	}

	private Card getCardFromPlayer(Player player) {

		Card to_return = null;

		do {
			String move = player.readMessage();
			JSONObject json2 = new JSONObject(move);
			String action = json2.getString(JSONActionsE.EVENT.name());
			if (action.equals(JSONEventsE.MAKEMOVE.name())) {
				if (json2.has((JSONEventsE.MAKEMOVE.name()))) {
					if (json2.getString(JSONEventsE.MAKEMOVE.name()).equals(JSONIngameAttributes.CARD.name())) {
						JSONObject jsonCard = (JSONObject) json2.get(JSONIngameAttributes.CARD.name());
						WertigkeitE wertigkeit = WertigkeitE.valueOf(jsonCard.get(CardE.WERTIGKEIT.name()).toString());
						SymbolE symbol = SymbolE.valueOf(jsonCard.get(CardE.SYMBOL.name()).toString());
						boolean trumpf = jsonCard.getBoolean(CardE.TRUMPF.name());

						feedback = JSONIngameAttributes.VALID;
						to_return = new Card(symbol, wertigkeit,trumpf);

					} else if (json2.getString(JSONEventsE.MAKEMOVE.name())
							.equals(JSONIngameAttributes.TIMEEXPIRED.name())) {

						Card pickedCard = new RandomCardPicker(player, cardGame).getCard();
						feedback = JSONIngameAttributes.TIMEEXPIRED;
						return pickedCard;

					}
				}

			}
		} while (to_return == null);

		return to_return;

	}

}
