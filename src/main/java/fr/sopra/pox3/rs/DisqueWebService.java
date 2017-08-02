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

import fr.sopra.pox3.dto.DisqueDTO;
import fr.sopra.pox3.dto.MaisonDeDisqueDTO;
import fr.sopra.pox3.ejb.DisqueDAO;
import fr.sopra.pox3.entities.Disque;
import fr.sopra.pox3.entities.MaisonDeDisque;

@Path("/disques")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DisqueWebService {

	@EJB 
	DisqueDAO disqueDAO ;


	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public DisqueDTO findById(@PathParam("id") int id) {
		Disque disque = disqueDAO.findById(id);
		if (disque == null)
			return null;

		DisqueDTO dto = entityToDTO(disque);

		return dto;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<DisqueDTO> findAll() {
		List<Disque> disques = disqueDAO.findAll();
		if (disques == null)
			return null;

		List<DisqueDTO> dtos = new ArrayList<>();

		for (Disque disque : disques)
			dtos.add(entityToDTO(disque));

		return dtos;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public DisqueDTO create(DisqueDTO disque) {
		Disque disqueEntity = new Disque();
		disqueEntity.setNom(disque.getNom());

		disqueDAO.add(disqueEntity);

		disque.setId(disqueEntity.getId());

		return disque;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public DisqueDTO update(DisqueDTO disque) {
		Disque disqueEntity = new Disque();
		disqueEntity.setId(disque.getId());
		disqueEntity.setNom(disque.getNom());

		disqueDAO.update(disqueEntity);

		return disque;
	}

	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") int id) {
		disqueDAO.deleteById(id);
	}

//	@POST
//	@Path("{id}/addMaison")
//	public DisqueDTO addMaison(@PathParam("id") int id, MaisonDeDisqueDTO maisonDTO) {
//		Disque disque = disqueDAO.findById(id);
//		MaisonDeDisque maison = maisonDAO.findById(maisonDTO.getId());
//		if (disque==null)
//			throw new RuntimeException("Disque " + id + " not found");
//		
//		if (maison==null){
//			maisonDAO.add(maison);
//		}
//		
//		disque = interactionDAO.updateMaison(disque, maison);
//		return entityToDTO(disque,true);
//	}

	public DisqueDTO entityToDTO(Disque disque) {
		DisqueDTO disqueDTO = new DisqueDTO();
		disqueDTO.setId(disque.getId());
		disqueDTO.setNom(disque.getNom());

		return disqueDTO;
	}
	
}
