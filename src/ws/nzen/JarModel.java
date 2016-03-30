/** see License.md */
package ws.nzen;

import java.nio.file.Path;
import java.util.List;

/**
 * @author nzen
 *
 */
public class JarModel
{
	private List<Path> jarPaths;
	private List<String> jarArgs;

	public List<Path> getJarPaths()
	{
		return jarPaths;
	}
	public List<String> getJarArgs()
	{
		return jarArgs;
	}
	public void setJarPaths( List<Path> jarPaths )
	{
		this.jarPaths = jarPaths;
	}
	public void setJarArgs( List<String> jarArgs )
	{
		this.jarArgs = jarArgs;
	}

}
