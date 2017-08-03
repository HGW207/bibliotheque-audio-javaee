package fr.sopra.pox3.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.sopra.pox3.entities.MaisonDeDisque;

@Stateless
public class MaisonDeDisqueDAO extends GenericDAO<MaisonDeDisque>{
	

	@PersistenceContext(name = "Bibliotheque")
	private EntityManager em;
	
	public MaisonDeDisqueDAO(){
		super(MaisonDeDisque.class);
	}
	
}
