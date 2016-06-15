/** see License.md */
package ws.nzen.ui;

import java.awt.event.ActionListener;

import ws.nzen.model.JarModel;

public interface SelectionUi
{
	public void setJarModel( JarModel toBuildUiAround );

	public void setVisible( boolean yeahNow );

	public void addActionListener( ActionListener toRespondToSelection );
}
