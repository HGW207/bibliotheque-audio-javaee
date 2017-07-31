package fr.sopra.pox3.rs;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fr.sopra.pox3.dto.AuteurDTO;
import fr.sopra.pox3.dto.MaisonDeDisqueDTO;
import fr.sopra.pox3.ejb.AuteurDAO;
import fr.sopra.pox3.ejb.MaisonDeDisqueDAO;
import fr.sopra.pox3.entities.Auteur;
import fr.sopra.pox3.entities.MaisonDeDisque;

@Path("/auteurs")
public class AuteurWebService {

	@EJB
	private AuteurDAO auteurDAO;

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public AuteurDTO findById(@PathParam("id") int id) {
		Auteur auteur = auteurDAO.findById(id);
		if (auteur == null)
			return null;

		return dtoFromEntity(auteur);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<AuteurDTO> findAll() {
		List<Auteur> auteurs = auteurDAO.findAll();
		if (auteurs == null)
			return null;

		List<AuteurDTO> dtos = new ArrayList<>();

		for (Auteur auteur : auteurs)
			dtos.add(dtoFromEntity(auteur));

		return dtos;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AuteurDTO create(AuteurDTO auteur) {
		Auteur auteurEntity = new Auteur();
		auteurEntity.setNom(auteur.getNom());

		auteurDAO.add(auteurEntity);

		auteur.setId(auteurEntity.getId());

		return auteur;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AuteurDTO update(AuteurDTO auteur) {
		Auteur auteurEntity = new Auteur();
		auteurEntity.setId(auteur.getId());
		auteurEntity.setNom(auteur.getNom());

		auteurDAO.update(auteurEntity);

		return auteur;
	}

	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") int id) {
			auteurDAO.deleteById(id);
	}

	private AuteurDTO dtoFromEntity(Auteur auteur) {
		if (auteur == null)
			return null;

		AuteurDTO dto = new AuteurDTO();

		dto.setId(auteur.getId());
		dto.setNom(auteur.getNom());

		return dto;
	}
}
