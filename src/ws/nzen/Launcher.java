/** see License.md */
package ws.nzen;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

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
		String command = whereIsJvm.toString() +" -jar "+ whereIsJar.getFileName() +" "+ args;
		ProcessBuilder yourJar = new ProcessBuilder( command );
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

	/** tired of getting proof of concept under three layers of abstraction */
	private static void remedial()
	{
		String what = "/usr/bin/java -jar"; // doesn't work
		java.util.List<String> separated = new java.util.LinkedList<String>();
		separated.add( "/usr/bin/java" );
		separated.add( "-jar" );
		separated.add( "SplainTime.jar" );
		ProcessBuilder yourJar = new ProcessBuilder( separated );
		// yourJar.directory( java.nio.file.Paths.get( "" ).toFile() );
		try
		{
			yourJar.inheritIO();
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
