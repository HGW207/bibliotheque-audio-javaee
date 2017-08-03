package fr.sopra.pox3.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.sopra.pox3.entities.Auteur;

@Stateless
public class AuteurDAO extends GenericDAO<Auteur>{
	

	@PersistenceContext(name = "Bibliotheque")
	private EntityManager em;
	
	public AuteurDAO(){
		super(Auteur.class);
	}
	
}
