package entities;

import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import game.Card;

public class Player extends User{


	private List<Card> playerCards;
	private int team;
	
	public Player(String username,Socket socket){
		super(username,socket);
	}
	
	public Player(User user) {
		super(user.getUsername(),user.getSocket());
	}

	public void setCards(List<Card> cards){
		playerCards = new LinkedList<Card>(cards);
	}
	
	public List<Card> getCards(){
		return playerCards;
	}
	
	public void removeCard(Card card){
		
	}
	
}
