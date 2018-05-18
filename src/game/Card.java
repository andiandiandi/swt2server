package game;

import utils.SymbolE;
import utils.WertigkeitE;

public class Card {

	private SymbolE symbol;
	private WertigkeitE wertigkeit;
	private boolean trumpf;
	
	public Card() {
		// TODO Auto-generated constructor stub
	}

	public SymbolE getSymbol() {
		return symbol;
	}

	public void setSymbol(SymbolE symbol) {
		this.symbol = symbol;
	}

	public WertigkeitE getWertigkeit() {
		return wertigkeit;
	}

	public void setWertigkeit(WertigkeitE wertigkeit) {
		this.wertigkeit = wertigkeit;
	}

	public boolean isTrumpf() {
		return trumpf;
	}

	public void setTrumpf(boolean trumpf) {
		this.trumpf = trumpf;
	}
	
	
	
}
