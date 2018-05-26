package gamestates;

import java.util.List;

import org.json.JSONObject;

import entities.CardE;
import entities.Player;
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
			cardGame.addRoundSpecificCard(player,card);
			// send card to every other player
			cardBroadcaster.broadcast(playerList,player,card);
			
		}

		// decide who won that round
		System.out.println("before evaluation");
		Player player = cardGame.evaluateRound();
		System.out.println("after evaluation");
		System.out.println("notifying winner");
		notifyRoundWinner(player);
		System.out.println("after notifying winner");
		// update round integer
		round++;
		// check if game ended

	}

	private void notifyRoundWinner(Player player) {
		
		JSONObject json = new JSONObject();
		json.put(JSONActionsE.EVENT.name(), JSONEventsE.ROUNDWINNER.name());
		json.put(JSONEventsE.ROUNDWINNER.name(), player.getUsername());
		String notification = json.toString();
		
		for(Player receiver : playerList){			
			receiver.sendMessage(notification);
		}

		
	}



}
