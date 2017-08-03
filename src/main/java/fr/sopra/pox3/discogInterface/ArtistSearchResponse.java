package fr.sopra.pox3.discogInterface;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties( ignoreUnknown = true )
public class ArtistSearchResponse {

	private List<DiscogsArtist> results;

	public List<DiscogsArtist> getResults()
	{
		return results;
	}

	public void setResults( List<DiscogsArtist> results )
	{
		this.results = results;
	}
}

