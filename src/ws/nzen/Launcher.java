/** see License.md */
package ws.nzen;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class Launcher
{
	private Path whereIsJvm;
	private Path whereIsJar;
	private String args;

	public Launcher( Path jvm, String toLaunch, String itsArgs )
	{
		whereIsJvm = jvm;
		whereIsJar = Paths.get( toLaunch ).toAbsolutePath();
		// throws InvalidPathException - if the path string cannot be converted to a Path
		args = itsArgs;
	}

	public void runJar()
	{
		List<String> commandComponents = new LinkedList<String>();
		commandComponents.add( whereIsJvm.toString() );
		commandComponents.add( "-jar" );
		commandComponents.add( whereIsJar.getFileName().toString() );
		// System.out.println( whereIsJar.toString() ); // 4TESTS
		commandComponents.add( args );
		ProcessBuilder yourJar = new ProcessBuilder( commandComponents );
		yourJar.directory( whereIsJar.getParent().toFile() );

		/*for ( String arg : commandComponents )
			System.out.print( " "+ arg );  // also 4TESTS */

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
