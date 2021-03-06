package fr.sopra.pox3;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import fr.sopra.pox3.rs.AuteurWebService;
import fr.sopra.pox3.rs.DisqueWebService;
import fr.sopra.pox3.rs.MaisonDeDisqueWebService;

@ApplicationPath( "/api" )
public class BibliothequeWSApplication extends Application
{
	@Override
	public Set<Class<?>> getClasses()
	{
		Set<Class<?>> result = new HashSet<>();
		result.add( MaisonDeDisqueWebService.class );
		result.add( AuteurWebService.class );
		result.add(DisqueWebService.class);
		return result;
	}
}
