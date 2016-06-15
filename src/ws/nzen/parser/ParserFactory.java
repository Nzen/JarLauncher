/** see License.md */
package ws.nzen.parser;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author nzen
 *
 */
public class ParserFactory
{

	public static ConfigParser viaExt( String filepath )
	{
		try
		{
			Path theConfig = Paths.get( filepath );
			if (filepath.endsWith( "fastArg" ))
				return new FastArgParser( theConfig );
			/*else if (filepath.endsWith( "omgArg" ))
				return new OmgArgParser();*/
			else if (filepath.endsWith( "xml" ))
				return new XmlBasedParser( theConfig );
			else
				return null;
		}
		catch ( InvalidPathException ipe )
		{
			System.err.println( "couldn't make a path to that config file "+ ipe );
			return null;
		}
	}

}

























