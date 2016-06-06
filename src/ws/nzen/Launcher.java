/** see License.md */
package ws.nzen;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class Launcher
{
	private Path whereIsJvm;
	private Path whereIsJar;
	private String args;

	public Launcher( Path jvm, Path toLaunch, String itsArgs )
	{
		// like construct a processbuilder from that or whatever
		whereIsJvm = jvm;
		whereIsJar = toLaunch;
		args = itsArgs;
	}

	public void runJar()
	{
		List<String> commandComponents = new LinkedList<String>();
		commandComponents.add( whereIsJvm.toString() );
		commandComponents.add( "-jar" );
		commandComponents.add( whereIsJar.getFileName().toString() );
		commandComponents.add( args );
		ProcessBuilder yourJar = new ProcessBuilder( commandComponents );
		yourJar.directory( whereIsJar.getParent().toFile() );
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
