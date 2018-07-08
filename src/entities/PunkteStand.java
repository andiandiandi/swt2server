package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Entity
@TableGenerator(name="Punkte")
public class PunkteStand {

	
	@Id
	@Column(nullable=false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long refID;
	
	@Column(nullable=false)
	private String username;
	
	@Column(nullable=false)
	private int punkte;

	public PunkteStand() {}
	
	public Long getRefID() {
		return refID;
	}

	public String getSpielerID() {
		return username;
	}

	public void setSpielerID(String username) {
		this.username = username;
	}

	public int getPunkte() {
		return punkte;
	}

	public void setPunkte(int punkte) {
		this.punkte = punkte;
	}
	
	@Override
	public String toString() {
		return "Spieler: " + username + " hat Punkte: " + punkte;
	}
	
	
}
