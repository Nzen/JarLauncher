/** see License.md */
package ws.nzen.jarl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;

import ws.nzen.jarl.model.JarModel;
import ws.nzen.jarl.parser.ConfigParser;
import ws.nzen.jarl.parser.ParserFactory;
import ws.nzen.jarl.ui.CliSelection;
import ws.nzen.jarl.ui.SelectionUi;

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
		String testFile = "config.xml";
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
		dataGrinder.parseConfig();
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


	void showPreviousOptions()
	{
		if ( tableOfOptions == null )
		{
			throw new RuntimeException(
					"asked for optinos before they've been parsed" );
		}
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
				tableOfOptions.getPathOfComboRef(toUse),
				tableOfOptions.getArgOfComboRef(toUse) );
		platform.runJar( this );
	}

}











