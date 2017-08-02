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

import fr.sopra.pox3.entities.Auteur;

@RunWith(Arquillian.class)
public class AuteurDAOTest {
	@Deployment
	public static Archive<?> createDeployment() {
		// TODO Ajouter la data source pour les tests et l'utiliser à la place
		// de ExempleDS dans le fichier persistence.xml
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackage(Auteur.class.getPackage())
				.addPackage(AuteurDAO.class.getPackage())
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@PersistenceContext
	EntityManager em;

	@Inject
	UserTransaction utx;

	@EJB
	AuteurDAO auteurDAO;

	@Before
	public void preparePersistenceTest() throws Exception {
		startTransaction();
		System.out.println("Deleting old records");
		em.createQuery("delete from Auteur").executeUpdate();
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
		Auteur auteur = new Auteur();
		auteur.setNom("TotoRecords");
		em.persist(auteur);

		System.out.println("Selecting (using JPQL)...");
		List<Auteur> auteurs = em.createQuery("from Auteur m", Auteur.class).getResultList();

		System.out.println("Found " + auteurs.size() + " auteurs");
		assertEquals(1, auteurs.size());
		assertEquals("TotoRecords", auteurs.get(0).getNom());
	}

	@Test
	public void findAllTest() {
		assertEquals(0, auteurDAO.findAll().size());
		fillDB(2);
		assertEquals(2, auteurDAO.findAll().size());
	}
	
	@Test
	public void findByIdTest() {
		assertTrue(auteurDAO.findById(1)==null);
		fillDB(2);
		List<Auteur> auteurs = auteurDAO.findAll();
		Auteur auteur = auteurs.get(0);
		assertFalse(auteurDAO.findById(auteur.getId())==null);
		assertEquals(auteur.getNom(), auteurDAO.findById(auteur.getId()).getNom());
	}
	
	@Test
	public void addTest(){
		fillDB(2);
		assertEquals(2, auteurDAO.findAll().size());
		Auteur auteur = new Auteur();
		auteur.setNom("AuteurAddTest");
		assertTrue(auteur.getId()<=0);
		auteurDAO.add(auteur);
		assertTrue(auteur.getId()>0);
		assertEquals(3, auteurDAO.findAll().size());
	}
	
	//
	@Test(expected = EJBTransactionRolledbackException.class)
	public void addTestFailure(){
		Auteur auteur = new Auteur();
		auteur.setNom("MaisonAddTest");
		auteurDAO.add(auteur);
		
		//Expected failure when adding mains with the same Id
		Auteur auteur2 = new Auteur();
		auteur2.setId(auteur.getId());
		auteur2.setNom("plop");
		
		auteurDAO.add(auteur2);
	}

	private void fillDB(int n) {
		for (int i = 0; i < n; i++) {
			Auteur auteur = new Auteur();
			auteur.setNom("AuteurAutofill" + 1);
			em.persist(auteur);
		}
	}


	private void startTransaction() throws Exception {
		utx.begin();
		em.joinTransaction();
	}
}
