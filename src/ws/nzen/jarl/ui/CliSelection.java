/** see License.md */
package ws.nzen.jarl.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import ws.nzen.jarl.model.ArgBundle;
import ws.nzen.jarl.model.JarLocation;
import ws.nzen.jarl.model.JarModel;

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
			Map<Integer, String> viewToLocation = new HashMap<>(
					knowsJars.numberOfLocations() );
			Iterator<Map.Entry<String, JarLocation>> keyChain = knowsJars.getLocations();
			for ( Integer ind = 0; keyChain.hasNext(); ind++ )
			{
				Map.Entry<String, JarLocation> keyAndLoc = keyChain.next();
				viewToLocation.put( ind, keyAndLoc.getKey() );
				System.out.println( String.format( "%02d", ind )
						+" - "+ keyAndLoc.getValue().getDesc() );
			}
			System.out.print( "-- ? " );
			Integer userChose = null;
			String literalInput = input.next();
			try
			{
				userChose = Integer.parseInt( literalInput );
			}
			catch ( NumberFormatException nfe )
			{
				System.err.println( "sorry, you get to restart: "
						+ literalInput +" is not a number" );
				System.exit( 0 );
			}
			String jarIdChosen = viewToLocation.get( userChose );
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
			Map<Integer, String> viewToArg = new HashMap<>();
			System.out.println( "Available args:" );
			Iterator<Map.Entry<String, ArgBundle>> keyChain = knowsJars.getArgs();

			for ( Integer ind = 0; keyChain.hasNext(); ind++ )
			{
				Map.Entry<String, ArgBundle> keyAndLoc = keyChain.next();
				viewToArg.put( ind, keyAndLoc.getKey() );
				System.out.println( String.format( "%02d", ind )
						+" - "+ keyAndLoc.getValue().getDesc() );
			}
			System.out.print( "-- ? " );
			Integer userChose = null;
			String literalInput = input.next();
			try
			{
				userChose = Integer.parseInt( literalInput );
			}
			catch ( NumberFormatException nfe )
			{
				System.err.println( "sorry, you get to restart: "
						+ literalInput +" is not a number" );
				System.exit( 0 );
			}
			String argIdChosen = viewToArg.get( userChose );
			if ( ! knowsJars.argsHas( argIdChosen ) )
			{
				System.err.print( argIdChosen +" isn't an offered selection. ignored" );
				// IMPROVE actually handle this; perhaps by offering again
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










