package de.jplag.yakindu;

import de.jplag.Token;
import de.jplag.TokenType;
import org.eclipse.emf.ecore.EObject;
import java.io.File;

/**
 * Yakindu Statechart token.
 *
 */
public class YakinduToken extends Token {

    private final EObject eObject;

    /**
     * Creates a Yakindu Statecharts token that corresponds to an EObject.
     *
     * @param type    is the type of the token.
     * @param file    is the source model file.
     * @param element is the corresponding eObject in the model from which this token was extracted.
     */
    public YakinduToken(YakinduTokenType type, File file, EObject eObject) {
        super(type, file, NO_VALUE, NO_VALUE, NO_VALUE);
        this.eObject = eObject;
    }

    public YakinduToken(TokenType type, File file, int line, int column, int length, EObject eObject) {
        super(type, file, line, column, length);
        this.eObject = eObject;
    }

    /**
     * @return the EObject corresponding to the token.
     */
    public EObject getEObject() {
        return eObject;
    }
}
