package de.jplag.statecharts.parser;

import de.jplag.statecharts.parser.util.NodeUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class XmlParser {

    private final static Set<String> ALLOWED_ELEMENTS = Set.of(
            "transition", "state", "initial", "parallel",
            "onentry", "onexit", "raise", "if", "elseif",
            "else", "for", "log", "assign", "cancel", "script", "send"
    );
    private final DocumentBuilder builder;

    public XmlParser() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
    }

    public List<Node> parse(File file) throws IOException, SAXException {
        Document document = builder.parse(file);
        Element root = document.getDocumentElement();
        return NodeUtil.getChildNodes(root, ALLOWED_ELEMENTS);
    }
}
