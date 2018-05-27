package game;

import java.util.ArrayList;
import java.util.List;

import entities.SymbolE;
import entities.WertigkeitE;

public class CardGenerator {

	private List<Card> cards;

	public CardGenerator() {
		cards = new ArrayList<Card>();
	}

	public List<Card> createCards() {
		if (!cards.isEmpty())
			return cards;

		String[] wertigkeiten = WertigkeitE.getValues();

		for (int i = 0; i < wertigkeiten.length; i++) {
			for (int j = 0; j < 2; j++) {
				cards.add(new Card(SymbolE.HERZ, WertigkeitE.valueOf(wertigkeiten[i]),false));
				cards.add(new Card(SymbolE.KARO, WertigkeitE.valueOf(wertigkeiten[i]),false));
				cards.add(new Card(SymbolE.KREUZ, WertigkeitE.valueOf(wertigkeiten[i]),false));
				cards.add(new Card(SymbolE.PIK, WertigkeitE.valueOf(wertigkeiten[i]),false));
			}
		}

		for (Card temp_card : cards) {

			if (temp_card.getWertigkeit() == WertigkeitE.DAME || temp_card.getWertigkeit() == WertigkeitE.BUBE
					|| temp_card.getSymbol() == SymbolE.KARO) {
				temp_card.setTrumpf(true);
			} else if (temp_card.getSymbol() == SymbolE.HERZ && temp_card.getWertigkeit() == WertigkeitE.ZEHN) {
				temp_card.setTrumpf(true);
			}

		}

		return cards;
	}

}

// herz 10, alle damen, alle buben, alle caros