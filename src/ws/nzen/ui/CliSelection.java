/** see License.md */
package ws.nzen.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
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
		String jarIdChosen = getJarId( input );
		if ( jarIdChosen.isEmpty() )
			return; // already complained
		String argIdChosen = getArgBundleId( input );
		reactor.actionPerformed(new ActionEvent( this, 6345,
				knowsJars.getComboRef( jarIdChosen, argIdChosen ) ));
		// FIX this doesn;t handle a no arg setup, le sigh
	}

	private String getJarId( Scanner input )
	{
		String jarIdChosen;
		if ( knowsJars.numberOfLocations() < 1 )
		{
			System.err.print( "Config doesn't have any jar locations. Quitting" );
			return "";
		}
		else if ( knowsJars.numberOfLocations() == 1 )
		{
			return "0";
		}
		else
		{
			System.out.println( "Available jars:" );
			Iterator<Map.Entry<String, JarLocation>> keyChain = knowsJars.getLocations();
			while ( keyChain.hasNext() )
			{
				Map.Entry<String, JarLocation> keyAndLoc = keyChain.next();
				System.out.println( keyAndLoc.getKey() +" - "+ keyAndLoc.getValue().getDesc() );
			}
			System.out.print( "-- ? " );
			jarIdChosen = input.next();
			if ( ! knowsJars.locationsHas( jarIdChosen ) )
			{
				System.err.print( jarIdChosen +" isn't an offered selection. ignored" );
				// IMPROVE actually handle this
				return "";
			}
			else
			{
				return jarIdChosen;
			}
		}
	}

	private String getArgBundleId( Scanner input )
	{
		if ( knowsJars.numberOfArgBundles() < 1 )
		{
			return "";
		}
		else if ( knowsJars.numberOfArgBundles() == 1 )
		{
			return "0";
		}
		else
		{
			System.out.println( "Available args:" );
			Iterator<Map.Entry<String, ArgBundle>> keyChain = knowsJars.getArgs();
			while ( keyChain.hasNext() )
			{
				Map.Entry<String, ArgBundle> keyAndLoc = keyChain.next();
				System.out.println( keyAndLoc.getKey() +" - "+ keyAndLoc.getValue().getDesc() );
			}
			System.out.print( "-- ? " );
			String argIdChosen = input.next();
			if ( ! knowsJars.argsHas( argIdChosen ) )
			{
				System.err.print( argIdChosen +" isn't an offered selection. ignored" );
				// IMPROVE actually handle this
				return "";
			}
			else
			{
				return argIdChosen;
			}
		}
	}

	@Override
	public void addActionListener( ActionListener toRespondToSelection )
	{
		reactor = toRespondToSelection;
	}

}










