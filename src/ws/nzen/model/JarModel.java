/** see License.md */
package ws.nzen.model;

import java.util.LinkedList;
import java.util.List;

/**
 * @author nzen
 * memory store of paths and args
 */
public class JarModel
{
	private List<String> jarPaths; // IMPROVE use a map instead
	private List<String> oldJarArgs;

	private List<String[]> jarLocations;
	private List<ArgBundle> args;
	private String jvmLocation = "";
	private final String separ = ":";

	public JarModel()
	{
		jarLocations = new LinkedList<String[]>();
		args = new LinkedList<ArgBundle>();
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

	public String getCombinationReference( String aPath, String chosenArgs )
	{
		String indOfPath = "";
		int ind = 0;
		for ( String somePath : jarPaths )
		{
			if ( somePath.equals(aPath) )
			{
				indOfPath = Integer.toString(ind);
				break;
			}
			ind++;
		}
		ind = 0;
		String indOfArg = "";
		for ( String someArg : oldJarArgs )
		{
			if ( someArg.equals(chosenArgs) )
			{
				indOfArg = Integer.toString(ind);
				break;
			}
			ind++;
		}
		return indOfPath + separ + indOfArg;
	}

	public String getJarOfComboRef( String combinationReference )
	{
		String[] indicies = combinationReference.split( separ );
		if ( indicies.length < 1 || indicies[0].isEmpty() )
			return "";
		int indOfJar = -1;
		try
		{
			indOfJar = Integer.parseInt( indicies[ 0 ] );
		}
		catch ( NumberFormatException leSigh )
		{
			System.err.print( indicies[ 0 ]
					+" is not a number callee "+ leSigh );
		}
		if ( indOfJar < 0 || indOfJar >= jarPaths.size() )
			return "";
		else
			return jarPaths.get( indOfJar );
	}

	/** comboRef should be \w:\d+ use latter to find the matching arg */
	public String[] getArgsOfComboRef( String combinationReference )
	{
		String[] indicies = combinationReference.split( separ );
		if ( indicies.length < 2 || indicies[1].isEmpty() )
			return new String[0];
		int indOfArg = -1;
		try
		{
			indOfArg = Integer.parseInt( indicies[ 1 ] );
		}
		catch ( NumberFormatException leSigh )
		{
			System.err.print( indicies[ 1 ]
					+" is not a number callee "+ leSigh );
		}
		if ( indOfArg < 0 || indOfArg >= oldJarArgs.size() )
			return new String[0];
		else
			return oldJarArgs.get( indOfArg ).split( " " );
	}

	public void addJarLocation( String[] pathDesc )
	{
		if ( pathDesc.length == 2 )
		{
			jarLocations.add( pathDesc );
		}
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
