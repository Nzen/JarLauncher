/** see License.md */
package ws.nzen;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ArgumentStore
{
	Path toSerialized;

	public ArgumentStore( String aFileWithArgsPresumably )
	{
		toSerialized = Paths.get( aFileWithArgsPresumably );
		// throws InvalidPathException - if the path string cannot be converted to a Path
	}

	public JarModel getJarOptions()
	{
		// return new ConfigParser().parseFrom( toSerialized ); // UNREADY
		List<String> replace = new java.util.ArrayList<String>(2);
		replace.add( "SplainTime.jar" );
		replace.add( "Applpi.jar" );
		List<String> unready = new java.util.ArrayList<String>(2);
		unready.add( "-h" );
		unready.add( "" );
		return new JarModel( replace, unready );
	}

	public Path getJvmLocation()
	{
		return Paths.get( "/usr/bin/java" );
	}

}





















