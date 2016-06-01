/** see License.md */
package ws.nzen;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ArgumentStore
{

	public ArgumentStore( String aFileWithArgsPresumably )
	{
		
	}

	public JarModel getJarOptions()
	{
		return new ConfigParser().parseFrom( Paths.get("") );
	}

	public Path getJvmLocation()
	{
		return Paths.get( "/usr/bin/java" );
		// throws InvalidPathException - if the path string cannot be converted to a Path
	}

	public Path jarCorrespondingTo( String fromSelectionUi )
	{
		return Paths.get( "SplainTime.jar" ).toAbsolutePath();
	}

	public String argsCorrespondingTo( String fromSelectionUi )
	{
		return "";
	}

}





















