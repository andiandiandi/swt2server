package utils;

import java.net.Socket;

public class User {

	protected String username;
	protected Socket socket;
	
	protected int score;
	protected GameStateE gameState;
	
	public User(String username,Socket socket){
		this.username=username;
		this.socket=socket;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}

	public GameStateE getGameState() {
		return gameState;
	}

	public void setGameState(GameStateE gameState) {
		this.gameState = gameState;
	}
	
	public Socket getSocket(){
		return socket;
	}


}