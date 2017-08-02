package fr.sopra.pox3.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import fr.sopra.pox3.entities.Chanson;

@Stateless
public class ChansonDAO {
	

	@PersistenceContext(name = "Bibliotheque")
	private EntityManager em;
	
	public Chanson findById(int id) {
		return em.find(Chanson.class, id);
	}

	public List<Chanson> findAll() {
		TypedQuery<Chanson> query = em.createQuery("from fr.sopra.pox3.entities.Chanson a", Chanson.class);
		return query.getResultList();
	}

	public void add(Chanson chanson) {
		em.persist(chanson);
	}

	public void update(Chanson chanson) {
		em.merge(chanson);
	}

	public void deleteById(int id) {
		Chanson chanson = findById(id);
		if (chanson != null)
			em.remove(chanson);
	}

	
}

