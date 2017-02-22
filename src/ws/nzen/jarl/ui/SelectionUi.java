/** see License.md */

package ws.nzen.jarl.ui;

import java.awt.event.ActionListener;

import ws.nzen.jarl.model.JarModel;

/**  */
public interface SelectionUi
{
	
	/**  */
	public void setJarModel( JarModel toBuildUiAround );

	/**  */
	public void setVisible( boolean yeahNow );

	/**  */
	public void addActionListener( ActionListener toRespondToSelection );
}
