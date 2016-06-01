/** see License.md */
package ws.nzen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Path;

/**
 * @author nzen
 *
 */
public class JarLauncher implements ActionListener
{
	Path toJvm;
	ArgumentStore jarInfo;

	/**
	 * @param args potentially the arg file location
	 */
	public static void main( String[] args )
	{
		if ( args.length > 0 )
		{
			new JarLauncher( args[0] );
		}
		else
		{
			// new JarLauncher();
			remedial();
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

	/** uses the default argfile to show options */
	public JarLauncher()
	{
		String testFile = "config.omgArg";
		showOptionsToUser( testFile );
	}

	/** uses user supplied argfile to show options */
	public JarLauncher( String fromArg )
	{
		showOptionsToUser( fromArg );
	}

	/** prep selection ui so user can choose the jar to launch */
	private void showOptionsToUser( String filename )
	{
		jarInfo = new ArgumentStore( filename );
		JarModel tableOfOptions = jarInfo.getJarOptions();
		toJvm = jarInfo.getJvmLocation();
		SelectionUi toShow = new CliSelection();
		toShow.setJarModel( tableOfOptions );
		toShow.addActionListener( this );
		toShow.setVisible( true );
	}

	/** called by SelectionUi to indicate the  */
	public void actionPerformed( ActionEvent userSelection )
	{
		String toUse = userSelection.getActionCommand();
		Launcher platform = new Launcher(
				toJvm,
				jarInfo.jarCorrespondingTo(toUse),
				jarInfo.argsCorrespondingTo(toUse) );
		platform.runJar();
	}

}











