/** see License.md */
package ws.nzen;

import java.util.LinkedList;
import java.util.List;

/**
 * @author nzen
 * memory store of paths and args
 */
public class JarModel
{
	private List<String> jarPaths;
	private List<String> jarArgs;
	private final String separ = ":";

	public JarModel()
	{
		throw new UnsupportedOperationException();
	}

	public JarModel( List<String> theJars, List<String> theOptions )
	{
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
		for ( String someArg : jarArgs )
		{
			if ( someArg.equals(chosenArgs) )
			{
				indOfPath = Integer.toString(ind);
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
	public String getArgsOfComboRef( String combinationReference )
	{
		String[] indicies = combinationReference.split( separ );
		if ( indicies.length < 2 || indicies[1].isEmpty() )
			return "";
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
		if ( indOfArg < 0 || indOfArg >= jarArgs.size() )
			return "";
		else
			return jarArgs.get( indOfArg );
	}

	public List<String> getJarPaths()
	{
		return jarPaths;
	}
	public List<String> getJarArgs()
	{
		return jarArgs;
	}
	public void setJarPaths( List<String> pJarPaths )
	{
		if ( pJarPaths == null )
			this.jarPaths = new java.util.LinkedList<String>();
		else
			this.jarPaths = pJarPaths;
	}
	public void setJarArgs( List<String> pJarArgs )
	{
		if ( pJarArgs == null )
			this.jarArgs = new java.util.LinkedList<String>();
		else
			this.jarArgs = pJarArgs;
	}

}
