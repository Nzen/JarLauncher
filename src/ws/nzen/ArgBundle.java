/** see License.md */
package ws.nzen;

import java.util.List;

/**
 * @author nzen
 * 
 */
public class ArgBundle
{

	private List<String> flags;
	private boolean needsIo;
	private String desc;

	public ArgBundle()
	{
		flags = new java.util.LinkedList<String>();
		needsIo = false;
		desc = "";
	}

	public List<String> getFlags()
	{
		return flags;
	}

	public boolean isNeedsIo()
	{
		return needsIo;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setFlags( List<String> flags )
	{
		this.flags = flags;
	}

	public void appendToFlags( String another )
	{
		flags.add( another );
	}

	public void setNeedsIo( boolean needsIo )
	{
		this.needsIo = needsIo;
	}

	public void setDesc( String desc )
	{
		this.desc = desc;
	}

}

















































