package de.jplag.emf.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory.Registry;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import de.jplag.AbstractParser;
import de.jplag.ParsingException;
import de.jplag.Token;
import de.jplag.emf.Language;
import de.jplag.emf.MetamodelToken;
import de.jplag.emf.MetamodelTokenType;
import de.jplag.emf.util.AbstractMetamodelVisitor;
import de.jplag.emf.util.AbstractModelView;
import de.jplag.emf.util.EMFUtil;
import de.jplag.emf.util.EmfaticModelView;

/**
 * Parser for EMF metamodels.
 */
public class EcoreParser extends AbstractParser {
    protected List<Token> tokens;
    protected File currentFile;
    protected AbstractModelView treeView;
    protected AbstractMetamodelVisitor visitor;

    /**
     * Creates the parser.
     */
    public EcoreParser() {
        EMFUtil.registerEcoreExtension();
        registerURIs();
    }

    private void registerURIs() {
    }

    /**
     * Parses all tokens from a set of files.
     * @param files is the set of files.
     * @return the list of parsed tokens.
     */
    public List<Token> parse(Set<File> files) throws ParsingException {
        tokens = new ArrayList<>();
        for (File file : files) {
            parseModelFile(file);
        }
        return tokens;
    }

    private Resource loadModelResource(File file) throws ParsingException {
        final ResourceSet resourceSet = new ResourceSetImpl();
        URI uri = URI.createFileURI(file.getAbsolutePath());
        Resource resource = null;
        try {
            resource = resourceSet.getResource(uri, false);
        } catch (WrappedException e) {
            //throw new ParsingException(e.getMessage());
            resource = resourceSet.getResource(uri, false);
        }
        return Resource(resource);
    }

    /**
     * Loads a metamodel from a file and parses it.
     * @param file is the metamodel file.
     */
    protected void parseModelFile(File file) throws ParsingException {
        currentFile = file;
        Resource model = loadModelResource(file);
        if (model == null) {
            throw new ParsingException(file, "failed to load model");
        } else {
            treeView = createView(file, model);
            for (EObject root : model.getContents()) {
                visitor = createMetamodelVisitor();
                visitor.visit(root);
            }
            tokens.add(Token.fileEnd(currentFile));
            treeView.writeToFile(Language.VIEW_FILE_SUFFIX);
        }
    }

    /**
     * Creates a model view. Can be overriden in subclasses for alternative views.
     * @param file is the path for the view file to be created.
     * @param modelResource is the resource containing the metamodel.
     * @return the view implementation.
     */
    protected AbstractModelView createView(File file, Resource modelResource) {
        return new EmfaticModelView(file, modelResource);
    }

    /**
     * Extension point for subclasses to employ different token generators.
     * @return a token generating metamodel visitor.
     */
    protected AbstractMetamodelVisitor createMetamodelVisitor() {
        return new MetamodelTokenGenerator(this);
    }

    /**
     * Adds an token to the parser.
     * @param type is the token type.
     * @param source is the corresponding {@link EObject} for which the token is added.
     */
    void addToken(MetamodelTokenType type, EObject source) {
        MetamodelToken token = new MetamodelToken(type, currentFile, source);
        tokens.add(treeView.convertToMetadataEnrichedToken(token));
    }
}
