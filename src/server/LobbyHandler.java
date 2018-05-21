package server;

import java.io.IOException;

import org.json.JSONObject;

public class LobbyHandler extends Thread {

	private ClientWorker cw;
	private Lobby lobby;

	public LobbyHandler(ClientWorker cw, Lobby lobby) {
		this.cw = cw;
		this.lobby = lobby;
	}

	@Override
	public void run() {
		while (true) {
			String nextAction;
			try {
				
				nextAction = cw.getReader().readLine();
				JSONObject json = new JSONObject(nextAction);
				String action = json.getString(JSONActionsE.EVENT.name());
				
				if (action.equals(JSONEventsE.QUEUEFORGAME.name())) {
					lobby.queue(cw);
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
