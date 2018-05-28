package game;

import java.util.List;

public class NormalTrumpfState extends TrumpfState {

	@Override
	public boolean validate(Card newCard, Card compareToPlayedCard, List<Card> playerDeck) {

		if (compareToPlayedCard.isTrumpf()) {

			if (newCard.isTrumpf())
				return true;
			else {

				boolean found = false;

				for (Card temp_card : playerDeck) {
					if (temp_card.isTrumpf()) {
						found = true;
						break;
					}
				}

				if (found)
					return false;
				else
					return true;

			}

		}

		if (!compareToPlayedCard.isTrumpf()) {

			// compare ist kein trumpf und newcard ist trumpf, obwohl compare fehlfarbe ist und im playerdeck noch gleiches fehl existiert
			if (newCard.isTrumpf()) {

				for (Card temp : playerDeck) {
					if (!temp.isTrumpf() && temp.getSymbol() == compareToPlayedCard.getSymbol())
						return false;

				}
				
				return true;
			}

			// compare kein trumpf und newcard kein trumpf
			if (newCard.getSymbol() == compareToPlayedCard.getSymbol())
				return true;

			if(!newCard.isTrumpf()){
				
				for(Card temp : playerDeck){
					if (!temp.isTrumpf() && temp.getSymbol() == compareToPlayedCard.getSymbol())
						return false;
				}
				
				return true;
				
			}

		}

		return false;
	}

}
