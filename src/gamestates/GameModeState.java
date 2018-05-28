package gamestates;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import entities.CardE;
import entities.GamemodeE;
import entities.Player;
import game.Card;
import game.CardGame;
import game.GameMode;
import game.calculationMode.CalculationMode;
import game.calculationMode.FarbstichCalculationMode;
import game.calculationMode.FleischlosCalculationMode;
import game.calculationMode.NormalCalculationMode;
import server.JSONActionsE;
import server.JSONAttributesE;
import server.JSONEventsE;

public class GameModeState extends GameSessionState {

	private CalculationMode mode;
	
	public GameModeState(List<Player> playerList, CardGame cardGame) {
		super(playerList, cardGame);
	}

	@Override
	public void execute() {

		GamemodeE gameModeE = null;
		
		//ask every player for gamemode
		JSONObject toSend = new JSONObject();
		toSend.put(JSONActionsE.EVENT.name(), JSONEventsE.GETGAMEMODE.name());
		for (Player p : playerList)
			p.sendMessage(toSend.toString());
		
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
			if(temp.getOrder()==1){
				gameModeE = mode;
				setGameMode(mode);				
				break;
			}
			
			
		}
		
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
			//mode = new FarbstichCalculationMode(cardGame.getRoundSpecificCards());			
		}
		
	}

	public CalculationMode getGameMode() {
		return mode;
	}

}
