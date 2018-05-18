package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import entities.User;

public class ClientWorker implements Runnable {

	private User user;
	private ServerThread serverThread;

	private BufferedReader in = null;
	private PrintWriter out = null;

	private ClientStateE state;

	public ClientWorker(User user, ServerThread serverThread) {
		this.user = user;
		this.serverThread = serverThread;

		state = ClientStateE.LOBBY;
	}

	@Override
	public void run() {
		String line;
		try {
			in = new BufferedReader(new InputStreamReader(user.getSocket().getInputStream()));
			out = new PrintWriter(user.getSocket().getOutputStream(), true);
		} catch (IOException e) {
			System.out.println("in or out failed");
			System.exit(-1);
		}

		while (true) {
			try {

				if (state == ClientStateE.LOBBY) {

					line = in.readLine();

					if (line.equals("start")) {
						System.out.println("queuing from clientworker");
						serverThread.changeToReadyState(this);
					}

				} else if (state == ClientStateE.INGAME) {

				}

			} catch (IOException e) {
				System.out.println("Read failed");
				System.exit(-1);
			}
		}

	}

	public User getUser() {
		return user;
	}

	public void changeState(ClientStateE state) {
		this.state = state;
	}
}
