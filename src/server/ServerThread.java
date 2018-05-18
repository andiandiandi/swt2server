package server;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import game.GameSession;
import utils.GameStateE;
import utils.Player;
import utils.User;

public class ServerThread extends Thread{

	private LinkedList<User> userList;
	private LinkedList<User> queuedForGame;
	private int maxPlayers = 80;
	
	private GameServer gameServer;
	
	public ServerThread(GameServer gameServer) {
		this.gameServer=gameServer;
		userList = new LinkedList<User>();
		queuedForGame = new LinkedList<User>();
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
				Thread t = new Thread(w);
				t.start();
			} catch (IOException e) {
				System.out.println("Accept failed: 4444");
				System.exit(-1);
			}

		}
		
	}
	
	public void changeToReadyState(User user){
		System.out.println("size of queue: " + queuedForGame.size());
		if(queuedForGame.size()<3){
			user.setGameState(GameStateE.READY);
			queuedForGame.add(user);
			System.out.println("queud user: " + user.getUsername());
		}else{
			System.out.println("now have 4 clients ready up");
			queuedForGame.add(user);
			
			//change gamestate to ingame & make users to players
			List<Player> players = new LinkedList<Player>();
			for(User u : queuedForGame){
				u.setGameState(GameStateE.INGAME);
				players.add(new Player(user));
			}
			
			queuedForGame.clear();
			
			
			//launch gameSession for 4 players
			Thread gameSession = new Thread(new GameSession(players,this));
			gameSession.start();
			System.out.println("started gameSession");
		
			
		}
	}
	
}
