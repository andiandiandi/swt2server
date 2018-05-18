package utils;

import java.util.List;

import game.Card;

public class Player extends User{


	private List<Card> cards;
	private int team;
	
	public Player(String username) {
		super(username);
	}

	public void setCards(List<Card> cards){
		
	}
	
	public List<Card> getCards(){
		return cards;
	}
	
	public void removeCard(Card card){
		
	}
	
}
