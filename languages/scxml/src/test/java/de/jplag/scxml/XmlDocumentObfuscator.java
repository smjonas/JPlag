package de.jplag.scxml;

import org.w3c.dom.Node;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

public class XmlDocumentObfuscator {

    private File inputFile;
    private File outputFile;

    public XmlDocumentObfuscator() {

    }

    public void init(File inputFile, File outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    private void writeXml(Node root) throws FileNotFoundException {
        StringWriter writer = new StringWriter();
        try {
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.transform(new DOMSource(root), new StreamResult(writer));
        } catch (TransformerException te) {
            System.out.println("failed to save XML to file");
        }

        try (PrintWriter out = new PrintWriter(outputFile)) {
            out.println(writer.toString());
        }
    }

    public void insertElement(String elementName, List<Map.Entry<String, String>> attributes) {

    }
}
