package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientWorker {

	private Socket socket;

	private PrintWriter writer;
	private BufferedReader reader;

	private String username;
	private int score;
	private boolean ingame;

	public ClientWorker(Socket socket) {
		this.socket = socket;

		try {
			writer = new PrintWriter(socket.getOutputStream());
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Socket getSocket() {
		return socket;
	}

	public void sendMessage(String message){
		writer.println(message);
		writer.flush();
	}

	public BufferedReader getReader() {
		return reader;
	}

	public void setIngame(boolean ingame) {
		this.ingame = ingame;
	}

	public boolean isIngame() {
		return ingame;
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

}
