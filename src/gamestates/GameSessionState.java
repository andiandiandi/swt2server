package gamestates;

import java.util.List;

import entities.Player;
import game.CardGame;

public abstract class GameSessionState {

	protected List<Player> playerList;
	protected CardGame cardGame;

	public GameSessionState(List<Player> playerList, CardGame cardGame) {
		this.playerList = playerList;
		this.cardGame = cardGame;
	}

	public abstract void execute();

}
