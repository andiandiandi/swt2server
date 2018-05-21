package entities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import game.Card;
import server.ClientWorker;

public class Player{


	private List<Card> playerCards;
	private Socket socket;
	
	private BufferedReader reader;
	private ClientWorker cw;
	
	public Player(ClientWorker cw) {
		this.cw = cw;
		socket = cw.getSocket();
		reader = cw.getReader();
	}

	
	public void setCards(List<Card> cards){
		playerCards = new LinkedList<Card>(cards);
	}
	
	public List<Card> getCards(){
		return playerCards;
	}
	
	public void removeCard(Card card){
		
	}
	
	public void sendMessage(String message){
		cw.sendMessage(message);
	}
	
	public String readMessage(){
		try {
			return reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
}
