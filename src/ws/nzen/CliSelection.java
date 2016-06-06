/** see License.md */
package ws.nzen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

/**
 * @author nzen
 *
 */
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
	public void setVisible( boolean yeahNow )
	{
		Scanner input = new Scanner( System.in );
		System.out.println( "Available jars:" );
		int ind = 0;
		for ( String jarPath : knowsJars.getJarPaths() )
		{
			System.out.print( ind +" "+ jarPath );
			ind++;
		}
		System.out.print( "-- ? " );
		Integer jarInd;
		try
		{
			jarInd = input.nextInt();
		}
		catch ( Exception ugh )
		{
			System.err.print( "Grr couldn't make that an int :[ "+ ugh );
		}
		System.out.println( "Available jars:" );
		ind = 0;
		for ( String arg : knowsJars.getJarArgs() )
		{
			System.out.print( ind +" "+ arg );
			ind++;
		}
		Integer argInd;
		try
		{
			argInd = input.nextInt();
		}
		catch ( Exception ugh )
		{
			System.err.print( "Grr couldn't make that an int :[ "+ ugh );
		}
		reactor.actionPerformed(new ActionEvent( this, 6345,
				knowsJars.getCombinationReference("jar","args") ));
	}

	@Override
	public void addActionListener( ActionListener toRespondToSelection )
	{
		reactor = toRespondToSelection;
	}

}
