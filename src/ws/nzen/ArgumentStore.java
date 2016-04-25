/** see License.md */
package ws.nzen;

import java.nio.file.Paths;

public class ArgumentStore
{

	public ArgumentStore( String toMakePathFromAndValidate )
	{
		
	}

	public JarModel getJarOptions()
	{
		return new ConfigParser().parseFrom( Paths.get("") );
	}

}
