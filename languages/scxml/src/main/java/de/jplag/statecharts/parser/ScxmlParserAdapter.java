package de.jplag.statecharts.parser;

import de.jplag.AbstractParser;
import de.jplag.ParsingException;
import de.jplag.Token;
import de.jplag.emf.util.MetamodelTreeView;
import de.jplag.statecharts.parser.model.Statechart;
import de.jplag.statecharts.util.AbstractStatechartVisitor;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Parser adapter for SCXML statecharts.
 * @author Jonas Strittmatter
 */
public class ScxmlParserAdapter extends AbstractParser {
    protected List<Token> tokens;
    protected File currentFile;
    protected MetamodelTreeView treeView;
    protected AbstractStatechartVisitor visitor;

    /**
     * Creates the parser.
     */
    public ScxmlParserAdapter() {

    }

    /**
     * Parses all tokens from a set of files.
     * @param files the set of files.
     * @return the list of parsed tokens.
     */
    public List<Token> parse(Set<File> files) throws ParsingException {
        tokens = new ArrayList<>();
        for (File file : files) {
            parseModelFile(file);
        }
        return tokens;
    }

    /**
     * Loads a statechart from a file and parses it.
     * @param file is the statechart file.
     */
    protected void parseModelFile(File file) throws ParsingException {
        currentFile = file;
        Statechart statechart;
        try {
            statechart = new ScxmlParser().parse(file);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new ParsingException(file, "failed to parse statechart");
        }

        if (statechart != null) {

        }

//        if (model == null) {
//            throw new ParsingException(file, "failed to load model");
//        } else {
//            for (EObject root : model) {
//                visitor = createMetamodelVisitor();
//                visitor.visit(root);
//            }
//            tokens.add(Token.fileEnd(currentFile));
//        }
    }

    /**
     * Extension point for subclasses to employ different token generators.
     * @return a token generating statechart visitor.
     */
    protected AbstractStatechartVisitor createStatechartVisitor() {
        return new SimpleStatechartTokenGenerator(this);
    }
}
