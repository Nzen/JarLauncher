/** see License.md */
package ws.nzen.jarl.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

import ws.nzen.jarl.model.ArgBundle;
import ws.nzen.jarl.model.JarLocation;
import ws.nzen.jarl.model.JarModel;
import ws.nzen.jarl.model.SelfDescribes;

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
			// just use the first one
			return knowsJars.getLocations().next().getKey();
		}
		else
		{
			System.out.println( "Available jars:" );
			Map<Integer, String> viewToLocation = showOptions( knowsJars.getLocations(),
					knowsJars.numberOfLocations(), new TreeMap<>() );
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
			// just use the first one
			return knowsJars.getArgs().next().getKey();
		}
		else
		{
			System.out.println( "Available args:" );
			Map<Integer, String> viewToArgs = showOptions( new TreeMap<>(),
					knowsJars.numberOfLocations(), knowsJars.getArgs() );
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
			String argIdChosen = viewToArgs.get( userChose );
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


	// wasn't able to use private Map<> sOpt( Iterator<M.E<Str, ? extends Interface>>, ... )

	private Map<Integer, String> showOptions( Iterator<Map.Entry<String, JarLocation>> toShow,
			int howMany, Map<Integer, String> viewToModel )
	{
		if ( howMany <= 10 )
		{
			// single column
			for ( Integer ind = 0; toShow.hasNext(); ind++ )
			{
				Map.Entry<String, JarLocation> keyAndLoc = toShow.next();
				viewToModel.put( ind, keyAndLoc.getKey() );
				System.out.println( formattedIndAndDesc( ind, keyAndLoc
						.getValue().getDesc() ) );
			}
		}
		else
		{
			// two columns
			int halfway = howMany / 2;
			java.util.Queue<String> firstColumn = new java.util.ArrayDeque<>( halfway );
			String fullDesc;
			int longest = 0;
			for ( Integer ind = 0; ind < halfway; ind++ )
			{
				Map.Entry<String, JarLocation> keyAndLoc = toShow.next();
				viewToModel.put( ind, keyAndLoc.getKey() );
				fullDesc = formattedIndAndDesc( ind, keyAndLoc
						.getValue().getDesc() );
				if ( fullDesc.length() > longest )
				{
					longest = fullDesc.length();
				}
				firstColumn.add( fullDesc );
			}
			String maxWidthFlag = "%-"+ longest +"s";
			for ( Integer ind = halfway; toShow.hasNext(); ind++ )
			{
				Map.Entry<String, JarLocation> keyAndLoc = toShow.next();
				viewToModel.put( ind, keyAndLoc.getKey() );
				System.out.println( String.format( maxWidthFlag,
						firstColumn.poll() ) + formattedIndAndDesc(
								ind, keyAndLoc.getValue().getDesc() ) );
			}
		}
		return viewToModel;
	}


	private Map<Integer, String> showOptions( Map<Integer, String> viewToModel,
			int howMany, Iterator<Map.Entry<String, ArgBundle>> toShow )
	{
		if ( howMany <= 10 )
		{
			// single column
			for ( Integer ind = 0; toShow.hasNext(); ind++ )
			{
				Map.Entry<String, ArgBundle> keyAndLoc = toShow.next();
				viewToModel.put( ind, keyAndLoc.getKey() );
				System.out.println( formattedIndAndDesc( ind, keyAndLoc
						.getValue().getDesc() ) );
			}
		}
		else
		{
			// two columns
			int halfway = howMany / 2;
			java.util.Queue<String> firstColumn = new java.util.ArrayDeque<>( halfway );
			String fullDesc;
			int longest = 0;
			for ( Integer ind = 0; ind < halfway; ind++ )
			{
				Map.Entry<String, ArgBundle> keyAndLoc = toShow.next();
				viewToModel.put( ind, keyAndLoc.getKey() );
				fullDesc = formattedIndAndDesc( ind, keyAndLoc
						.getValue().getDesc() );
				if ( fullDesc.length() > longest )
				{
					longest = fullDesc.length();
				}
				firstColumn.add( fullDesc );
			}
			String maxWidthFlag = "%-"+ (longest +1) +"s";
			for ( Integer ind = halfway; toShow.hasNext(); ind++ )
			{
				Map.Entry<String, ArgBundle> keyAndLoc = toShow.next();
				viewToModel.put( ind, keyAndLoc.getKey() );
				System.out.println( String.format( maxWidthFlag,
						firstColumn.poll() ) + formattedIndAndDesc(
								ind, keyAndLoc.getValue().getDesc() ) );
			}
		}
		return viewToModel;
	}


	private String formattedIndAndDesc( int ind, String desc )
	{
		return String.format( "%02d", ind ) +" - "+ desc;
	}

	@Override
	public void addActionListener( ActionListener toRespondToSelection )
	{
		reactor = toRespondToSelection;
	}

}










