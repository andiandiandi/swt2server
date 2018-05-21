package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import game.GameSession;

public class Server {

	private String ip = "localhost";
	private int port = 25000;

	private ServerSocket serverSocket;

	public Server() {
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}

		acceptUser();
	}

	public void acceptUser() {

		ServerThread serverThread = new ServerThread(this);
		serverThread.setName("Server");
		serverThread.start();

	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

}
