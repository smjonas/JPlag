package de.jplag.xml;

import de.jplag.ParsingException;
import de.jplag.Token;
import de.jplag.xml.parser.ScxmlParserAdapter;
import org.kohsuke.MetaInfServices;

import java.io.File;
import java.util.List;
import java.util.Set;

/**
 * Language for EMF metamodels from the Eclipse Modeling Framework (EMF).
 *
 * @author Timur Saglam
 */
@MetaInfServices(de.jplag.Language.class)
public class XmlLanguage implements de.jplag.Language {
    public static final String VIEW_FILE_SUFFIX = ".TreeView";
    public static final String FILE_ENDING = ".xml";

    private static final String NAME = "XML";
    private static final String IDENTIFIER = "xml";
    private static final int DEFAULT_MIN_TOKEN_MATCH = 6;

    protected final ScxmlParserAdapter adapter;

    public XmlLanguage() {
        this.adapter = new ScxmlParserAdapter();
    }

    @Override
    public String[] suffixes() {
        return new String[]{FILE_ENDING};
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public int minimumTokenMatch() {
        return DEFAULT_MIN_TOKEN_MATCH;
    }

    @Override
    public List<Token> parse(Set<File> files) throws ParsingException {
        return adapter.parse(files);
    }

    @Override
    public boolean useViewFiles() {
        return true;
    }

    @Override
    public String viewFileSuffix() {
        return VIEW_FILE_SUFFIX;
    }
}
