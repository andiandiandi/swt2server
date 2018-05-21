package server;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;

import entities.Player;
import game.GameSession;

public class ServerThread extends Thread {

	private int maxPlayers = 80;

	private Lobby lobby;
	private Server gameServer;

	public ServerThread(Server gameServer) {
		this.gameServer = gameServer;
		lobby = new Lobby();
	}

	@Override
	public void run() {
		while (true) {
			ClientWorker cw;
			Authorizator authorizator;
			try {
				
				Socket client = gameServer.getServerSocket().accept();
				cw = new ClientWorker(client);
				authorizator = new Authorizator(cw,this);
				authorizator.start();
				
				
			} catch (IOException e) {
				System.out.println("Accept failed: 4444");
				System.exit(-1);
			}
		}

	}

	
	public void verified(ClientWorker cw,Thread t){
		lobby.addUser(cw);
		t.stop();
	}
	
}
