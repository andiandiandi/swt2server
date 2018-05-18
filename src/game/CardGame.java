package game;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import entities.Player;

public class CardGame {

	
	private CardGenerator cardGenerator;
	
	private List<Player> playerList;
	
	private Map<Player,List<Card>> stiche;
	private Map<Player, Card> board;
	private Map<Player, List<Card>> assignedCards;
	
	private Player roundStarter;
	
	
	public CardGame(List<Player> playerList) {
		this.playerList=playerList;
		stiche = new HashMap();
		cardGenerator = new CardGenerator();
		board=new HashMap(4);
		assignedCards=new HashMap();
	}
	

	public void updateCardGame(){
		
	}
	
	public boolean validPlay(Player p, Card card) {
		//	Check card: Ob eine Trumpfkarte gespielt wurde oder das Symbol, zu dem des Spieleröffners, passt
		if(!card.isTrumpf()	&&	card.getSymbol() !=	board.get(roundStarter).getSymbol()) {
			
			if(assignedCards.get(p).contains(board.get(roundStarter).getSymbol())) {
				System.out.println("Zug nicht zulässig. Sie besitzen noch eine spielbare Karte");
				return false;
			}
			else {
				board.put(p, card);
				return true;
			}
		}
		return true;
	}
	
	public void playedCard(Player p, Card card) {
		/*
		 * 	Regel: Spieler 1 legt seine Karte und eröffnet die Runde, die anderen Spieler müssen, dasselbe Symbol legen. 
		 * 	Ansonsten einen Trumpf oder eine Fehlfarbe.
		 */
		if(board.isEmpty()) {
			roundStarter=p;
			board.put(p, card);			
		}
		else {
			while(!validPlay(p, card)) {
				System.out.println("Unzulässiger Zug!");
			}
			board.put(p, card);
		}	
	}
	
	private void calculateStiche(){
		/*
		 * 	Wertigkeiten: Herz T > Kreuz D > D >  B > Karo Ass > Karo T > Karo K > rest sind Fehlfarben
		 */
		Map.Entry<Player, Card> firstEntry = board.entrySet().iterator().next();
		Player largestKey = firstEntry.getKey();
		Card largestKeyValue = firstEntry.getValue();
		
		for(Map.Entry<Player, Card> map : board.entrySet()	) {
			Player key = map.getKey();
			Card value = map.getValue();
			if(value.compareTo(largestKeyValue) > 0) {
				largestKeyValue = value;
				largestKey = key;
			}
		}

		roundStarter = largestKey;
		List<Card> wonCards=new LinkedList<Card>();
		for(Player p: playerList) {
				wonCards.add(board.get(p));
		}
		stiche.put(largestKey, wonCards);
	}
	
	public void shuffle(){
		List<Card> cards = cardGenerator.createCards();
		Collections.shuffle(cards);
		int index = 0;
		List<Card> playerCards = new LinkedList();
		for(int i=0;i<4;i++){
			playerCards.clear();
			for(int j=0;j<10;j++){
				playerCards.add(cards.get(index++));
			}
			playerList.get(i).setCards(new LinkedList<Card>(playerCards));
		}
	}
	
	public Player calculateWinner(){
		return null;
	}

	
	
}
