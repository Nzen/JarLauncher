/** see License.md */
package ws.nzen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author nzen
 *
 */
public class CliSelection implements SelectionUi
{
	private ActionListener reactor;

	@Override
	public void setJarModel( JarModel toBuildUiAround )
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setVisible( boolean yeahNow )
	{
		System.out.println( "which jar?" );
		System.out.println( "which args?" );
		// FIX move next bit elsewhere
		reactor.actionPerformed(new ActionEvent( this, 6345, "SplainTime.jar" ));
	}

	@Override
	public void addActionListener( ActionListener toRespondToSelection )
	{
		reactor = toRespondToSelection;
	}

}
