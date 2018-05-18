package server;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import entities.Player;
import entities.User;
import game.GameSession;

public class ServerThread extends Thread{

	private LinkedList<User> userList;
	private LinkedList<ClientWorker> queuedForGame;
	private int maxPlayers = 80;
	
	private GameServer gameServer;
	
	public ServerThread(GameServer gameServer) {
		this.gameServer=gameServer;
		userList = new LinkedList<User>();
		queuedForGame = new LinkedList<ClientWorker>();
	}

	@Override
	public void run() {
		while (true) {
			ClientWorker w;
			try {
				Socket client = gameServer.getServerSocket().accept();
				User user = new User(client.getInetAddress().getHostName(),client);
				userList.add(user);
				w = new ClientWorker(user,this);
				System.out.println("client connected");
				Thread thread = new Thread(w);
				thread.start();
			} catch (IOException e) {
				System.out.println("Accept failed: 4444");
				System.exit(-1);
			}

		}
		
	}
	
	public void changeToReadyState(ClientWorker client){
		
			queuedForGame.add(client);
	
			if(queuedForGame.size()<4){
				System.out.println("added user");
			}else{
				for(ClientWorker c : queuedForGame){
					c.changeState(ClientStateE.INGAME);
				}
			
				//launch gameSession for 4 players
				Thread gameSession = new Thread(new GameSession(queuedForGame,this));
				gameSession.start();
				
				queuedForGame = new LinkedList<ClientWorker>();
				System.out.println("started gameSession");
			
			}
		
	}
	
}
