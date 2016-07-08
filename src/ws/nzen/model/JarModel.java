/** see License.md */
package ws.nzen.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author nzen
 * memory store of paths and args
 */
public class JarModel
{
	@Deprecated
	private List<String> jarPaths; // IMPROVE use a map instead
	@Deprecated
	private List<String> oldJarArgs;

	private int jlId = 0, argId = 0;
	private Map<String, JarLocation> jarLocations;
	private Map<String, ArgBundle> args;
	private String jvmLocation = "";
	private final String separ = ":";

	/**  */
	public JarModel()
	{
		jarLocations = new HashMap<String, JarLocation>();
		args = new HashMap<String, ArgBundle>();
	}

	public JarModel( List<String> theJars, List<String> theOptions )
	{
		setJarPaths( theJars );
		setJarArgs( theOptions );
	}

	public JarModel( String jvm, List<String> theJars, List<String> theOptions )
	{
		setJvmLocation( jvm );
		setJarPaths( theJars );
		setJarArgs( theOptions );
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
		jarLocations.put( Integer.toString( jlId ), pathDesc );
		jlId++;
	}

	/**  */
	public void addArgBundle( ArgBundle more )
	{
		args.put( Integer.toString( argId ), more );
		argId++;
	}

	/**  */
	public Set<Map.Entry<String, JarLocation>> getLocations()
	{
		return jarLocations.entrySet(); // IMPROVE use an iterator instead
	}

	/**  */
	public Set<Map.Entry<String, ArgBundle>> getArgs()
	{
		return args.entrySet();
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

	public List<String> getJarPaths()
	{
		return jarPaths;
	}

	public List<String> getJarArgs()
	{
		return oldJarArgs;
	}

	public void setJarPaths( List<String> pJarPaths )
	{
		if ( pJarPaths == null )
			this.jarPaths = new LinkedList<String>();
		else
			this.jarPaths = pJarPaths;
	}

	public void setJarArgs( List<String> pJarArgs )
	{
		if ( pJarArgs == null )
			this.oldJarArgs = new LinkedList<String>();
		else
			this.oldJarArgs = pJarArgs;
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
