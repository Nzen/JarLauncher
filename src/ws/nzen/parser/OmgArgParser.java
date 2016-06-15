/** see License.md */
package ws.nzen.parser;

import java.util.LinkedList;
import java.util.List;

import ws.nzen.model.JarModel;

/**
 * @author nzen
 *
 */
@Deprecated
public class OmgArgParser
{
	private List<String> jarPaths;
	private List<String> jarArgs;

	public JarModel parseToModel()
	{
		JarModel persisted = new JarModel();
		String here = "../../SplainTime.jar";
		String howToInit = "-h";
		jarPaths = new LinkedList<String>();
		jarPaths.add(here);
		jarArgs = new LinkedList<String>();
		jarArgs.add(howToInit);
		persisted.setJarPaths(jarPaths);
		persisted.setJarArgs(jarArgs);
		return persisted;
	}

}
