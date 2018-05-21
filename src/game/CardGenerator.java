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
		else {

			String[] wertigkeiten = WertigkeitE.getValues();

			for (int i = 0; i < wertigkeiten.length; i++) {
				for (int j = 0; j < 2; j++) {
					cards.add(new Card(SymbolE.HERZ, WertigkeitE.valueOf(wertigkeiten[i])));
					cards.add(new Card(SymbolE.KARO, WertigkeitE.valueOf(wertigkeiten[i])));
					cards.add(new Card(SymbolE.KREUZ, WertigkeitE.valueOf(wertigkeiten[i])));
					cards.add(new Card(SymbolE.PIK, WertigkeitE.valueOf(wertigkeiten[i])));
				}
			}

			return cards;
		}
	}

}
