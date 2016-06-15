/** see License.md */
package ws.nzen;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import ws.nzen.model.ArgBundle;
import ws.nzen.model.JarLocation;

/**  */
public class Launcher
{
	private Path whereIsJvm;
	private Path whereIsJar;
	private String[] args;

	/** old version */
	public Launcher( Path jvm, String toLaunch, String[] itsArgs )
	{
		whereIsJvm = jvm;
		whereIsJar = Paths.get( toLaunch ).toAbsolutePath();
		// throws InvalidPathException - if the path string cannot be converted to a Path
		args = itsArgs;
	}

	/**  */
	public Launcher( Path jvm, JarLocation toLaunch, ArgBundle itsArgs )
	{
		whereIsJvm = jvm;
	}

	/**  */
	public void runJar()
	{
		List<String> commandComponents = new LinkedList<String>();
		commandComponents.add( whereIsJvm.toString() );
		commandComponents.add( "-jar" );
		commandComponents.add( whereIsJar.getFileName().toString() );
		// System.out.println( whereIsJar.toString() ); // 4TESTS
			// FIX cheating for now. have the model split these
		for ( String currArg : args )
		{
			commandComponents.add( currArg );
		}
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

	/**  */
	private boolean needsIo()
	{
		for ( String each : args )
			if (each.equals( "-debug" )) // eventually do something more robust
				return true;
		// else
		return false;
	}

}















