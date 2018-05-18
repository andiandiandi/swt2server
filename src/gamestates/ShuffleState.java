package gamestates;

import java.util.List;

import game.Card;
import game.CardGame;
import utils.Player;

public class ShuffleState extends GameSessionState{

	public ShuffleState(List<Player> playerList,CardGame cardGame) {
		super(playerList,cardGame);
	}

	@Override
	public void execute() {
		cardGame.shuffle();
		for(Player p : playerList){
			String cards = "go ";
			for(Card c : p.getCards()){
				cards += c.toString() + ",";
			}
			writeMessage(p, cards);
		}
	}

	
	
}
