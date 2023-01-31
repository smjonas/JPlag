package de.jplag.yakindu.parser;

import de.jplag.yakindu.Language;
import de.jplag.yakindu.StatechartToken;
import de.jplag.yakindu.StatechartTokenType;
import de.jplag.scxml.parser.model.StatechartElement;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.sgraph.resource.ResourceUtil;
import de.jplag.AbstractParser;
import de.jplag.ParsingException;
import de.jplag.Token;
import de.jplag.yakindu.util.AbstractStatechartVisitor;
import de.jplag.yakindu.util.StatechartView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Parser adapter for Yakindu statecharts.
 *
 * @author Jonas Strittmatter
 */
public class YakinduParserAdapter extends AbstractParser {

    private static final Logger logger = LoggerFactory.getLogger(ResourceUtil.class);

    protected List<Token> tokens;
    protected File currentFile;
    protected AbstractStatechartVisitor visitor;
    protected StatechartView view;

    /**
     * Creates the parser.
     */
    public YakinduParserAdapter() {
      // Register model URIs
	  EPackage.Registry.INSTANCE.put("http://www.yakindu.org/sct/sgraph/2.0.0", SGraphPackage.eINSTANCE);
	  EPackage.Registry.INSTANCE.put("http://www.eclipse.org/gmf/runtime/1.0.2/notation", NotationPackage.eINSTANCE);

      // Register .ysc extension
      Registry registry = Registry.INSTANCE;
      Map<String, Object> extensionMap = registry.getExtensionToFactoryMap();
      String extension = Language.FILE_ENDING.substring(1);
      extensionMap.put(extension, new XMIResourceFactoryImpl());
    }

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

    private Statechart loadStatechart(File file) {
		final ResourceSet resourceSet = new ResourceSetImpl();
		try {
			Resource resource = resourceSet.getResource(URI.createFileURI(file.getAbsolutePath()), true);
			return (Statechart) EcoreUtil.getObjectByType(resource.getContents(), SGraphPackage.Literals.STATECHART);
		} catch (WrappedException exception) {
			logger.error("Could not load {}: {}", file, exception.getCause().getMessage());
		}
		return null;
    }

    /**
     * Loads a Yakindu statechart from a file, parses it and extracts tokens from it.
     *
     * @param file is the statechart file.
     */
    protected void parseModelFile(File file) throws ParsingException {
        currentFile = file;
        view = new StatechartView(file);

        Statechart statechart = loadStatechart(file);
        if (statechart == null) {
          throw new ParsingException(file, "failed to load statechart");
        }

        visitor = createStatechartVisitor();
        visitor.visit(statechart);
        tokens.add(Token.fileEnd(currentFile));
        view.writeToFile(Language.VIEW_FILE_SUFFIX);
    }

    public void addToken(StatechartTokenType type, StatechartElement source) {
        StatechartToken token = new StatechartToken(type, currentFile, source);
        StatechartToken enhancedToken = view.enhanceToken(token, visitor.getCurrentTreeDepth());
        tokens.add(enhancedToken);
    }

    public void addToken(StatechartTokenType type) {
        addToken(type, null);
    }

    /**
     * Extension point for subclasses to employ different token generators.
     *
     * @return a token generating statechart visitor.
     */
    protected AbstractStatechartVisitor createStatechartVisitor() {
        return new SimpleStatechartTokenGenerator(this);
    }
}
