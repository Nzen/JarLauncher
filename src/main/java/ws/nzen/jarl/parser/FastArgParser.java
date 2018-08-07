/** see License.md */

package ws.nzen.jarl.parser;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import ws.nzen.jarl.JarLauncher;
import ws.nzen.jarl.model.ArgBundle;
import ws.nzen.jarl.model.JarLocation;
import ws.nzen.jarl.model.JarModel;

/**
 * @author nzen
 *
 */
public class FastArgParser implements ConfigParser
{
	private Path fastArgFile;
	private JarModel aggregate;
	private JarLauncher theActualRoot;

	public FastArgParser( Path toUse )
	{
		fastArgFile = toUse;
		aggregate = new JarModel();
	}

	/**  */
	public void setCompletionListener( JarLauncher tailWaggingTheDog )
	{
		theActualRoot = tailWaggingTheDog;
	}

	/**  */
	public void setPathToConfig( Path aFile )
	{
		fastArgFile = aFile;
	}

	/**  */
	public void parseConfig()
	{
		theActualRoot.showOptions( parseToModel() );
	}

	/**  */
	public JarModel parseToModel()
	{
		String jvm = "";
		final int tagLen = "<_>".length(); 
		for ( String temp : getFileLines() )
		{
			if ( temp.startsWith( "<v>" ) )
			{
				jvm = temp.substring( tagLen );
			}
			else if ( temp.startsWith( "<j>" ) )
			{
				aggregate.addJarLocation( new JarLocation(temp.substring( tagLen )) );
			}
			else if ( temp.startsWith( "<a>" ) )
			{
				ArgBundle separatedArgs = new ArgBundle();
				temp = temp.substring( tagLen );
				// I've needed to be opinionated to salvage this data format
				if ( temp.contains( "-debug" ) )
					separatedArgs.isNeedsIo();
				for ( String flag : temp.split( " " ) )
					separatedArgs.appendToFlags( flag );
				aggregate.addArgBundle( separatedArgs );
			}
			// else ignore
		}
		if ( jvm.isEmpty() )
		{
			System.err.println( "fastArgs file didn't have a jvm location, quitting" );
			System.exit( 1 );
			return null; // unreachable
		}
		else
		{
			return aggregate;
		}
	}

	/**  */
	private List<String> getFileLines()
	{
		List<String> fileText = new LinkedList<String>();
		try
		{
			if ( Files.exists( fastArgFile ) )
			{
				fileText = Files.readAllLines(
						fastArgFile, Charset.defaultCharset() );
			}
		}
		catch ( IOException ioe )
		{
			System.err.println( "LF.rsf() had some I/O problem."
					+ " there's like five options\n " + ioe.toString() );
		}
		return fileText;
	}

}









