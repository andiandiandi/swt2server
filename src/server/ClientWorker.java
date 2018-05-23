package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class ClientWorker {

	private Socket socket;

	private PrintWriter writer;
	private BufferedReader reader;

	private String username;
	private Statistic statistic;
	private boolean ingame;

	public ClientWorker(Socket socket) {
		this.socket = socket;
		
		statistic = new Statistic();
		
		Random random = new Random();

		statistic.setGames(random.nextInt(15));
		statistic.setWins(random.nextInt(15));
		statistic.setLost(random.nextInt(15));
		statistic.setScore(random.nextInt(500));

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

	public void sendMessage(String message) {
		writer.println(message);
		writer.flush();
	}

	public String readMessage(){
		try {
			return reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
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

	public Statistic getStatistic() {
		return statistic;
	}

}
