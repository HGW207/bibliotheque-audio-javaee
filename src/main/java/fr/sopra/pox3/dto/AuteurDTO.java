package fr.sopra.pox3.dto;

import java.util.Optional;

public class AuteurDTO {

	private int id;
	private String nom;
	private Optional<MaisonDeDisqueDTO> maisonDTO = Optional.empty();

	public MaisonDeDisqueDTO getMaisonDTO() {
		if (maisonDTO.isPresent())
			return maisonDTO.get();
		else
			return null;
	}

	public void setMaisonDTO(MaisonDeDisqueDTO maisonDTO) {
		this.maisonDTO = Optional.of(maisonDTO);
	}

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
}
