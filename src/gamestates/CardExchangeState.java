package gamestates;

import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import entities.CardE;
import entities.Player;
import entities.PlayerComparator;
import entities.SymbolE;
import entities.WertigkeitE;
import game.Card;
import game.CardGame;
import game.MoveCoordinator;
import server.JSONActionsE;
import server.JSONEventsE;
import server.JSONIngameAttributes;

public class CardExchangeState extends GameSessionState {

	private int round;

	private MoveCoordinator moveCoordinator;
	private CardBroadcaster cardBroadcaster;

	public CardExchangeState(List<Player> playerList, CardGame cardGame) {
		super(playerList, cardGame);

		moveCoordinator = new MoveCoordinator(cardGame);
		cardBroadcaster = new CardBroadcaster();
		round = 1;
	}

	@Override
	public void execute() {

		for (Player player : playerList) {

			// tell player to do move and start time
			Card card = moveCoordinator.getMove(player);
			cardGame.addRoundSpecificCard(player, card);
			// send card to every other player
			cardBroadcaster.broadcast(playerList, player, card);

		}

		// decide who won that round
		Player player = cardGame.evaluateRound();
		// playerlist sortieren
		rotatePlayerList(player);

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		notifyRoundWinner(player);
		// update round integer
		round++;
		//reset move validator
		cardGame.resetMoveValidator();
		// check if game ended

	}

	// a b c d
	// b c d a
	
	public int getRound() {
		return round;
	}

	/**
	 * Rotiert den Gewinner nach vorne
	 * 
	 * @param winner
	 */
	private void rotatePlayerList(Player winner) {

		if (winner.getOrder() == 1)
			return;

		if (winner.getOrder() == 2) 
			Collections.rotate(playerList, 1);
		if (winner.getOrder() == 3)
			Collections.rotate(playerList, 2);
		if (winner.getOrder() == 4)
			Collections.rotate(playerList, 3);
	}

	/**
	 * Informiert Player, wer gewonnen hat
	 * 
	 * @param player
	 */
	private void notifyRoundWinner(Player player) {

		JSONObject json = new JSONObject();
		json.put(JSONActionsE.EVENT.name(), JSONEventsE.ROUNDWINNER.name());
		json.put(JSONEventsE.ROUNDWINNER.name(), player.getUsername());
		String notification = json.toString();

		for (Player receiver : playerList) {
			receiver.sendMessage(notification);
		}

	}

}
