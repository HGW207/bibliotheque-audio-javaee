package fr.sopra.pox3.dto;

import java.util.List;

import fr.sopra.pox3.entities.Auteur;

public class MaisonDeDisqueDTO
{
	
	private int id;
	private String nom;
	private List<AuteurDTO> auteursDTO;

	public int getId()
	{
		return id;
	}

	public void setId( int id )
	{
		this.id = id;
	}

	public String getNom()
	{
		return nom;
	}

	public void setNom( String nom )
	{
		this.nom = nom;
	}

	public List<AuteurDTO> getAuteurs() {
		return auteursDTO;
	}

	public void setAuteurs(List<AuteurDTO> auteurs) {
		this.auteursDTO = auteurs;
	}
	
	
}
