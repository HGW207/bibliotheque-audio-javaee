package fr.sopra.pox3.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public abstract class GenericDAO<T> {

	
	private final Class<T> classAttribute;
	
	protected GenericDAO(Class<T> classParam){
		this.classAttribute = classParam;
	}
	
	@PersistenceContext(name = "Bibliotheque")
	private EntityManager em;

	
	public T findById(int id) {
		return em.find(classAttribute, id);
	}

	public List<T> findAll() {
		TypedQuery<T> query = em.createQuery("from "+classAttribute.getSimpleName()+" entity", classAttribute);
		return query.getResultList();
	}

	public void add(T disque) {
		em.persist(disque);
	}

	public void update(T disque) {
		em.merge(disque);
	}

	public void deleteById(int id) {
		T entity = findById(id);
		if (entity != null)
			em.remove(entity);
	}

}
