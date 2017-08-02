package fr.sopra.pox3.ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.sopra.pox3.entities.Auteur;
import fr.sopra.pox3.entities.MaisonDeDisque;

@Stateless
public class InteractionMaisonAuteurDAO {

	@PersistenceContext(name = "Bibliotheque")
	private EntityManager em;

	@EJB
	MaisonDeDisqueDAO maisonDAO;
	@EJB
	AuteurDAO auteurDAO;

	public Auteur updateMaison(Auteur auteur, MaisonDeDisque maison) {
		auteur.setMaison(maison);
		auteurDAO.update(auteur);
		return auteur;
	}

	public void removeMaisonFromAllAuteurs(MaisonDeDisque maison) {
		for (Auteur auteur : maison.getAuteurs()){
			auteur.removeMaison();
			auteurDAO.update(auteur);
		}
	}

}
