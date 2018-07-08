package game;

import java.util.List;

import entities.Card;

public class NormalTrumpfState extends TrumpfState {

	@Override
	public boolean validate(Card newCard, Card compareToPlayedCard, List<Card> playerDeck) {

		System.out.println("comparecard: " + compareToPlayedCard + "\nnew Card: " + newCard );
		
		
		//compare : koenig kreuz
		//new: 		zehn kreuz
		
		if (compareToPlayedCard.isTrumpf()) {

			if (newCard.isTrumpf()){
				System.out.println("BEIDE TRUMPF;ANGENOMMEN");
				return true;
			}
			else {

				boolean found = false;

				for (Card temp_card : playerDeck) {
					if (temp_card.isTrumpf()) {
						found = true;
						break;
					}
				}

				if (found){
					System.out.println("COMPARE IST TRUMPF;NEWCARD IST KEIN TRUMPF;ANGENOMMEN");
					return false;
				}
				else{
					System.out.println("COMPARE IST TRUMPF;NEWCARD IST KEIN TRUMPF;NICHT ANGENOMMEN");
					return true;
				}

			}

		}

		if (!compareToPlayedCard.isTrumpf()) {

			// compare ist kein trumpf und newcard ist trumpf, obwohl compare fehlfarbe ist und im playerdeck noch gleiches fehl existiert
			if (newCard.isTrumpf()) {

				for (Card temp : playerDeck) {
					if (!temp.isTrumpf() && temp.getSymbol() == compareToPlayedCard.getSymbol()){
						System.out.println("TEMP GEFUNDEN! : " + temp);
						System.out.println("NEWCARD IST TRUMPF; COMPARE IST KEIN TRUMPF");
						return false;
					}

				}
				
				System.out.println("ANGENOMMEN1");
				return true;
			}

			// compare kein trumpf und newcard kein trumpf
			
			if (newCard.getSymbol() == compareToPlayedCard.getSymbol()){
				System.out.println("KEIN TRUMPF; SYMBOL STIMMT ÜBEREIN;ANGENOMMEN");
				return true;
			}

			if(!newCard.isTrumpf()){
				
				for(Card temp : playerDeck){
					if (!temp.isTrumpf() && temp.getSymbol() == compareToPlayedCard.getSymbol()){
						System.out.println("KEIN TRUMPF UND IM DECK NICHTS GEFUNDEN;NICHT ANGENOMMEN");
						return false;
					}
				}
				
				System.out.println("ANGENOMMEN2");
				return true;
				
			}

		}

		return false;
	}

}
