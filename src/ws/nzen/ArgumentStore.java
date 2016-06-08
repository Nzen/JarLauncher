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
		return new ConfigParser().parseFrom( toSerialized );
	}

	public Path getJvmLocation()
	{
		return Paths.get( "/usr/bin/java" );
	}

}





















