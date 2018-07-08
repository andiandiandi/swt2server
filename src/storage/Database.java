package storage;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.Player;
import entities.PunkteStand;
import entities.Spiel;

public class Database {

	private static Database instance;
	private EntityManagerFactory factory;
	private EntityManager em;

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

		factory = Persistence.createEntityManagerFactory("doppelkopfSpiel");
	}

	public boolean verifyUser(String username, String password) {

		boolean alreadyLoggedIn = isLoggedIn(username);

		if (!alreadyLoggedIn) {
			for (String name : validUsernames) {
				if (username.equals(name)) {
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

	public void persist(Map<Player, Integer> playerList) {

		em = factory.createEntityManager();

		// create new todo
		em.getTransaction().begin();

		Spiel spiel = new Spiel();

		for (Player p : playerList.keySet()) {
			PunkteStand punktestand = new PunkteStand();
			punktestand.setSpielerID(p.getUsername());
			punktestand.setPunkte(playerList.get(p));
			System.out.println(punktestand);
			spiel.add(punktestand);
		}

		em.persist(spiel);

		em.getTransaction().commit();
		em.close();

	}

	public static Database getInstance() {
		if (Database.instance == null) {
			Database.instance = new Database();
		}
		return Database.instance;
	}
}
