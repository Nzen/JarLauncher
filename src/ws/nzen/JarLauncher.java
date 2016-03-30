/** see License.md */
package ws.nzen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author nzen
 *
 */
public class JarLauncher implements ActionListener
{

	/**
	 * @param args
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

	public JarLauncher()
	{
		String testFile = "config.omgArg";
		showOptionsToUser( testFile );
	}

	public JarLauncher( String fromArg )
	{
		showOptionsToUser( fromArg );
	}

	private void showOptionsToUser( String filename )
	{
		ArgumentStore knowsLocation = new ArgumentStore( filename );
		JarModel tableOfOptions = knowsLocation.getJarOptions();
		SelectionUi toShow = new CliSelection();
		toShow.setJarModel( tableOfOptions );
		toShow.setVisible( true );
	}

	public void actionPerformed( ActionEvent userSelection )
	{
		String toUse = userSelection.getActionCommand();
		Launcher platform = new Launcher( new JarModel(), toUse );
		platform.runJar();
		/*
		harvest selection from ae or ui
		provide to launcher
		run
		*/
	}

}











