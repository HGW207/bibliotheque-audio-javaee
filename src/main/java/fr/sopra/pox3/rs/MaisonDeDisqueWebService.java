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

@Path( "/maisons" )
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
public class MaisonDeDisqueWebService
{
	@EJB
	private MaisonDeDisqueDAO maisonDeDisqueDAO;
	
	@EJB
	private AuteurDAO auteurDAO;

	@GET
	@Path( "/{id}" )
	@Produces( MediaType.APPLICATION_JSON )
	public MaisonDeDisqueDTO findById( @PathParam( "id" ) int id )
	{
		MaisonDeDisque maison = maisonDeDisqueDAO.findById( id );
		if( maison == null )
			return null;
		
		MaisonDeDisqueDTO dto = entityToDTO(maison,true);
		
		return dto;
	}

	@GET
	@Produces( MediaType.APPLICATION_JSON )
	public List<MaisonDeDisqueDTO> findAll()
	{
		List<MaisonDeDisque> maisons = maisonDeDisqueDAO.findAll();
		if( maisons == null )
			return null;

		List<MaisonDeDisqueDTO> dtos = new ArrayList<>();

		for( MaisonDeDisque maison : maisons )
			dtos.add( entityToDTO(maison) );

		return dtos;
	}

	@POST
	@Consumes( MediaType.APPLICATION_JSON )
	@Produces( MediaType.APPLICATION_JSON )
	public MaisonDeDisqueDTO create( MaisonDeDisqueDTO maison )
	{
		MaisonDeDisque maisonEntity = new MaisonDeDisque();
		maisonEntity.setNom( maison.getNom() );

		maisonDeDisqueDAO.add( maisonEntity );

		maison.setId( maisonEntity.getId() );

		return maison;
	}

	@PUT
	@Consumes( MediaType.APPLICATION_JSON )
	@Produces( MediaType.APPLICATION_JSON )
	public MaisonDeDisqueDTO update( MaisonDeDisqueDTO maison )
	{
		MaisonDeDisque maisonEntity = new MaisonDeDisque();
		maisonEntity.setId( maison.getId() );
		maisonEntity.setNom( maison.getNom() );

		maisonDeDisqueDAO.update( maisonEntity );

		return maison;
	}

	@DELETE
	@Path( "/{id}" )
	public void delete( @PathParam( "id" ) int id )
	{
		maisonDeDisqueDAO.deleteById( id );
	}


	
	@POST
	@Path("{id}/addAuteur")
	public MaisonDeDisqueDTO addAuteur(@PathParam("id") int id,AuteurDTO auteurDTO){
		MaisonDeDisque maison = maisonDeDisqueDAO.findById(id);
		
		Auteur auteur = auteurDAO.findById(auteurDTO.getId());
		if(auteur==null){
			auteur = new Auteur();
			auteur.setNom(auteurDTO.getNom());
			auteurDAO.add(auteur);
		}
		
		
		maison.addAuteur(auteur);
		return entityToDTO(maison,true);
	}
	
	public MaisonDeDisqueDTO entityToDTO(MaisonDeDisque maison,boolean getAllAttributes){
		MaisonDeDisqueDTO maisonDTO = new MaisonDeDisqueDTO();
		maisonDTO.setId(maison.getId());
		maisonDTO.setNom(maison.getNom());
		if (getAllAttributes){
			if (maison.getAuteurs()!=null){
				List<AuteurDTO> auteursDTO = new ArrayList<>();
				AuteurWebService ws = new AuteurWebService();
				for(Auteur auteur : maison.getAuteurs()){
					auteursDTO.add(ws.entityToDTO(auteur));
				}
				maisonDTO.setAuteurs(auteursDTO);
			}
		}
		return maisonDTO;
	}

	public MaisonDeDisqueDTO entityToDTO(MaisonDeDisque maison){
		return entityToDTO(maison, false);
	}
		
}
