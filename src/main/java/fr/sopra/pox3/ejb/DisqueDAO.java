package fr.sopra.pox3.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import fr.sopra.pox3.entities.Disque;

@Stateless
public class DisqueDAO {
	

	@PersistenceContext(name = "Bibliotheque")
	private EntityManager em;
	
	public Disque findById(int id) {
		return em.find(Disque.class, id);
	}

	public List<Disque> findAll() {
		TypedQuery<Disque> query = em.createQuery("from fr.sopra.pox3.entities.Disque d", Disque.class);
		return query.getResultList();
	}

	public void add(Disque disque) {
		em.persist(disque);
	}

	public void update(Disque disque) {
		em.merge(disque);
	}

	public void deleteById(int id) {
		Disque disque = findById(id);
		if (disque != null)
			em.remove(disque);
	}

	
}
