/** see License.md */
package ws.nzen.jarl;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import ws.nzen.jarl.model.ArgBundle;
import ws.nzen.jarl.model.JarLocation;

/**  */
public class Launcher
{
	private Path whereIsJvm;
	private JarLocation whereIsJar;
	private ArgBundle args;

	/**  */
	public Launcher( Path jvm, JarLocation toLaunch, ArgBundle itsArgs )
	{
		whereIsJvm = jvm;
		whereIsJar = toLaunch;
		args = itsArgs;
	}

	/** build process from the JarModel */
	public void runJar( JarLauncher restarter )
	{
		Path jarFile = Paths.get( whereIsJar.getLocation() ).toAbsolutePath();
		List<String> commandComponents = new LinkedList<String>();
		commandComponents.add( whereIsJvm.toString() );
		Iterator<String> jvmFlags = whereIsJar.getTunerIterator();
		while ( jvmFlags.hasNext() )
		{
			commandComponents.add( jvmFlags.next() );
		}
		commandComponents.add( "-jar" );
		commandComponents.add( jarFile.getFileName().toString() );
		commandComponents.addAll( args.getFlags() );
		ProcessBuilder yourJar = new ProcessBuilder( commandComponents );
		yourJar.directory( jarFile.getParent().toFile() );
		/*for ( String arg : commandComponents )
			System.out.print( " "+ arg );  // also 4TESTS */
		if ( args.isNeedsIo() )
		{
			yourJar.inheritIO();
		}
		try
		{
			yourJar.start().waitFor();
			restarter.showPreviousOptions();
		}
		catch ( IOException ie )
		{
			System.err.println( "Couldn't launch jar because "+ ie );
		}
		catch ( InterruptedException ie )
		{
			System.err.println( "Programmatically told to quit via "+ ie );
			return;
		}
	}

}















