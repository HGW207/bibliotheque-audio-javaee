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

import fr.sopra.pox3.entities.MaisonDeDisque;

@RunWith(Arquillian.class)
public class MaisonDeDisqueDAOTest {
	@Deployment
	public static Archive<?> createDeployment() {
		// TODO Ajouter la data source pour les tests et l'utiliser à la place
		// de ExempleDS dans le fichier persistence.xml
		return ShrinkWrap.create(WebArchive.class, "test.war").addPackage(MaisonDeDisque.class.getPackage())
				.addPackage(MaisonDeDisqueDAO.class.getPackage())
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@PersistenceContext
	EntityManager em;

	@Inject
	UserTransaction utx;

	@EJB
	MaisonDeDisqueDAO maisonDAO;

	@Before
	public void preparePersistenceTest() throws Exception {
		startTransaction();
		System.out.println("Deleting old records");
		em.createQuery("delete from MaisonDeDisque").executeUpdate();
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
		MaisonDeDisque maison = new MaisonDeDisque();
		maison.setNom("TotoRecords");
		em.persist(maison);

		System.out.println("Selecting (using JPQL)...");
		List<MaisonDeDisque> maisons = em.createQuery("from MaisonDeDisque m", MaisonDeDisque.class).getResultList();

		System.out.println("Found " + maisons.size() + " maisons");
		assertEquals(1, maisons.size());
		assertEquals("TotoRecords", maisons.get(0).getNom());
	}

	@Test
	public void findAllTest() {
		assertEquals(0, maisonDAO.findAll().size());
		fillDB(2);
		assertEquals(2, maisonDAO.findAll().size());
	}
	
	@Test
	public void findByIdTest() {
		assertTrue(maisonDAO.findById(1)==null);
		fillDB(2);
		List<MaisonDeDisque> maisons = maisonDAO.findAll();
		MaisonDeDisque maison = maisons.get(0);
		assertFalse(maisonDAO.findById(maison.getId())==null);
		assertEquals(maison.getNom(), maisonDAO.findById(maison.getId()).getNom());
	}
	
	@Test
	public void addTest(){
		fillDB(2);
		assertEquals(2, maisonDAO.findAll().size());
		MaisonDeDisque maison = new MaisonDeDisque();
		maison.setNom("MaisonAddTest");
		assertTrue(maison.getId()<=0);
		maisonDAO.add(maison);
		assertTrue(maison.getId()>0);
		assertEquals(3, maisonDAO.findAll().size());
	}
	
	//
	@Test(expected = EJBTransactionRolledbackException.class)
	public void addTestFailure(){
		MaisonDeDisque maison = new MaisonDeDisque();
		maison.setNom("MaisonAddTest");
		maisonDAO.add(maison);
		
		//Expected failure when adding mains with the same Id
		MaisonDeDisque maison2 = new MaisonDeDisque();
		maison2.setId(maison.getId());
		maison2.setNom("plop");
		
		maisonDAO.add(maison2);
	}

	private void fillDB(int n) {
		for (int i = 0; i < n; i++) {
			MaisonDeDisque maison = new MaisonDeDisque();
			maison.setNom("MaisonAutofill" + 1);
			em.persist(maison);
		}
	}


	private void startTransaction() throws Exception {
		utx.begin();
		em.joinTransaction();
	}
}
