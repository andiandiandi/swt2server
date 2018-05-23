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
		lobbyHandler.start();

		users.put(client, lobbyHandler);

		for (ClientWorker cw : users.keySet()) {
			updateUserListClientside(cw);
		}

	}

	private void setUsersToIngame(List<ClientWorker> queue) {

		boolean found;
		System.out.println("setusers..");

		for (ClientWorker temp : queue) {
			temp.setIngame(true);
			LobbyHandler temp_lobbyHandler = users.get(temp);
			temp_lobbyHandler.setGameStarted();
			temp_lobbyHandler.stop();
			temp_lobbyHandler = null;
		}

		for (ClientWorker cw : users.keySet()) {

			if (!cw.isIngame()) {
				System.out.println("updating userlist for user: " + cw.getUsername());
				updateUserListClientside(cw);
			}

		}

	}

	public synchronized boolean queue(ClientWorker cw) {

		if (queue.contains(cw)) {
			JSONObject json = new JSONObject();
			json.put(JSONActionsE.EVENT.name(), JSONEventsE.FAIL.name());
			json.put(JSONEventsE.FAIL.name(), JSONLobbyAttributes.ALREADYQUEUED.name());

			cw.sendMessage(json.toString());

			return false;
		}

		queue.add(cw);

		if (queue.size() == 4) {
			System.out.println("setting users to ingame");

			new Thread(new Runnable() {

				@Override
				public void run() {
					setUsersToIngame(queue);
					System.out.println("after setting users to ingame");
					new Thread(new GameSession(queue)).start();
					queue.clear();
					for (ClientWorker temp : users.keySet()) {
						if (!temp.isIngame()) {
							updateUserQueueClientside(temp);
						}
					}
				}

			}).start();

			return true;

		} else {
			JSONObject json = new JSONObject();
			json.put(JSONActionsE.EVENT.name(), JSONEventsE.QUEUENUMBER.name());
			json.put(JSONLobbyAttributes.NEWQUEUENUMBER.name(), queue.size() + "");
			for (ClientWorker temp : users.keySet()) {
				temp.sendMessage(json.toString());
			}
		}
		return false;
	}

	void updateUserListClientside(ClientWorker cw) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put(JSONActionsE.EVENT.name(), JSONEventsE.USERLIST.name());
		JSONArray jsonArr = new JSONArray();

		for (ClientWorker temp : users.keySet()) {
			JSONObject jsonUser = new JSONObject();
			jsonUser.put("username", temp.getUsername());
			jsonUser.put("ingame", temp.isIngame());
			jsonArr.put(jsonUser);
		}

		jsonObj.put(JSONLobbyAttributes.NEWUSERLIST.name(), jsonArr);

		cw.sendMessage(jsonObj.toString());
	}

	void updateUserStatisticClientside(ClientWorker cw) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put(JSONActionsE.EVENT.name(), JSONEventsE.STATISTICS.name());
		JSONArray jsonArr = new JSONArray();

		JSONObject json_games = new JSONObject();
		JSONObject json_wins = new JSONObject();
		JSONObject json_lost = new JSONObject();
		JSONObject json_score = new JSONObject();

		json_games.put("games", cw.getStatistic().getGames() + "");
		json_wins.put("wins", cw.getStatistic().getWins() + "");
		json_lost.put("lost", cw.getStatistic().getLost() + "");
		json_score.put("score", cw.getStatistic().getScore() + "");

		jsonArr.put(json_games);
		jsonArr.put(json_wins);
		jsonArr.put(json_lost);
		jsonArr.put(json_score);

		jsonObj.put(JSONLobbyAttributes.USERSTATISTIC.name(), jsonArr);

		cw.sendMessage(jsonObj.toString());
	}

	void updateUserQueueClientside(ClientWorker cw) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put(JSONActionsE.EVENT.name(), JSONEventsE.QUEUENUMBER.name());
		jsonObj.put(JSONLobbyAttributes.NEWQUEUENUMBER.name(), queue.size() + "");

		cw.sendMessage(jsonObj.toString());
	}

}
