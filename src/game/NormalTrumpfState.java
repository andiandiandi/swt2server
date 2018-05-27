package game;

import java.util.List;

public class NormalTrumpfState extends TrumpfState {

	@Override
	public boolean validate(Card newCard, Card compareToPlayedCard, List<Card> playerDeck) {

		if (compareToPlayedCard.isTrumpf()) {
			System.out.println("compared ist trumpf: " + compareToPlayedCard);

			if (newCard.isTrumpf())
				return true;
			else{
				
				boolean found = false;
				
				for(Card temp_card : playerDeck){
					if(temp_card.isTrumpf()){
						found = true;
						break;
					}
				}
				
				if(found)
					return false;
				else
					return true;
	
			}

		}

		System.out.println("newCARD=TRUMPF?: " + newCard.isTrumpf());
		System.out.println("compareCARD=TRUMPF?:" + compareToPlayedCard.isTrumpf());
		if (!compareToPlayedCard.isTrumpf()) {

			//compare kein trumpf und newcard ist trumpf
			if(newCard.isTrumpf())
				return true;
			
			//compare kein trumpf und newcard kein trumpf
			if(newCard.getSymbol()==compareToPlayedCard.getSymbol())
				return true;
			
			//gucke nach fehlfarbe
			System.out.println("compared ist nicht trump: " + compareToPlayedCard);
			boolean found = false;

			System.out.println("vergleiche nun mit comparedCard: " + compareToPlayedCard);
			for (Card temp_card : playerDeck) {
				System.out.println("vergleichskarte: " + temp_card);
				if (!temp_card.isTrumpf() && temp_card.getSymbol() == compareToPlayedCard.getSymbol()){
					found = true;
					break;
				}
			}

			if (!found)
				return true;
			else
				return false;

		}

		return false;
	}

}

