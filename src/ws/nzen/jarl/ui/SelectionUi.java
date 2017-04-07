/** see License.md */

package ws.nzen.jarl.ui;

import java.awt.event.ActionListener;

import ws.nzen.jarl.model.JarModel;

/**  */
public interface SelectionUi
{
	
	/** what you will show */
	public void setJarModel( JarModel toBuildUiAround );

	/** time to take input */
	public void setVisible( boolean yeahNow );

	/** callbackee that will process the input */
	public void addActionListener( ActionListener toRespondToSelection );
}
