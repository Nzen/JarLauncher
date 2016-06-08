/** see License.md */
package ws.nzen;

import java.nio.file.Path;

import ws.nzen.parser.FastArgParser;

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
			FastArgParser toUse = new FastArgParser( toConfig );
			return toUse.parseToModel();
		}
		else
			return null;
	}

}
