/** see License.md */
package ws.nzen.jarl.model;

import java.util.Iterator;
import java.util.List;

/**
 * @author nzen
 * Saves information related to the jar location
 */
public class JarLocation
{

	private String location;
	private String desc;
	private List<String> tuners;

	public JarLocation()
	{
		tuners = new java.util.ArrayList<>(2);
	}

	public JarLocation( String where )
	{
		super();
		location = where;
		desc = "";
	}

	public JarLocation( String where, String asWhat )
	{
		super();
		location = where;
		desc = asWhat;
	}

	/** deep clone */
	public JarLocation( JarLocation toClone )
	{
		location = new String( toClone.getLocation() );
		desc = new String( toClone.getDesc() );
		tuners = new java.util.ArrayList<>( toClone.numberOfTuners() );
		Iterator<String> cloneeFlags = toClone.getTunerIterator();
		while ( cloneeFlags.hasNext() )
		{
			tuners.add( new String(cloneeFlags.next()) );
		}
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation( String location )
	{
		this.location = location;
	}

	/** desc or location */
	public String getDesc()
	{
		String tuneNotes = " ";
		for ( String knob : tuners )
			tuneNotes += knob;
		if ( desc.isEmpty() )
			return location + tuneNotes;
		else
			return desc + tuneNotes;
	}

	public void setDesc( String desc )
	{
		this.desc = desc;
	}

	public void appendTuner( String jvmKnob )
	{
		tuners.add(jvmKnob);
	}

	public int numberOfTuners()
	{
		return tuners.size();
	}

	public List<String> getTuners() {
		return tuners;
	}

	public Iterator<String> getTunerIterator()
	{
		return tuners.iterator();
	}

}































