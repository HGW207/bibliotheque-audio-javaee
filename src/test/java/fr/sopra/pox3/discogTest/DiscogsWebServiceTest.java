package fr.sopra.pox3.discogTest;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.sopra.pox3.discogInterface.ArtistSearchResponse;
import fr.sopra.pox3.discogInterface.DiscogsArtist;
import fr.sopra.pox3.discogInterface.DiscogsRelease;
import fr.sopra.pox3.discogInterface.DiscogsSecret;
import fr.sopra.pox3.discogInterface.DiscogsWebService;
import fr.sopra.pox3.discogInterface.ReleaseSearchResponse;
import fr.sopra.pox3.ejb.AuteurDAO;
import fr.sopra.pox3.ejb.MaisonDeDisqueDAO;
import fr.sopra.pox3.entities.Auteur;
import fr.sopra.pox3.entities.MaisonDeDisque;

@RunWith(Arquillian.class)
public class DiscogsWebServiceTest {
	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war").addPackage(DiscogsWebService.class.getPackage())
				.addPackage(Auteur.class.getPackage()).addPackage(AuteurDAO.class.getPackage())
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@EJB
	AuteurDAO auteurDAO;
	@EJB
	MaisonDeDisqueDAO maisonDAO;
	@PersistenceContext
	EntityManager em;
	@Inject
	UserTransaction utx;
	
	

	@Test
	public void testDiscogsDeBase() throws Exception {
		ArtistSearchResponse response = getDiscogsService().searchArtist("Toto", "artist", DiscogsSecret.getKey(),
				DiscogsSecret.getSecret(), DiscogsWebService.USER_AGENT);
		DiscogsArtist artist = response.getResults().get(0);

		Auteur auteur = new Auteur();
		auteur.setNom(artist.getTitle());

		auteurDAO.add(auteur);

		ReleaseSearchResponse response2 = getDiscogsService().searchReleasesOfAnArtist(68693, DiscogsSecret.getKey(),
				DiscogsSecret.getSecret(), DiscogsWebService.USER_AGENT);
		

		int taille = maisonDAO.findAll().size();
		for (DiscogsRelease release : response2.getReleases()) {
			if (release.getLabel() != null) {
				MaisonDeDisque maison = new MaisonDeDisque();
				maison.setNom(release.getLabel());
			//	maison.addAuteur(auteur);
				maisonDAO.update(maison);
				taille = maisonDAO.findAll().size();
				System.out.println(taille);
			}
		}

		System.out.println(response2.getReleases().get(4).getLabel());

	}

	private DiscogsWebService getDiscogsService() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(DiscogsWebService.APPLICATION_ADRESS);

		DiscogsWebService discogsService = target.proxy(DiscogsWebService.class);
		return discogsService;
	}
	
	@Before
	public void preparePersistenceTest() throws Exception {
		startTransaction();
		System.out.println("Deleting old records");
		em.createQuery("delete from Auteur").executeUpdate();
		em.createQuery("delete from MaisonDeDisque").executeUpdate();
		utx.commit();

		em.clear();

		startTransaction();
	}
	
	@After
	public void commitTransaction() throws Exception {
		utx.commit();
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