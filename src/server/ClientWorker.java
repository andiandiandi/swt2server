package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.JSONObject;

import entities.User;

public class ClientWorker implements Runnable {

	private Socket socket;
	private User user;
	private ServerThread serverThread;
	
	private BufferedReader in;
	private PrintWriter out;

	private ClientStateE state;

	public ClientWorker(Socket socket,User user, ServerThread serverThread) {
		this.socket = socket;
		this.user = user;
		this.serverThread = serverThread;

		try {
			out = new PrintWriter(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		state = ClientStateE.LOBBY;
	}

	@Override
	public void run() {

		String request = "";


		while (true) {
			try {

				if (state == ClientStateE.LOBBY) {

					request = in.readLine();

					JSONObject json = new JSONObject(request);
					String jsonParsed = json.getString("event");

					if (jsonParsed.equals("queueForGame")) {
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

	public PrintWriter getWriter(){
		return out;
	}
	
	public BufferedReader getReader(){
		return in;
	}


	public User getUser() {
		return user;
	}

	public void changeState(ClientStateE state) {
		this.state = state;
	}
}
