/** see License.md */
package ws.nzen.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import ws.nzen.model.JarModel;

/** shows options via stdin */
public class CliSelection implements SelectionUi
{
	private ActionListener reactor;
	private JarModel knowsJars;

	@Override
	public void setJarModel( JarModel toBuildUiAround )
	{
		knowsJars = toBuildUiAround;
	}

	@Override
	/** show options, get selection, send to launcher */
	public void setVisible( boolean yeahNow )
	{
		Scanner input = new Scanner( System.in );
		System.out.println( "Available jars:" );
		int ind = 0;
		for ( String jarPath : knowsJars.getJarPaths() )
		{
			System.out.println( ind +" "+ jarPath );
			ind++;
		}
		System.out.print( "-- ? " );
		Integer jarInd = -1; // IMPROVE
		String jarChosen = "";
		try
		{
			jarInd = input.nextInt();
			jarChosen = knowsJars.getJarPaths().get( jarInd );
		}
		catch ( Exception ugh )
		{
			System.err.print( "Grr couldn't make that an int :[ "+ ugh );
		}

		System.out.println( "Available args:" );
		ind = 0;
		for ( String arg : knowsJars.getJarArgs() )
		{
			System.out.println( ind +" "+ arg );
			ind++;
		}
		System.out.print( "-- ? " );
		int argInd = -1;
		String argChosen = "";
		try
		{
			argInd = input.nextInt();
			argChosen = knowsJars.getJarArgs().get( argInd );
		}
		catch ( Exception ugh )
		{
			System.err.print( "Grr couldn't make that an int :[ "+ ugh );
		}

		reactor.actionPerformed(new ActionEvent( this, 6345,
				knowsJars.getCombinationReference(
						jarChosen, argChosen ) ));
	}

	@Override
	public void addActionListener( ActionListener toRespondToSelection )
	{
		reactor = toRespondToSelection;
	}

}
