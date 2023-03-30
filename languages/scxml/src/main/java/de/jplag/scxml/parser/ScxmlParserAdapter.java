package de.jplag.scxml.parser;

import de.jplag.AbstractParser;
import de.jplag.ParsingException;
import de.jplag.Token;
import de.jplag.scxml.ScxmlLanguage;
import de.jplag.scxml.ScxmlToken;
import de.jplag.scxml.ScxmlTokenType;
import de.jplag.scxml.parser.model.Statechart;
import de.jplag.scxml.parser.model.StatechartElement;
import de.jplag.scxml.util.AbstractStatechartVisitor;
import de.jplag.scxml.util.ScxmlView;
import de.jplag.statecharts.parser.HandcraftedScxmlTokenGenerator;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Parser adapter for SCXML statecharts.
 *
 * @author Jonas Strittmatter
 */
public class ScxmlParserAdapter extends AbstractParser {
    protected List<Token> tokens;
    protected File currentFile;
    protected AbstractStatechartVisitor visitor;
    protected ScxmlView view;

    /**
     * Parses all tokens from a set of files.
     *
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
     * Loads a statechart from a file, parses it and extracts tokens from it.
     *
     * @param file is the statechart file.
     */
    protected void parseModelFile(File file) throws ParsingException {
        currentFile = file;
        Statechart statechart;
        view = new ScxmlView(file);

        try {
            statechart = new ScxmlParser().parse(file);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new ParsingException(file, "failed to parse statechart");
        }

        visitor = createStatechartVisitor();
        visitor.visit(statechart);
        tokens.add(Token.fileEnd(currentFile));
        view.writeToFile(ScxmlLanguage.VIEW_FILE_SUFFIX);
    }

    public void addToken(ScxmlTokenType type, StatechartElement source) {
        ScxmlToken token = new ScxmlToken(type, currentFile, source);
        Token enhancedToken = view.enhanceToken(token, visitor.getCurrentTreeDepth());
        tokens.add(enhancedToken);
    }

    public void addToken(ScxmlTokenType type) {
        addToken(type, null);
    }

    /**
     * Extension point for subclasses to employ different token generators.
     *
     * @return a token generating statechart visitor.
     */
    protected AbstractStatechartVisitor createStatechartVisitor() {
        return new HandcraftedScxmlTokenGenerator(this);
    }
}
