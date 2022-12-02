package de.jplag.statecharts;

import de.jplag.TokenType;

/**
 * Ecore meta-metamodel token type. Defines which tokens can be extracted from a metamodel.
 */
public enum StatechartTokenType implements TokenType {
    TRANSITION("Transition"),
    STATE("State");

    private final String description;

    StatechartTokenType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
