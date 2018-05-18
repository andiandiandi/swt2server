package utils;

public class User {

	private String username;
	private int score;
	private boolean readyForGame;
	
	public User(String username){
		this.username=username;
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

	public boolean isReadyForGame() {
		return readyForGame;
	}

	public void setReadyForGame(boolean readyForGame) {
		this.readyForGame = readyForGame;
	}
	
	
	
}
