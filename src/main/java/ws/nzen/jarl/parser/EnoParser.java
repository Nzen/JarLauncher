/* see ../../../../../LICENSE for release details */
package ws.nzen.jarl.parser;

import java.io.IOException;
import java.nio.file.Path;

import ws.nzen.format.eno.EnoElement;
import ws.nzen.format.eno.Eno;
import ws.nzen.format.eno.FieldSet;
import ws.nzen.format.eno.Section;
import ws.nzen.format.eno.Value;
import ws.nzen.jarl.JarLauncher;
import ws.nzen.jarl.model.ArgBundle;
import ws.nzen.jarl.model.JarLocation;
import ws.nzen.jarl.model.JarModel;

/**  */
public class EnoParser implements ConfigParser
{
	private JarLauncher listener;
	private Path original;

	public EnoParser( Path toConfig )
	{
		original = toConfig;
	}

	/* (non-Javadoc)
	 * @see ws.nzen.jarl.parser.ConfigParser#setCompletionListener(ws.nzen.jarl.JarLauncher)
	 */
	@Override
	public void setCompletionListener( JarLauncher leSigh )
	{
		listener = leSigh;
	}


	/* (non-Javadoc)
	 * @see ws.nzen.jarl.parser.ConfigParser#setPathToConfig(java.nio.file.Path)
	 */
	@Override
	public void setPathToConfig( Path config )
	{
		original = config;
	}


	/* (non-Javadoc)
	 * @see ws.nzen.jarl.parser.ConfigParser#parseConfig()
	 */
	@Override
	public void parseConfig()
	{
		/*
		So, because of how saxParser works and my confidence at the time,
		XmlBasedParser saves the built model and stuff into JarLauncher.
		As I want to get this tonight, I'll forbear refactoring, maybe. 
		*/
		Section enoDoc = null;
		try
		{
			enoDoc = new Eno().deserialize( original );
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			System.exit( 1 );
		}
		JarModel assembledInfo = new JarModel();
		assembledInfo = withJre( enoDoc, assembledInfo );
		assembledInfo = withJars( enoDoc, assembledInfo );
		assembledInfo = withArgs( enoDoc, assembledInfo );
		listener.showOptions( assembledInfo );
	}


	private JarModel withJre( Section enoDoc, JarModel assembledInfo )
	{
		Section locations = enoDoc.section( "Locations" );
		Section oneJvm = locations.section( "Jvms" );
		for ( FieldSet whereIsJvm : oneJvm.fieldSets( "location" ) )
		{
			if ( ! whereIsJvm.entry( "type" ).equals( "jvm" ) )
				// NOTE malformed config
				continue;
			assembledInfo.setJvmLocation(
					whereIsJvm.entry( "path" )
						.requiredStringValue() );
			// NOTE only using one because of current jarlauncher limitation
			break;
		}
		return assembledInfo;
	}


	private JarModel withJars( Section enoDoc, JarModel assembledInfo )
	{
		Section locations = enoDoc.section( "Locations" );
		Section jars = locations.section( "Jars" );
		// NOTE clumsy until I a
		for ( FieldSet location : jars.fieldSets() )
		{
			if ( ! location.entry( "type" ).requiredStringValue()
					.equals( "jar" ) )
				// NOTE malformed config
				continue;
			JarLocation currLocation = new JarLocation(
					location.entry( "path" ).requiredStringValue(),
					location.entry( "desc" ).optionalStringValue() );
			assembledInfo.addJarLocation( currLocation );
		}
		return assembledInfo;
	}


	private JarModel withArgs( Section enoDoc, JarModel assembledInfo )
	{
		Section allArgs = enoDoc.section( "Arguments" );
		for ( Section bundle : allArgs.sections() )
		{
			ArgBundle currBundle = new ArgBundle();
			Value fullDesc = (Value)bundle.field( "summary" );
			String desc = ( fullDesc.optionalStringValue() == null )
					? "" : fullDesc.optionalStringValue();
			currBundle.setDesc( desc );
			currBundle.setNeedsIo( bundle.field( "needsIo" ) != null );
			for ( String arg : bundle.list( "args" ).optionalStringValues() )
			{
				currBundle.appendToFlags( arg );
			}
			assembledInfo.addArgBundle( currBundle );
		}
		return assembledInfo;
	}

}











































































