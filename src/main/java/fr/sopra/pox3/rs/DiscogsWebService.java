package fr.sopra.pox3.rs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

public interface DiscogsWebService {
	
	@Path("/database/search")
	@GET
	@Produces("application/json")
	public DiscogsSearchArtistResponse searchArtist(@QueryParam("q") String dunno,@QueryParam("type") String MUSTBEARTIST /*,...*/);
}
