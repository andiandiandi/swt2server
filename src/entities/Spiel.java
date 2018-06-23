package entities;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.TableGenerator;


@Entity
@TableGenerator(name = "Spiel")
public class Spiel {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long spielID;


	private Date created;
	
	@PrePersist
	  protected void onCreate() {
	    created = new Date();
	  }

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<PunkteStand> punkte;

	public Spiel() {
		punkte = new LinkedList<PunkteStand>();
	}

	public void add(PunkteStand p1) {
		punkte.add(p1);
	}
	
	

}
