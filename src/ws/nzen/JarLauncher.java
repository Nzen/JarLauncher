/** see License.md */
package ws.nzen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;

import ws.nzen.parser.ConfigParser;
import ws.nzen.parser.ParserFactory;
import ws.nzen.ui.CliSelection;

/**
 * @author nzen
 *
 */
public class JarLauncher implements ActionListener
{

	private JarModel tableOfOptions;

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
			new JarLauncher();
		}
	}

	/** uses the default argfile to show options */
	public JarLauncher()
	{
		String testFile = "config.fastArg";
		sendToParser( testFile );
	}

	/** uses user supplied argfile to show options */
	public JarLauncher( String fromArg )
	{
		sendToParser( fromArg );
	}

	/** wait until parser is done */
	private void sendToParser( String filename )
	{
		ConfigParser dataGrinder = ParserFactory.viaExt( filename );
		dataGrinder.setCompletionListener( this );
	}

	/** model is ready, so show that to the user */
	public void showOptions( JarModel optionsFromConfig )
	{
		if ( optionsFromConfig == null )
		{
			System.err.print( "le sigh, parsing failed. quitting" );
			System.exit( 1 );
		}
		tableOfOptions = optionsFromConfig;
		SelectionUi toShow = new CliSelection();
		toShow.setJarModel( tableOfOptions );
		toShow.addActionListener( this );
		toShow.setVisible( true );
	}

	/** called by SelectionUi to indicate we are ready */
	public void actionPerformed( ActionEvent userSelection )
	{
		String toUse = userSelection.getActionCommand();
		Launcher platform = new Launcher(
				Paths.get( tableOfOptions.getJvmLocation() ),
				tableOfOptions.getJarOfComboRef(toUse),
				tableOfOptions.getArgsOfComboRef(toUse) );
		platform.runJar();
	}

}











