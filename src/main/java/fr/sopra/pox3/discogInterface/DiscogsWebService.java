package fr.sopra.pox3.discogInterface;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

public interface DiscogsWebService {

	static final String USER_AGENT = "datMyApp +http://myApp.ph";
	static final String APPLICATION_ADRESS = "https://api.discogs.com";

	@GET
	@Path("/database/search")
	ArtistSearchResponse searchArtist(@QueryParam("q") String term,
										@QueryParam("type") String mustBeArtist,
										@QueryParam("key") String key,
										@QueryParam("secret") String secret,
										@HeaderParam("User-Agent") String userAgent);
	// https://api.discogs.com/database/search?q=Toto&type=artist&key=ZzafBwkhtVyRsAuxnjCV&secret=jqMyEUiyUtojeAKZvFsGOcKrfapycMst
	@GET
	@Path("/artists/{artist_id}/releases")
	ReleaseSearchResponse searchReleasesOfAnArtist(@PathParam("artist_id") int id,
													@QueryParam("key") String key,
													@QueryParam("secret") String secret,
													@HeaderParam("User-Agent") String userAgent);
}