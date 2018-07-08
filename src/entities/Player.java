package entities;

import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import server.ClientWorker;

public class Player implements Comparable<Player> {

	private List<Card> playerCards;
	private Socket socket;
	
	private boolean re;
	private boolean solo;
	private List<ActionCallE> ansagen;

	private ClientWorker cw;

	private int order = -1;

	public Player(ClientWorker cw) {
		this.cw = cw;
		socket = cw.getSocket();
		solo = false;
		re = false;
		ansagen = new ArrayList<ActionCallE>();
	}

	public void addAnsage(ActionCallE a) {
		ansagen.add(a);
	}

	public List<ActionCallE> getAnsagen() {
		return ansagen;
	}

	public void setCards(List<Card> cards) {
		playerCards = new LinkedList<Card>(cards);
		
		if(playerCards.contains(new Card(SymbolE.KREUZ,WertigkeitE.DAME,true))){
			re = true;
			System.out.println("PLAYER: " + getUsername() + " is re: " + re);	
		}
	}

	public List<Card> getCards() {
		return playerCards;
	}

	public boolean isSolo() {
		return solo;
	}

	public void setSolo(boolean solo) {
		this.solo = solo;
	}

	public Card removeCard(Card card) {
		playerCards.remove(card);
		return card;
	}

	public void sendMessage(String message) {
		cw.sendMessage(message);
	}

	public String getUsername() {
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
		if (this.getOrder() > o.getOrder())
			return 1;
		else if (this.getOrder() < o.getOrder())
			return -1;
		else
			return 0;
	}

}
