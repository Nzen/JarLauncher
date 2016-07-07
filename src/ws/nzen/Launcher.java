/** see License.md */
package ws.nzen;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ws.nzen.model.ArgBundle;
import ws.nzen.model.JarLocation;

/**  */
public class Launcher
{
	private Path whereIsJvm;
	private Path whereIsJar;
	private String[] argsOld;
	private List<String> fargs;
	private ArgBundle args;

	/** old version */
	public Launcher( Path jvm, String toLaunch, String[] itsArgs )
	{
		whereIsJvm = jvm;
		whereIsJar = Paths.get( toLaunch ).toAbsolutePath();
		// throws InvalidPathException - if the path string cannot be converted to a Path
		argsOld = itsArgs;
		fargs = new java.util.ArrayList<String>();
	}

	/**  */
	public Launcher( Path jvm, JarLocation toLaunch, ArgBundle itsArgs )
	{
		whereIsJvm = jvm;
		whereIsJar = Paths.get( toLaunch.getLocation() ).toAbsolutePath();
		args = itsArgs;
	}

	/**  */
	public void runJar()
	{
		List<String> commandComponents = new LinkedList<String>();
		commandComponents.add( whereIsJvm.toString() );
		commandComponents.add( "-jar" );
		commandComponents.add( whereIsJar.getFileName().toString() );
		commandComponents.addAll( args.getFlags() );
		ProcessBuilder yourJar = new ProcessBuilder( commandComponents );
		yourJar.directory( whereIsJar.getParent().toFile() );

		/*for ( String arg : commandComponents )
			System.out.print( " "+ arg );  // also 4TESTS */

		if ( args.isNeedsIo() )
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

}















