package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import utils.User;

public class ClientWorker implements Runnable {

	private User user;
	private ServerThread serverThread;
	

	public ClientWorker(User user,ServerThread serverThread) {
		this.user=user;
		this.serverThread=serverThread;
	}

	@Override
	public void run() {
		String line;
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			in = new BufferedReader(new InputStreamReader(user.getSocket().getInputStream()));
			out = new PrintWriter(user.getSocket().getOutputStream(), true);
		} catch (IOException e) {
			System.out.println("in or out failed");
			System.exit(-1);
		}

		while (true) {
			try {
				line = in.readLine();
				System.out.println("incoming msg: " + line);
				if(line.equals("start")){
					System.out.println("queuing from clientworker");
					serverThread.changeToReadyState(user);
				}

			} catch (IOException e) {
				System.out.println("Read failed");
				System.exit(-1);
			}
		}

	}
}
