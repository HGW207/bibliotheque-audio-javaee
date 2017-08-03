package fr.sopra.pox3.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.sopra.pox3.entities.Disque;

@Stateless
public class DisqueDAO extends GenericDAO<Disque>{
	

	@PersistenceContext(name = "Bibliotheque")
	private EntityManager em;
	
	public DisqueDAO(){
		super(Disque.class);
	}
	
}
