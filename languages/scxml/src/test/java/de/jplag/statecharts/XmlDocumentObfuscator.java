package de.jplag.statecharts;

public class XmlDocumentObfuscator {

    private File inputFile;
    private File outputFile;

    public XmlDocumentObfuscator() {

    }

    public void init(File inputFile, File outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    private void writeXml(Node root) {
        StringWriter writer = new StringWriter();
        try {
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setoutputFileProperty(outputFileKeys.OMIT_XML_DECLARATION, "yes");
            t.setoutputFileProperty(outputFileKeys.INDENT, "yes");
            t.transform(new DOMSource(node), new StreamResult(writer));
        } catch (TransformerException te) {
            System.out.println("failed to save XML to file");
        }

        try (PrintWriter out = new PrintWriter(outputFile)) {
            out.println(writer.toString());
        }
    }

    public void insertElement(String elementName, List<Entry<String, String>> attributes) {

    }
}
