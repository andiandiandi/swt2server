package game;

import java.util.List;

public abstract class TrumpfState {

	public abstract boolean validate(Card newCard, Card compareToPlayedCard, List<Card> playerDeck);

}
