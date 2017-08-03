package fr.sopra.pox3.discogInterface;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties( ignoreUnknown = true )
public class ReleaseSearchResponse {

	List<DiscogsRelease> releases;

	public List<DiscogsRelease> getReleases() {
		return releases;
	}

	public void setReleases(List<DiscogsRelease> releases) {
		this.releases = releases;
	}
	
	
}
