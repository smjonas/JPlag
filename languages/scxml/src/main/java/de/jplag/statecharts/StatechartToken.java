package de.jplag.statecharts;

import de.jplag.Token;
import de.jplag.TokenType;

import java.io.File;

/**
 * EMF metamodel token.
 *
 * @author Timur Saglam
 */
public class StatechartToken extends Token {

    /**
     * Creates an Ecore metamodel token that corresponds to an EObject.
     *
     * @param type    is the type of the token.
     * @param file    is the source model file.
     */
    public StatechartToken(StatechartTokenType type, File file) {
        this(type, file, NO_VALUE, NO_VALUE, NO_VALUE);
    }

    /**
     * Creates an Ecore metamodel token.
     *
     * @param type is the type of the token.
     * @param file is the source model file.
     */
    public StatechartToken(TokenType type, File file) {
        this(type, file, NO_VALUE, NO_VALUE, NO_VALUE);
    }

    /**
     * Creates a token with column and length information.
     *
     * @param type   is the token type.
     * @param file   is the source code file.
     * @param line   is the line index in the source code where the token resides. Cannot be smaller than 1.
     * @param column is the column index, meaning where the token starts in the line.
     * @param length is the length of the token in the source code.
     *               is the corresponding eObject in the model from which this token was extracted
     */
    public StatechartToken(TokenType type, File file, int line, int column, int length) {
        super(type, file, line, column, length);
    }

    /**
     * @return the optional corresponding EObject of the token.
     */
//    public Optional<EObject> getEObject() {
//        return eObject;
//    }
}
