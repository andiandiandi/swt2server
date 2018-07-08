package game;

import entities.GamemodeE;
import entities.Player;
import game.calculationMode.CalculationMode;
import game.calculationMode.FarbstichCalculationMode;
import game.calculationMode.FleischlosCalculationMode;
import game.calculationMode.NormalCalculationMode;

public class GameMode {
	
	
	private static GameMode instance;
	
	private CalculationMode calculationMode;
	private GamemodeE gameModeE;
	
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
		if(mode instanceof NormalCalculationMode){
			gameModeE = GamemodeE.NORMAL;
		}else if(mode instanceof FarbstichCalculationMode){
			gameModeE = GamemodeE.FARBSTICH;
		}else if(mode instanceof FleischlosCalculationMode){
			gameModeE = GamemodeE.FLEISCHLOS;
		}
	}
	/**
	 * 
	 * @return Gibt den Sieger zurück
	 */
	public Player evaluateRound(){
		return calculationMode.evaluateRound();
	}
	
	public GamemodeE getGameModeE(){
		return gameModeE;
	}
	
}
