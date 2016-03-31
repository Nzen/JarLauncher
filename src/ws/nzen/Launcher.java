/** see License.md */
package ws.nzen;

import java.io.IOException;
import java.nio.file.Path;

public class Launcher
{
	private Path whereIsJar;
	private String args;

	public Launcher( JarModel yeah, String itsLate )
	{
		// like construct a processbuilder from that or whatever
		whereIsJar = yeah.getJarPaths().get(0);
		args = yeah.getJarArgs().get(0);
	}

	public void runJar()
	{
		String command = "java -jar "+ whereIsJar.toString()
				+" "+ args;
		ProcessBuilder yourJar = new ProcessBuilder( command );
		yourJar.directory( whereIsJar.toFile() );
		if ( whetherNeedsIo() )
		{
			yourJar.inheritIO();
		}
		try
		{
			yourJar.start();
		}
		catch ( IOException ie )
		{
			System.err.println( "Couldn't launch jar because "+ ie.toString() );
		}
	}

	private boolean whetherNeedsIo()
	{
		return args.contains( "-debug" ); // eventually do something more robust
	}

}
