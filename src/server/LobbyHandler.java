package server;

import java.io.IOException;

import org.json.JSONObject;

public class LobbyHandler extends Thread {

	private ClientWorker cw;
	private Lobby lobby;
	private volatile boolean gameStarted = false;

	public LobbyHandler(ClientWorker cw, Lobby lobby) {
		this.cw = cw;
		this.lobby = lobby;
	}

	@Override
	public void run() {
		while (!gameStarted) {
			String nextAction;
			nextAction = cw.readMessage();
			if (nextAction != null) {

				JSONObject json = new JSONObject(nextAction);
				String action = json.getString(JSONActionsE.EVENT.name());

				if (action.equals(JSONEventsE.USERLIST.name())) {
					lobby.updateUserListClientside(cw);
				} else if (action.equals(JSONEventsE.STATISTICS.name())) {
					lobby.updateUserStatisticClientside(cw);
				} else if (action.equals(JSONEventsE.QUEUENUMBER.name())) {
					lobby.updateUserQueueClientside(cw);
				} else if (action.equals(JSONEventsE.QUEUEFORGAME.name())) {
					gameStarted = lobby.queue(cw);
				}
			}

		}
	}

	public void setGameStarted() {
		this.gameStarted = true;
	}

}
