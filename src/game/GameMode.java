package game;

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
	
	void setCalculationMode(CalculationMode mode){
		this.calculationMode=mode;
	}
	
	public int calculate(Card card){
		return calculationMode.calculateValue(card);
	}
	
}
