/** see License.md */
package ws.nzen;

import java.util.List;

/**
 * @author nzen
 *
 */
public class JarModel
{
	private List<String> jarPaths;
	private List<String> jarArgs;

	public String getCombinationReference( String aPath, String someArgs )
	{
		return "UNREADY";
	}

	public String getJarOfComboRef( String combinationReference )
	{
		return "UNREADY";
	}

	public String getArgsOfComboRef( String combinationReference )
	{
		return "UNREADY";
	}

	public List<String> getJarPaths()
	{
		return jarPaths;
	}
	public List<String> getJarArgs()
	{
		return jarArgs;
	}
	public void setJarPaths( List<String> jarPaths )
	{
		this.jarPaths = jarPaths;
	}
	public void setJarArgs( List<String> jarArgs )
	{
		this.jarArgs = jarArgs;
	}

}
