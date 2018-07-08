package entities;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import game.Card;
import server.ClientWorker;

public class Player implements Comparable<Player>{

	private List<Card> playerCards;
	private Socket socket;
	
	private boolean re;

	private ClientWorker cw;

	private int order = -1;

	public Player(ClientWorker cw) {
		this.cw = cw;
		socket = cw.getSocket();
	}

	public void setCards(List<Card> cards) {
		playerCards = new LinkedList<Card>(cards);
		
		if(playerCards.contains(new Card(SymbolE.KREUZ,WertigkeitE.DAME,true))){
			re = true;
		}
	}

	public List<Card> getCards() {
		return playerCards;
	}

	public Card removeCard(Card card) {
		playerCards.remove(card);
		return card;
	}

	public void sendMessage(String message) {
		cw.sendMessage(message);
	}

	public String getUsername(){
		return cw.getUsername();
	}
	
	public String readMessage() {
		return cw.readMessage();
	}

	public void setOrder(int order){
		this.order=order;
	}
	
	public int getOrder() {
		return order;
	}

	public boolean isRe() {
		return re;
	}

	public void setRe(boolean re) {
		this.re = re;
	}

	@Override
	public int compareTo(Player o) {
		if(this.getOrder()>o.getOrder())
			return 1;
		else if(this.getOrder()<o.getOrder())
			return -1;
		else return 0;
	}
	

}
