/** see License.md */
package ws.nzen.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Scanner;

import ws.nzen.model.ArgBundle;
import ws.nzen.model.JarLocation;
import ws.nzen.model.JarModel;

/** shows options via stdin */
public class CliSelection implements SelectionUi
{
	private ActionListener reactor;
	private JarModel knowsJars;

	@Override
	/** set it or @throws NullPointerException */
	public void setJarModel( JarModel toBuildUiAround )
	{
		if ( toBuildUiAround == null )
			throw new NullPointerException();
		knowsJars = toBuildUiAround;
	}

	@Override
	/** show options, get selection, send to launcher */
	public void setVisible( boolean yeahNow )
	{
		Scanner input = new Scanner( System.in );
		System.out.println( "Available jars:" );
		for ( Map.Entry<String, JarLocation> keyAndLoc : knowsJars.getLocations() ) // FIX use iterators instead
		{
			System.out.println( keyAndLoc.getKey() +" - "+ keyAndLoc.getValue().getDesc() );
		}
		System.out.print( "-- ? " );
		String jarIdChosen = input.next();
		if ( ! knowsJars.locationsHas( jarIdChosen ) )
			System.err.print( jarIdChosen +" isn't an offered selection. ignored" );
			// IMPROVE actually handle this
		System.out.println( "Available args:" );
		for ( Map.Entry<String, ArgBundle> keyAndLoc : knowsJars.getArgs() )
		{
			System.out.println( keyAndLoc.getKey() +" - "+ keyAndLoc.getValue().getDesc() );
		}
		System.out.print( "-- ? " );
		String argIdChosen = input.next();
		if ( ! knowsJars.argsHas( argIdChosen ) )
			System.err.print( argIdChosen +" isn't an offered selection. ignored" );
			// IMPROVE actually handle this

		reactor.actionPerformed(new ActionEvent( this, 6345,
				knowsJars.getComboRef( jarIdChosen, argIdChosen ) ));
	}

	@Override
	public void addActionListener( ActionListener toRespondToSelection )
	{
		reactor = toRespondToSelection;
	}

}
