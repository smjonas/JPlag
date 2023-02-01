package de.jplag.yakindu.parser;

import de.jplag.AbstractParser;
import de.jplag.ParsingException;
import de.jplag.Token;
import de.jplag.yakindu.Language;
import de.jplag.yakindu.YakinduToken;
import de.jplag.yakindu.YakinduTokenType;
import de.jplag.yakindu.util.AbstractStatechartVisitor;
import de.jplag.yakindu.util.StatechartView;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.xtext.validation.EObjectDiagnosticImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yakindu.base.SGraphPackage;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.sgraph.resource.ResourceUtil;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory.Registry;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        registerURIs();
        registerFileExtensions();
    }

    private void registerURIs() {
        EPackage.Registry registry = EPackage.Registry.INSTANCE;
        registry.put("http://www.yakindu.org/sct/sgraph/2.0.0", SGraphPackage.eINSTANCE);
        registry.put("http://www.eclipse.org/gmf/runtime/1.0.2/notation", NotationPackage.eINSTANCE);
    }

    private void registerFileExtensions() {
        Map<String, Object> extensionMap = Registry.INSTANCE.getExtensionToFactoryMap();
        for (String suffix : Language.SUFFIXES) {
            String extension = suffix.substring(1);
            extensionMap.put(extension, new XMIResourceFactoryImpl());
        }
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

    private Statechart loadStatechart(File file) throws ParsingException {
		final ResourceSet resourceSet = new ResourceSetImpl();
        URI uri = URI.createFileURI(file.getAbsolutePath());
        Resource resource;
		try {
            resource = resourceSet.getResource(uri, true);
        } catch (Exception e) {
            logger.trace("{}: failed to load (parts of) statechart model:\n {}", file, e.getCause().getMessage());
            resource = resourceSet.getResource(uri, false);
        }

//		} catch (WrappedException exception) {
//            throw new ParsingException(file, "failed to load statechart:\n" +  exception.getCause().getMessage());
        //}
        return (Statechart) EcoreUtil.getObjectByType(resource.getContents(), SGraphPackage.Literals.STATECHART);
    }

    /**
     * Loads a Yakindu statechart from a file, parses it and extracts tokens from it.
     *
     * @param file is the statechart file.
     */
    protected void parseModelFile(File file) throws ParsingException {
        currentFile = file;
        //view = new StatechartView(file);

        Statechart statechart = loadStatechart(file);
        visitor = createStatechartVisitor();
        visitor.visit(statechart);
        tokens.add(Token.fileEnd(currentFile));
        // view.writeToFile(Language.VIEW_FILE_SUFFIX);
    }

    public void addToken(YakinduTokenType type, EObject source) {
        YakinduToken token = new YakinduToken(type, currentFile, source);
        // YakinduToken enhancedToken = view.enhanceToken(token, visitor.getCurrentTreeDepth());
        // TODO: use enhanced token
        tokens.add(token);
    }

    public void addToken(YakinduTokenType type) {
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
