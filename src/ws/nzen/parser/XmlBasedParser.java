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
import ws.nzen.model.ArgBundle;
import ws.nzen.model.JarLocation;
import ws.nzen.model.JarModel;

public class XmlBasedParser implements ConfigParser
{

	private JarLauncher listener;
	private Path dataNugget;

	/** prep with file to read */
	public XmlBasedParser( Path toConfig )
	{
		dataNugget = toConfig;
	}

	public void setCompletionListener( JarLauncher tailWaggingTheDog )
	{
		listener = tailWaggingTheDog;
	}

	public void setPathToConfig( Path aFile )
	{
		dataNugget = aFile;
	}

	/** event driven parse ; should call listener when finished */
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

	/** Builds a JarModel from xml with the expected dtd via dsm */
	class XmlWorkHorse extends DefaultHandler
	{
		private JarModel assembledInfo;
		private ArgBundle argSink;
		private JarLocation pathSink;
		private XmlJarModelState currently;

		public XmlWorkHorse()
		{
			assembledInfo = new JarModel();
			currently = XmlJarModelState.outsideJloptions;
		}

		@Override
		/** transition state, maybe prep element to receive data */
		public void startElement(String uri, String localName, String qName, Attributes attributes)
		{
			switch ( currently )
			{
			case outsideJloptions :
			{
				if ( qName.equals( "jl_options" ) )
					currently = XmlJarModelState.between;
				break;
			}
			case between :
			{
				// be jar OR argb
				if ( qName.equals( "location" ) )
				{
					if ( attributes.getLength() > 0
						&& attributes.getLocalName( 0 ).equals( "type" ) )
					{
						if ( attributes.getValue( 0 ).equals( "jvm" ) )
						{
							currently = XmlJarModelState.jvm;
						}
						else if ( attributes.getValue( 0 ).equals( "jar" ) )
						{
							pathSink = new JarLocation();
							currently = XmlJarModelState.jar;
						}
						else
						{
							System.err.println( "unexpected location type" );
						}
					}
					else if ( attributes.getLength() > 0 )
					{
						System.err.println( "unexpected location attribute :"
								+ "\n\tln-"+ attributes.getLocalName( 0 )
								+" v-"+ attributes.getValue( 0 ) );
					}
					else	
					{
						System.err.println( "untyped location tag is ambiguous" );
					}
				}
				else if ( qName.equals( "argBundle" ) )
				{
					argSink = new ArgBundle();
					currently = XmlJarModelState.argB;
				}
				else
				{
					System.err.println( "unexpected main tag : qn-"+ qName );
				}
				break;
			}
			case jar :
			{
				// be path or desc
				if ( qName.equals( "path" ) )
				{
					currently = XmlJarModelState.path;
				}
				else if ( qName.equals( "desc" ) )
				{
					currently = XmlJarModelState.desc;
				}
				else
				{
					System.err.println( "unexpected sub location tag : qn-"+ qName );
				}
				break;
			}
			case argB :
			{
				// be flag needsio OR summary
				if ( qName.equals( "flag" ) )
				{
					currently = XmlJarModelState.flag;
				}
				else if ( qName.equals( "summary" ) )
				{
					currently = XmlJarModelState.summary;
				}
				else if ( qName.equals( "needsIo" ) )
				{
					argSink.setNeedsIo( true );
					currently = XmlJarModelState.needsIo;
				}
				else
				{
					System.err.println( "unexpected sub argBundle tag : qn-"+ qName );
				}
				break;
			}
			case jvm :
			case path :
			case desc :
			case flag :
			case summary :
			case needsIo :
			default :
			{
				System.err.println( "unexpected xml nesting qn-"+ qName +", ignored" );
			}
			}
			
		}

		@Override
		/** save data */
		public void characters(char[] ch, int start, int length)
		{
			String strOfVal = new String( ch, start, length );
			switch ( currently )
			{
			case jvm :
			{
				assembledInfo.setJvmLocation( strOfVal );
				break;
			}
			case path :
			{
				pathSink.setLocation( strOfVal );
				break;
			}
			case desc :
			{
				pathSink.setDesc( strOfVal );
				break;
			}
			case flag :
			{
				argSink.appendToFlags( strOfVal );
				break;
			}
			case summary :
			{
				argSink.setDesc( strOfVal );
				break;
			}
			case jar :
			case argB :
			case needsIo :
			case between :
			default :
			{
				if ( ! strOfVal.trim().isEmpty() )
					System.err.print( "unexpected text "+ strOfVal +", ignored" );
			}
			}
		}

		@Override
		/** transition state ; maybe post accumulated data to the model */
		public void endElement(String uri, String localName, String qName)
		{
			switch ( currently )
			{
			case jvm :
			{
				if ( qName.equals( "location" ) )
				{
					currently = XmlJarModelState.between;
				}
				else
				{
					System.err.println( "unexpected end tag : qn-"+ qName );
				}
				break;
			}
			case jar :
			{
				if ( qName.equals( "location" ) )
				{
					assembledInfo.addJarLocation( new JarLocation(pathSink) );
					pathSink = null;
					currently = XmlJarModelState.between;
				}
				else
				{
					System.err.println( "unexpected end tag : qn-"+ qName );
				}
				break;
			}
			case path :
			{
				if ( qName.equals( "path" ) )
				{
					currently = XmlJarModelState.jar;
				}
				else
				{
					System.err.println( "unexpected end tag : qn-"+ qName );
				}
				break;
			}
			case desc :
			{
				if ( qName.equals( "desc" ) )
				{
					currently = XmlJarModelState.jar;
				}
				else
				{
					System.err.println( "unexpected end tag : qn-"+ qName );
				}
				break;
			}
			case argB :
			{
				if ( qName.equals( "argBundle" ) )
				{
					assembledInfo.addArgBundle( new ArgBundle(argSink) );
					argSink = null;
					currently = XmlJarModelState.between;
				}
				else
				{
					System.err.println( "unexpected end tag : qn-"+ qName );
				}
				break;
			}
			case flag :
			{
				if ( qName.equals( "flag" ) )
				{
					currently = XmlJarModelState.argB;
				}
				else
				{
					System.err.println( "unexpected end tag : qn-"+ qName );
				}
				break;
			}
			case summary :
			{
				if ( qName.equals( "summary" ) )
				{
					currently = XmlJarModelState.argB;
				}
				else
				{
					System.err.println( "unexpected end tag : qn-"+ qName );
				}
				break;
			}
			case needsIo :
			{
				if ( qName.equals( "needsIo" ) )
				{
					currently = XmlJarModelState.argB;
				}
				else
				{
					System.err.println( "unexpected end tag : qn-"+ qName );
				}
				break;
			}
			case between :
			{
				if ( qName.equals( "jl_options" ) )
				{
					currently = XmlJarModelState.outsideJloptions;
				}
				else
				{
					System.err.println( "unexpected end tag : qn-"+ qName );
				}
				break;
			}
			default :
			{
				System.err.println( "unexpected xml nesting, ignored" );
			}
			}
		}

		@Override
		/** tell the listener that we're done */
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































