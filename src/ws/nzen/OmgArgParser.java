/** see License.md */
package ws.nzen;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

/**
 * @author nzen
 *
 */
public class OmgArgParser
{
	private List<Path> jarPaths;
	private List<String> jarArgs;

	public JarModel parseToModel()
	{
		JarModel persisted = new JarModel();
		Path here = Paths.get("../../SplainTime.jar");
		String howToInit = "-h";
		jarPaths = new LinkedList<Path>();
		jarPaths.add(here);
		jarArgs = new LinkedList<String>();
		jarArgs.add(howToInit);
		persisted.setJarPaths(jarPaths);
		persisted.setJarArgs(jarArgs);
		return persisted;
	}

}
