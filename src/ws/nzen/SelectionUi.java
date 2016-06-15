/** see License.md */
package ws.nzen;

import java.awt.event.ActionListener;

public interface SelectionUi
{
	public void setJarModel( JarModel toBuildUiAround );

	public void setVisible( boolean yeahNow );

	public void addActionListener( ActionListener toRespondToSelection );
}
