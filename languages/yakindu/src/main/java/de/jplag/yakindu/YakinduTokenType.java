package de.jplag.yakindu;

import de.jplag.TokenType;

/**
 * Ecore meta-metamodel token type. Defines which tokens can be extracted from a metamodel.
 */
public enum YakinduTokenType implements TokenType {

    REGION("Region"),
    REGION_END("Region end", true),
    REGULAR_STATE("Regular state"),
    FINAL_STATE("Final state"),
    VERTEX_END("Vertex end", true),
    EVENT("Event"),
    VARIABLE("Variable"),
    TRANSITION("Transition"),
    TRIGGER("Trigger"),
    EFFECT("Effect"),
    CHOICE("Choice"),
    DYNAMIC_CHOICE("Dynamic choice"),
    STATIC_CHOICE("Static choice"),
    ENTRY("Entry"),
    INITIAL_ENTRY("Initial entry"),
    SHALLOW_HISTORY_ENTRY("Shallow history entry"),
    DEEP_HISTORY_ENTRY("Deep history entry"),
    EXIT("Exit"),
    SYNCHRONIZATION("Synchronization");

    private final String description;
    private boolean isEndToken = false;

    YakinduTokenType(String description) {
        this.description = description;
    }

    /**
     * Creates a statechart token type that may be an end token.
     * @param isEndToken indicates that the token is an end token
     */
    YakinduTokenType(String description, boolean isEndToken) {
        this(description);
        this.isEndToken = isEndToken;
    }

    public String getDescription() {
        return description;
    }

    public boolean isEndToken() {
        return isEndToken;
    }

}
