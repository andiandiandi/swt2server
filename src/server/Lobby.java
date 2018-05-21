package server;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import game.GameSession;

public class Lobby {

	private Map<ClientWorker, LobbyHandler> users;
	private List<ClientWorker> queue;

	public Lobby() {
		users = new HashMap<ClientWorker, LobbyHandler>();
		queue = new LinkedList<ClientWorker>();
	}

	public void addUser(ClientWorker client) {
		LobbyHandler lobbyHandler = new LobbyHandler(client, this);
		users.put(client, lobbyHandler);
		lobbyHandler.start();
	}

	private void setUsersToIngame(List<ClientWorker> queue) {

		boolean found = false;

		JSONObject jsonObj = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		
		for(ClientWorker temp : queue){
			jsonArr.put(temp.getUsername());
		}
		
		jsonObj.put(JSONActionsE.EVENT.name(), JSONEventsE.USERLISTCHANGED.name());
		jsonObj.put(JSONAttributesE.USERSINGAME.name(), jsonArr);
		
		for (ClientWorker cw2 : users.keySet()) {
			found = false;
			for (ClientWorker cw : queue) {
				if (cw == cw2) {
					cw2.setIngame(true);
					found = true;
					break;
				}
			}
			if (!found) {
				cw2.sendMessage(jsonObj.toString());
			}
		}

	}

	public synchronized void queue(ClientWorker cw) {
		queue.add(cw);

		if (queue.size() == 4) {
			setUsersToIngame(queue);

			new Thread(new GameSession(queue, this)).start();

			queue.clear();
		} else {
			JSONObject json = new JSONObject();
			json.put(JSONActionsE.EVENT.name(), JSONEventsE.QUEUENUMCHANGED.name());
			json.put(JSONAttributesE.NEWQUEUENUMBER.name(), queue.size() + "");
			for (ClientWorker temp : queue) {
				temp.sendMessage(json.toString());
			}
		}
	}

}
