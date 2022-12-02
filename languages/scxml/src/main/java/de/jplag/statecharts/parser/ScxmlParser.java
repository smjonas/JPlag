package de.jplag.statecharts.parser;

import de.jplag.statecharts.parser.model.Statechart;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * An SCXML parser implementation based on a SAX ("Simple API for XML") parser.
 * @author Jonas Strittmatter
 */
public class ScxmlParser extends DefaultHandler {

    private Statechart statechart;
    private final SAXParser saxParser;

    public ScxmlParser() throws ParserConfigurationException, SAXException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        this.saxParser = factory.newSAXParser();
    }

    public Statechart parse(File file) throws IOException, SAXException {
        saxParser.parse(file, this);
        return statechart;
    }

    @Override
    public void startDocument() throws SAXException {
        statechart = new Statechart();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        System.out.println("Saw " + uri + ", localName =" + localName);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        System.out.println("Saw chars " + Arrays.toString(ch));
    }
}
