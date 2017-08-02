
package fr.sopra.pox3.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Auteur {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int id;

	@ManyToMany
	private List<Disque> disques;

	@ManyToOne
	private MaisonDeDisque maison;

	private String nom;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public MaisonDeDisque getMaison() {
		return this.maison;
	}

	public void setMaison(MaisonDeDisque maison) {
		this.maison = maison;
	}

	public void removeMaison() {
		this.setMaison(null);
		
	}
	
}