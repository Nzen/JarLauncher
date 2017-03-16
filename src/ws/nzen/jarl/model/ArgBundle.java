/** see License.md */
package ws.nzen.jarl.model;

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

	/** basic init */
	public ArgBundle()
	{
		flags = new java.util.LinkedList<String>();
		needsIo = false;
		desc = "";
	}

	/** deep clone */
	public ArgBundle( ArgBundle toClone )
	{
		flags = new java.util.ArrayList<String>( toClone.numberOfFlags() );
		for ( String flag : toClone.getFlags() )
		{
			flags.add( new String( flag ) );
		}
		needsIo = toClone.needsIo;
		desc = new String( toClone.getDesc() );
	}

	public List<String> getFlags()
	{
		return flags;
	}

	public boolean isNeedsIo()
	{
		return needsIo;
	}

	/**  */
	public String getDesc()
	{
		if ( desc.isEmpty() )
		{
			String fakeDesc = "";
			for ( String oneArg : flags )
			{
				fakeDesc += " "+ oneArg;
			}
			return fakeDesc;
		}
		else
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

	public int numberOfFlags()
	{
		return flags.size();
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

























