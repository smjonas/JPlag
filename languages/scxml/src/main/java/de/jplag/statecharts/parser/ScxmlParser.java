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
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * An SCXML parser implementation based on a SAX ("Simple API for XML") parser.
 *
 * @author Jonas Strittmatter
 */
public class ScxmlParser extends DefaultHandler {

    enum ScxmlElement {
        ROOT("scxml"),
        STATE("state"),
        PARALLEL("parallel"),
        TRANSITION("transition");

        public String getElementName() {
            return elementName;
        }

        private final String elementName;

        ScxmlElement(String elementName) {
            this.elementName = elementName;
        }
    }

    private Statechart statechart;
    private final SAXParser saxParser;
    private static final List<String> scxmlElementNames = Stream.of(ScxmlElement.values())
            .map(ScxmlElement::getElementName)
            .toList();

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
        switch (qName) {
            case "scxml":
                statechart = new Statechart()
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
    }
}
