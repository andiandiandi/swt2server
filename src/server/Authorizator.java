package server;

import java.io.IOException;

import org.json.JSONObject;

import storage.Database;

public class Authorizator extends Thread {

	private ClientWorker cw;
	private ServerThread serverThread;
	private Database db;

	public Authorizator(ClientWorker cw, ServerThread serverThread) {
		this.cw = cw;
		this.serverThread = serverThread;

		db = Database.getInstance();
	}

	@Override
	public void run() {
		while (true) {
			String loginData = cw.readMessage();
			if (loginData != null) {

				JSONObject credentials = new JSONObject(loginData);

				String event = credentials.getString(JSONActionsE.EVENT.name());
				String username = credentials.getString(JSONAttributesE.USERNAME.name());
				String password = credentials.getString(JSONAttributesE.PASSWORD.name());

				if (event.equals(JSONEventsE.LOGIN.name()) && db.verifyUser(username, password)) {
					JSONObject verify_login = new JSONObject();
					verify_login.put(JSONActionsE.EVENT.name(), JSONEventsE.LOGIN.name());
					verify_login.put(JSONEventsE.LOGIN.name(), JSONAttributesE.LOGINVERIFIED.name());

					cw.sendMessage(verify_login.toString());

					cw.setUsername(username);
					serverThread.verified(cw, this);
				} else {
					JSONObject verify_login = new JSONObject();
					verify_login.put(JSONActionsE.EVENT.name(), JSONEventsE.LOGIN.name());
					verify_login.put(JSONEventsE.LOGIN.name(), JSONAttributesE.LOGINDENIED.name());

					cw.sendMessage(verify_login.toString());

				}
			}
		}
	}

}
