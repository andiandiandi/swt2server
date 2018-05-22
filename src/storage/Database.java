package storage;

import java.util.LinkedList;
import java.util.List;

public class Database {

	private static Database instance;

	private Database() {
	}

	public boolean verifyUser(String username,String password){
		List<String> validUsernames = new LinkedList<String>();
		validUsernames.add("a");
		validUsernames.add("b");
		validUsernames.add("c");
		validUsernames.add("d");
		validUsernames.add("e");
		validUsernames.add("f");
		validUsernames.add("g");
		validUsernames.add("h");
		
		
		for(String name : validUsernames){
			if(username.equals(name))
				return true;
		}
		
		return false;
	}

	public static Database getInstance() {
		if (Database.instance == null) {
			Database.instance = new Database();
		}
		return Database.instance;
	}
}
