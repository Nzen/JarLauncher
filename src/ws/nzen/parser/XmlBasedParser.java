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
		private ArgBundle argSink;
		private JarLocation pathSink;
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
							System.out.println( "goto jvm" ); // 4TESTS
							currently = XmlJarModelState.jvm;
						}
						else if ( attributes.getValue( 0 ).equals( "jar" ) )
						{
							pathSink = new JarLocation();
							System.out.println( "goto jar" ); // 4TESTS
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
					System.out.println( "goto argB" ); // 4TESTS
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
					System.out.println( "goto path" ); // 4TESTS
					currently = XmlJarModelState.path;
				}
				else if ( qName.equals( "desc" ) )
				{
					System.out.println( "goto desc" ); // 4TESTS
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
					System.out.println( "goto flag" ); // 4TESTS
					currently = XmlJarModelState.flag;
				}
				else if ( qName.equals( "summary" ) )
				{
					System.out.println( "goto summary" ); // 4TESTS
					currently = XmlJarModelState.summary;
				}
				else if ( qName.equals( "needsIo" ) )
				{
					argSink.setNeedsIo( true );
					System.out.println( "goto needsIo" ); // 4TESTS
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
		/**  */
		public void characters(char[] ch, int start, int length)
		{
			String strOfVal = new String( ch, start, length );
			switch ( currently )
			{
			case jvm :
			{
				System.out.println( "save in jvm : "+ strOfVal ); // 4TESTS
				assembledInfo.setJvmLocation( strOfVal );
				break;
			}
			case path :
			{
				System.out.println( "save in path : "+ strOfVal ); // 4TESTS
				pathSink.setLocation( strOfVal );
				break;
			}
			case desc :
			{
				System.out.println( "save in desc : "+ strOfVal ); // 4TESTS
				pathSink.setDesc( strOfVal );
				break;
			}
			case flag :
			{
				System.out.println( "save in flag : "+ strOfVal ); // 4TESTS
				argSink.appendToFlags( strOfVal );
				break;
			}
			case summary :
			{
				System.out.println( "save in summary : "+ strOfVal ); // 4TESTS
				argSink.setDesc( strOfVal );
				break;
			}
			case jar :
			case argB :
			case needsIo :
			case between :
			default :
			{
				// ignore white space
				// System.err.print( "unexpected xml nesting, ignored" );
			}
			}
		}

		@Override
		/**  */
		public void endElement(String uri, String localName, String qName)
		{
			switch ( currently )
			{
			case jvm :
			{
				// goto betwix
				if ( qName.equals( "location" ) )
				{
					System.out.println( "return between" ); // 4TESTS
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
				// goto betwix
				if ( qName.equals( "location" ) )
				{
					assembledInfo.addJarLocation( new JarLocation(pathSink) );
					pathSink = null;
					System.out.println( "return between" ); // 4TESTS
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
					System.out.println( "return jar" ); // 4TESTS
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
					System.out.println( "return jar" ); // 4TESTS
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
					System.out.println( "return between" ); // 4TESTS
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
					System.out.println( "return argB" ); // 4TESTS
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
				System.out.println( "return argB" ); // 4TESTS
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
					System.out.println( "return argB" ); // 4TESTS
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
				// be jl option
				if ( qName.equals( "jl_options" ) )
				{
					System.out.println( "return outsideJloptions" ); // 4TESTS
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































