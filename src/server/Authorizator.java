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
			try {
				String loginData = cw.getReader().readLine();
				JSONObject credentials = new JSONObject(loginData);

				String event = credentials.getString(JSONActionsE.EVENT.name());
				String username = credentials.getString("username");
				String password = credentials.getString("password");

				if (event.equals(JSONEventsE.LOGIN.name()) && db.verifyUser(username, password)) {
					cw.setUsername(username);
					serverThread.verified(cw, this);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
