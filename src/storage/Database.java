package storage;

public class Database {
	
	private static Database instance;

	private Database () {}
	
	public boolean verifyUser(String username,String password){
		return true;
	}

	public static Database getInstance() {
		if (Database.instance == null) {
			Database.instance = new Database();
		}
		return Database.instance;
	}
}
