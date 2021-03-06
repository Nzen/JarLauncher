/** see License.md */
package ws.nzen.jarl.parser;

import java.nio.file.Path;

import ws.nzen.jarl.JarLauncher;

/**  */
public interface ConfigParser
{

	/** so I can recover the control flow */
	public void setCompletionListener( JarLauncher leSigh );

	/** what I am parsing */
	public void setPathToConfig( Path config );

	/** parses and calls completionListener; throws something if no path */
	public void parseConfig();

}
























