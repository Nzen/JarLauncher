package ws.nzen.parser;

import java.io.IOException;
import java.nio.file.Path;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ws.nzen.JarLauncher;

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

	/**  */
	class XmlWorkHorse extends DefaultHandler
	{

		/**  */
		public void endDocument()
		{
			listener.showOptions( null );
		}

		/*
		 	characters(char[] ch, int start, int length)
Receive notification of character data inside an element.
void 	endDocument()
Receive notification of the end of the document.
void 	endElement(String uri, String localName, String qName)
Receive notification of the end of an element.
void 	endPrefixMapping(String prefix)
Receive notification of the end of a Namespace mapping.
void 	error(SAXParseException e)
Receive notification of a recoverable parser error.
void 	fatalError(SAXParseException e)
Report a fatal XML parsing error.
void 	ignorableWhitespace(char[] ch, int start, int length)
Receive notification of ignorable whitespace in element content.
void 	notationDecl(String name, String publicId, String systemId)
Receive notification of a notation declaration.
void 	processingInstruction(String target, String data)
Receive notification of a processing instruction.
InputSource 	resolveEntity(String publicId, String systemId)
Resolve an external entity.
void 	setDocumentLocator(Locator locator)
Receive a Locator object for document events.
void 	skippedEntity(String name)
Receive notification of a skipped entity.
void 	startDocument()
Receive notification of the beginning of the document.
void 	startElement(String uri, String localName, String qName, Attributes attributes)
Receive notification of the start of an element.
void 	startPrefixMapping(String prefix, String uri)
Receive notification of the start of a Namespace mapping.
void 	unparsedEntityDecl(String name, String publicId, String systemId, String notationName)
Receive notification of an unparsed entity declaration.
void 	warning(SAXParseException e)
Receive notification of a parser warning.
		*/
	}
}































