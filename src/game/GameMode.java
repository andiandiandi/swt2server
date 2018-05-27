package game;

import entities.Player;
import game.calculationMode.CalculationMode;

public class GameMode {
	
	
	private static GameMode instance;
	
	private CalculationMode calculationMode;
	
	private GameMode() {
	}

	public static synchronized GameMode getInstance() {
		if (GameMode.instance == null) {
			GameMode.instance = new GameMode();
		}
		return GameMode.instance;
	}
	/**
	 * Wurde ein besondees Spiel angesagt? Sonst normal
	 * @param mode
	 */
	void setCalculationMode(CalculationMode mode){
		this.calculationMode=mode;
	}
	/**
	 * 
	 * @return Gibt den Sieger zurück
	 */
	public Player evaluateRound(){
		return calculationMode.evaluateRound();
	}
	
}
