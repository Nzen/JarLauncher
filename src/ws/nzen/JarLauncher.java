/** see License.md */
package ws.nzen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;

/**
 * @author nzen
 *
 */
public class JarLauncher implements ActionListener
{
	Path toJvm;
	// use this instead
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
		ArgumentStore jarInfo = new ArgumentStore( filename );
		tableOfOptions = jarInfo.getJarOptions();
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
				tableOfOptions.getJarOfComboRef(toUse),
				tableOfOptions.getArgsOfComboRef(toUse) );
		platform.runJar();
	}

}











