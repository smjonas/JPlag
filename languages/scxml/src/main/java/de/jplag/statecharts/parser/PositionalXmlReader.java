package de.jplag.statecharts.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

public class PositionalXmlReader {

    public static final String LINE_NUMBER_KEY = "lineNumber";
    Document document;

    public Document readXML(File file) throws ParserConfigurationException, SAXException, IOException {
        SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        document = builder.newDocument();
        parser.parse(file, createHandler());
        return document;
    }

    private DefaultHandler createHandler() {
        Stack<Element> elementStack = new Stack<>();
        StringBuilder textBuffer = new StringBuilder();
        return new DefaultHandler() {
            private Locator locator;

            @Override
            public void setDocumentLocator(Locator locator) {
                this.locator = locator;
            }

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) {
                addTextIfNeeded();
                Element element = document.createElement(qName);
                for (int i = 0; i < attributes.getLength(); i++) {
                    element.setAttribute(attributes.getQName(i), attributes.getValue(i));
                }
                element.setUserData(LINE_NUMBER_KEY, String.valueOf(this.locator.getLineNumber()), null);
                elementStack.push(element);
            }

            @Override
            public void endElement(String uri, String localName, String qName) {
                addTextIfNeeded();
                Element closedElement = elementStack.pop();
                // Check if this is the root element
                if (elementStack.isEmpty()) {
                    document.appendChild(closedElement);
                } else {
                    Element parent = elementStack.peek();
                    parent.appendChild(closedElement);
                }
            }

            @Override
            public void characters(char[] ch, int start, int length) {
                textBuffer.append(ch, start, length);
            }

            // Outputs text accumulated under the current node
            private void addTextIfNeeded() {
                if (textBuffer.length() > 0) {
                    Element element = elementStack.peek();
                    Node textNode = document.createTextNode(textBuffer.toString());
                    element.appendChild(textNode);
                    textBuffer.delete(0, textBuffer.length());
                }
            }
        };
    }
}
