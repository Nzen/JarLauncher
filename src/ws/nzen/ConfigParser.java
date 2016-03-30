/** see License.md */
package ws.nzen;

import java.nio.file.Path;

/**
 * @author nzen
 *
 */
public class ConfigParser
{

	public JarModel parseFrom( Path toConfig )
	{
		if ( true )
		{
			OmgArgParser toUse = new OmgArgParser();
			return toUse.parseToModel();
		}
		else
			return null;
	}

}
