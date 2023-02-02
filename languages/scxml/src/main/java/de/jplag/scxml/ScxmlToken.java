package de.jplag.scxml;

import de.jplag.Token;
import de.jplag.TokenType;
import de.jplag.scxml.parser.model.StatechartElement;

import java.io.File;

/**
 * EMF metamodel token.
 *
 * @author Timur Saglam
 */
public class ScxmlToken extends Token {

    private final StatechartElement element;

    /**
     * Creates an Ecore metamodel token that corresponds to an EObject.
     *
     * @param type    is the type of the token.
     * @param file    is the source model file.
     * @param element is the corresponding eObject in the model from which this token was extracted.
     */
    public ScxmlToken(TokenType type, File file, StatechartElement element) {
        super(type, file, NO_VALUE, NO_VALUE, NO_VALUE);
        this.element = element;
    }

    public ScxmlToken(TokenType type, File file, int line, int column, int length, StatechartElement element) {
        super(type, file, line, column, length);
        this.element = element;
    }

    /**
     * @return the StatechartElement corresponding to the token.
     */
    public StatechartElement getStatechartElement() {
        return element;
    }
}
