package gamestates;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.json.JSONObject;

import entities.GamemodeE;
import entities.Player;
import entities.SymbolE;
import game.CardGame;
import game.calculationMode.CalculationMode;
import game.calculationMode.FarbstichCalculationMode;
import game.calculationMode.FleischlosCalculationMode;
import game.calculationMode.NormalCalculationMode;
import server.JSONActionsE;
import server.JSONEventsE;

public class GameModeState extends GameSessionState {

	private CalculationMode mode;
	
	public GameModeState(List<Player> playerList, CardGame cardGame) {
		super(playerList, cardGame);
	}

	@Override
	public void execute() {

		GamemodeE gameModeE = GamemodeE.NORMAL;
		
		//ask every player for gamemode
		JSONObject toSend = new JSONObject();
		toSend.put(JSONActionsE.EVENT.name(), JSONEventsE.GETGAMEMODE.name());
		for (Player p : playerList)
			p.sendMessage(toSend.toString());
		
		Map<Player,GamemodeE> map = new HashMap<Player,GamemodeE>();
		
		//get response
		for(Player temp : playerList){
			String msg = temp.readMessage();
			
			JSONObject obj = new JSONObject(msg);
			
			while(!obj.has(JSONEventsE.GETGAMEMODE.name())){
				msg = temp.readMessage();
				obj = new JSONObject(msg);
			}
			String string_mode = obj.getString(JSONEventsE.GETGAMEMODE.name());
			GamemodeE mode = GamemodeE.valueOf(string_mode);
			map.put(temp,mode);
		}
		
		Iterator<Player> it = new TreeSet(map.keySet()).iterator();
		Player aktuell = null;
		while(it.hasNext()){
			aktuell = it.next();
			if(map.get(aktuell)!=gameModeE.NORMAL){
				gameModeE = map.get(aktuell);
				break;
			}
		}
		setGameMode(gameModeE);				
		//b d a c
		
		//send ack with payload: gamemode 
		for(Player temp2 : playerList){
			JSONObject json = new JSONObject();
			json.put(JSONActionsE.EVENT.name(), JSONEventsE.GETGAMEMODE.name());
			json.put(JSONEventsE.GETGAMEMODE.name(), gameModeE.name());
			temp2.sendMessage(json.toString());
		}
	}

	private void setGameMode(GamemodeE gameModeE) {
		
		if(gameModeE == GamemodeE.NORMAL)
			mode = new NormalCalculationMode(cardGame.getRoundSpecificCards());
		else if(gameModeE == GamemodeE.FLEISCHLOS)
			mode = new FleischlosCalculationMode(cardGame.getRoundSpecificCards());
		else if(gameModeE == GamemodeE.FARBSTICH){
			// TODO Farbe muss vom clienten übergeben werden
			mode = new FarbstichCalculationMode(SymbolE.HERZ,cardGame.getRoundSpecificCards());			
		}
		
	}

	public CalculationMode getGameMode() {
		return mode;
	}

}
