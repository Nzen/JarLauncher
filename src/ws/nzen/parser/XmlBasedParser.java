package ws.nzen.parser;

import java.io.IOException;
import java.nio.file.Path;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ws.nzen.JarLauncher;
import ws.nzen.model.JarModel;

public class XmlBasedParser implements ConfigParser
{

	private JarLauncher listener;
	private Path dataNugget;

	/**  */
	public XmlBasedParser( Path toConfig )
	{
		dataNugget = toConfig;
	}

	/**  */
	public void setCompletionListener( JarLauncher tailWaggingTheDog )
	{
		listener = tailWaggingTheDog;
	}

	/**  */
	public void setPathToConfig( Path aFile )
	{
		dataNugget = aFile;
	}

	/**  */
	public void parseConfig()
	{
		SAXParserFactory makesActualParser = SAXParserFactory.newInstance();
		try
		{
			SAXParser knowsHowToRead = makesActualParser.newSAXParser();
			knowsHowToRead.parse( dataNugget.toFile(), new XmlWorkHorse() );
		}
		catch ( ParserConfigurationException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch ( SAXException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch ( IOException e )
		{
			System.err.println( "xmp.pc couldn't read file" );
			e.printStackTrace();
		}
	}

	enum XmlJarModelState { jvm, jar, path, desc,
		argB, flag, summary, needsIo, between, outsideJloptions };

	/**  */
	class XmlWorkHorse extends DefaultHandler
	{
		private JarModel assembledInfo;
		private XmlJarModelState currently;

		public XmlWorkHorse()
		{
			assembledInfo = new JarModel();
			currently = XmlJarModelState.outsideJloptions;
		}

		@Override
		/**  */
		public void startElement(String uri, String localName, String qName, Attributes attributes)
		{
			System.out.println( "< start elem : u-"+ uri +" ln-"+ localName +" qn- "+ qName );
			for ( int ind = 0; ind < attributes.getLength(); ind++ )
			{
				System.out.println( "\tAttribute t-"+ attributes.getType( ind ) +" v-"+ attributes.getValue( ind )
				+" ln-"+ attributes.getLocalName( ind ) +"" );
			}
			switch ( currently )
			{
			case outsideJloptions :
			{
				if ( qName.equals( "jl_options" ) )
					currently = XmlJarModelState.between;
			}
			case jar :
			{
				// be path or desc
			}
			case argB :
			{
				// be flag needsio OR summary
			}
			case between :
			{
				// be jar OR argb
			}
			case jvm :
			case path :
			case desc :
			case flag :
			case summary :
			case needsIo :
			default :
			{
				System.err.print( "unexpected xml nesting, ignored" );
			}
			}
			
		}

		@Override
		/**  */
		public void characters(char[] ch, int start, int length)
		{
			String strOfVal = new String( ch, start, length );
			System.out.println( "-curr chars- s:l::"+ start +":"+ length +" |"+ strOfVal +"|" );

			switch ( currently )
			{
			case jvm :
			{
				// save to jvm
			}
			case jar :
			{
				// ignore
			}
			case path :
			{
				// save path
			}
			case desc :
			{
				// save
			}
			case argB :
			{
				// ignore
			}
			case flag :
			{
				// save
			}
			case summary :
			{
				// save
			}
			case needsIo :
			{
				// ignore
			}
			case between :
			{
				// ignore
			}
			default :
			{
				System.err.print( "unexpected xml nesting, ignored" );
			}
			}
		}

		@Override
		/**  */
		public void endElement(String uri, String localName, String qName)
		{
			System.out.println( "> end elem : u-"+ uri +" ln-"+ localName +" qn- "+ qName );

			switch ( currently )
			{
			case jvm :
			{
				// goto betwix
			}
			case jar :
			{
				// goto betwix
			}
			case path :
			{
				// goto jar
			}
			case desc :
			{
				// goto jar
			}
			case argB :
			{
				// goto betwix
			}
			case flag :
			{
				// goto argB
			}
			case summary :
			{
				// goto argB
			}
			case needsIo :
			{
				// goto argB
			}
			case between :
			{
				// be jl option
			}
			default :
			{
				System.err.print( "unexpected xml nesting, ignored" );
			}
			}
		}

		@Override
		/**  */
		public void endDocument()
		{
			listener.showOptions( assembledInfo );
		}

		// --

		public void fatalError(org.xml.sax.SAXParseException spe)
		{
			System.err.print( "fatal error  "+ spe );
			spe.printStackTrace();
		}

		public void error(org.xml.sax.SAXParseException spe)
		{
			System.err.print( "error "+ spe );
		}

		public void warning(org.xml.sax.SAXParseException spe)
		{
			System.err.print( "warning "+ spe );
		}

		public void unparsedEntityDecl(String name, String publicId, String systemId, String notationName)
		{
			System.out.println( "unparsed element : n-"+ name );
		}

		// --

		public void startPrefixMapping(String prefix, String uri)
		{
			System.out.println( "<start prefix "+ prefix +" u-"+ prefix +">" );
		}

		public void endPrefixMapping(String prefix)
		{
			System.out.println( "<end prefix "+ prefix +">" );
		}

		public void notationDecl(String name, String publicId, String systemId)
		{
			System.out.println( "<notation "+ name +"pub-"+ publicId +" sy-"+ systemId +" >" );
		}

		public void processingInstruction(String target, String data)
		{
			System.out.println( "<process t-"+ target +" d-"+ data +" >" );
		}

		public void skippedEntity(String name)
		{
			System.out.println( "<skipped "+ name +">" );
		}
	}
}































