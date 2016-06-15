/** see License.md */

package ws.nzen.parser;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import ws.nzen.JarModel;

/**
 * @author nzen
 *
 */
public class FastArgParser
{
	private Path fastArgFile;
	private JarModel aggregate;

	public FastArgParser( Path toUse )
	{
		fastArgFile = toUse;
	}

	public JarModel parseToModel()
	{
		List<String> lines = getFileLines();
		String jvm = "";
		List<String> jars = new LinkedList<String>();
		List<String> args = new LinkedList<String>();
		final int tagLen = "<_>".length(); 
		for ( String temp : lines )
		{
			if ( temp.startsWith( "<v>" ) )
			{
				jvm = temp.substring( tagLen );
			}
			else if ( temp.startsWith( "<j>" ) )
			{
				jars.add( temp.substring( tagLen ) );
			}
			else if ( temp.startsWith( "<a>" ) )
			{
				args.add( temp.substring( tagLen ) );
			}
			// else ignore
		}
		aggregate = new JarModel( jvm, jars, args );
		return aggregate;
	}

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









