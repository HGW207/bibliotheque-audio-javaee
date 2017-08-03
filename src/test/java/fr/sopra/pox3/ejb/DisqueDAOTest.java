package fr.sopra.pox3.ejb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.sopra.pox3.entities.Disque;

@RunWith(Arquillian.class)
public class DisqueDAOTest {
	@Deployment
	public static Archive<?> createDeployment() {
		// TODO Ajouter la data source pour les tests et l'utiliser à la place
		// de ExempleDS dans le fichier persistence.xml
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackage(Disque.class.getPackage())
				.addPackage(DisqueDAO.class.getPackage())
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@PersistenceContext
	EntityManager em;

	@Inject
	UserTransaction utx;

	@EJB
	DisqueDAO disqueDAO;

	@Before
	public void preparePersistenceTest() throws Exception {
		startTransaction();
		System.out.println("Deleting old records");
		em.createQuery("delete from Disque").executeUpdate();
		utx.commit();

		em.clear();

		startTransaction();
	}

	@After
	public void commitTransaction() throws Exception {
		utx.commit();
	}

	@Test
	public void shouldFindAllGamesUsingJpqlQuery() throws Exception {
		System.out.println("Inserting record");
		Disque disque = new Disque();
		disque.setNom("TotoRecords");
		em.persist(disque);

		System.out.println("Selecting (using JPQL)...");
		List<Disque> disques = em.createQuery("from Disque m", Disque.class).getResultList();

		System.out.println("Found " + disques.size() + " disques");
		assertEquals(1, disques.size());
		assertEquals("TotoRecords", disques.get(0).getNom());
	}

	@Test
	public void findAllTest() {
		assertEquals(0, disqueDAO.findAll().size());
		fillDB(2);
		assertEquals(2, disqueDAO.findAll().size());
	}
	
	@Test
	public void findByIdTest() {
		assertTrue(disqueDAO.findById(1)==null);
		fillDB(2);
		List<Disque> disques = disqueDAO.findAll();
		Disque disque = disques.get(0);
		assertFalse(disqueDAO.findById(disque.getId())==null);
		assertEquals(disque.getNom(), disqueDAO.findById(disque.getId()).getNom());
	}
	
	@Test
	public void addTest(){
		fillDB(2);
		assertEquals(2, disqueDAO.findAll().size());
		Disque disque = new Disque();
		disque.setNom("DisqueAddTest");
		assertTrue(disque.getId()<=0);
		disqueDAO.add(disque);
		assertTrue(disque.getId()>0);
		assertEquals(3, disqueDAO.findAll().size());
	}
	
	//
	@Test(expected = EJBTransactionRolledbackException.class)
	public void addTestFailure(){
		Disque disque = new Disque();
		disque.setNom("MaisonAddTest");
		disqueDAO.add(disque);
		
		//Expected failure when adding mains with the same Id
		Disque disque2 = new Disque();
		disque2.setId(disque.getId());
		disque2.setNom("plop");
		
		disqueDAO.add(disque2);
	}

	private void fillDB(int n) {
		for (int i = 0; i < n; i++) {
			Disque disque = new Disque();
			disque.setNom("DisqueAutofill" + 1);
			em.persist(disque);
		}
	}


	private void startTransaction() throws Exception {
		utx.begin();
		em.joinTransaction();
	}
}
