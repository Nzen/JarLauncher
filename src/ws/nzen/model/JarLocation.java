/** see License.md */
package ws.nzen.model;

/**
 * @author nzen
 * Saves information related to the jar location
 */
public class JarLocation
{

	private String location;
	private String desc;

	public JarLocation()
	{
		
	}

	public JarLocation( String where )
	{
		location = where;
		desc = "";
	}

	public JarLocation( String where, String asWhat )
	{
		location = where;
		desc = asWhat;
	}

	/** deep clone */
	public JarLocation( JarLocation toClone )
	{
		location = new String( toClone.getLocation() );
		desc = new String( toClone.getDesc() );
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation( String location )
	{
		this.location = location;
	}

	/**  */
	public String getDesc()
	{
		if ( desc.isEmpty() )
			return location;
		else
			return desc;
	}

	public void setDesc( String desc )
	{
		this.desc = desc;
	}

}
