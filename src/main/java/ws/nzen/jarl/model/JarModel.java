/** see License.md */
package ws.nzen.jarl.model;

import java.util.TreeMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author nzen
 * memory store of paths and args
 */
public class JarModel
{

	private int jlId = 0, argId = 0;
	private Map<String, JarLocation> jarLocations;
	private Map<String, ArgBundle> args;
	private String jvmLocation = "";
	private final String separ = ":";

	/**  */
	public JarModel()
	{
		jarLocations = new TreeMap<>();
		args = new TreeMap<>();
	}

	/**  */
	public String getComboRef( String locationId, String argBundleId )
	{
		boolean validLoc = jarLocations.containsKey( locationId );
		boolean validArg = args.containsKey( argBundleId );
		if ( validLoc && validArg )
		{
			return locationId + separ + argBundleId;
		}
		else if ( validLoc )
		{
			return locationId + separ;
		}
		else if ( validArg )
		{
			return separ + argBundleId;
		}
		else
		{
			return "";
		}
	}

	/** what you want or null */
	public JarLocation getPathOfComboRef( String comboRef )
	{
		if ( comboRef.isEmpty() )
			return null;
		String[] indicies = comboRef.split( separ );
		if (indicies.length > 0
				&& jarLocations.containsKey( indicies[0] ))
		{
			return jarLocations.get( indicies[0] );
		}
		else
		{
			return null;
		}
	}

	/** what you want or null */
	public ArgBundle getArgOfComboRef( String comboRef )
	{
		if ( comboRef.isEmpty() )
			return null;
		String[] indicies = comboRef.split( separ );
		if ( indicies.length > 1
			&& args.containsKey( indicies[1] ))
		{
			return args.get( indicies[1] );
		}
		else
		{
			return null;
		}
	}

	/**  */
	public void addJarLocation( JarLocation pathDesc )
	{
		jarLocations.put( String.format( "%02d", jlId ), pathDesc );
		jlId++;
	}

	/**  */
	public void addArgBundle( ArgBundle more )
	{
		args.put( String.format( "%02d", argId ), more );
		argId++;
	}

	public Iterator<Map.Entry<String, JarLocation>> getLocations()
	{
		return jarLocations.entrySet().iterator();
	}

	public int numberOfLocations()
	{
		return jarLocations.size();
	}

	public Iterator<Map.Entry<String, ArgBundle>> getArgs()
	{
		return args.entrySet().iterator();
	}

	public int numberOfArgBundles()
	{
		return args.size();
	}

	/**  */
	public boolean locationsHas( String id )
	{
		return jarLocations.containsKey( id );
	}

	/**  */
	public boolean argsHas( String id )
	{
		return args.containsKey( id );
	}

	public String getJvmLocation()
	{
		return jvmLocation;
	}

	public void setJvmLocation( String jvmLocation )
	{
		this.jvmLocation = jvmLocation;
	}

}
