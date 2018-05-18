package entities;

import java.net.Socket;

public class User {

	protected String username;
	protected Socket socket;
	
	protected int score;
	
	
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


	
	public Socket getSocket(){
		return socket;
	}


}
