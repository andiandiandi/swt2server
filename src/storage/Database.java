package storage;

import java.util.LinkedList;
import java.util.List;

public class Database {

	private static Database instance;

	List<String> validUsernames;
	private List<String> online;

	private Database() {
		online = new LinkedList<String>();
		validUsernames = new LinkedList<String>();
		validUsernames.add("a");
		validUsernames.add("b");
		validUsernames.add("c");
		validUsernames.add("d");
		validUsernames.add("e");
		validUsernames.add("f");
		validUsernames.add("g");
		validUsernames.add("h");

	}

	public boolean verifyUser(String username, String password) {

		boolean alreadyLoggedIn = isLoggedIn(username);

		if (!alreadyLoggedIn) {
			for (String name : validUsernames) {
				if (username.equals(name)){
					online.add(username);
					return true;
				}
			}
		}

		return false;
	}

	private boolean isLoggedIn(String username) {
		return online.contains(username);
	}

	public static Database getInstance() {
		if (Database.instance == null) {
			Database.instance = new Database();
		}
		return Database.instance;
	}
}
