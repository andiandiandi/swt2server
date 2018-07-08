package game;

import java.util.List;

import entities.Card;

public abstract class TrumpfState {

	public abstract boolean validate(Card newCard, Card compareToPlayedCard, List<Card> playerDeck);

}
