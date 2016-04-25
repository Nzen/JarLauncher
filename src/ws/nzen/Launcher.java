/** see License.md */
package ws.nzen;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Launcher
{
	private Path whereIsJar;
	private String args;

	public Launcher( JarModel yeah, String itsLate )
	{
		// like construct a processbuilder from that or whatever
		whereIsJar = Paths.get(itsLate); // probably itsLate is the selection indicies or something?
		//whereIsJar = yeah.getJarPaths().get( itsLate[0] );
		args = ""; // yeah.getJarArgs().get( itsLate[1] );
	}

	public void runJar()
	{
		String command = "java -jar "+ whereIsJar.toString()
				+" "+ args;
		ProcessBuilder yourJar = new ProcessBuilder( command );
		//yourJar.directory( whereIsJar.toAbsolutePath().getParent().toFile() );
		if ( needsIo() )
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

	private boolean needsIo()
	{
		return args.contains( "-debug" ); // eventually do something more robust
	}

}
