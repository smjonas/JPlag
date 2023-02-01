package de.jplag.yakindu;

import de.jplag.TokenType;

/**
 * Ecore meta-metamodel token type. Defines which tokens can be extracted from a metamodel.
 */
public enum YakinduTokenType implements TokenType {

    REGION_END("Region end"),
    REGION("Region", REGION_END),
    STATE_END("State end"),
    STATE("State", STATE_END),
    REGULAR_STATE_END("Regular state end"),
    REGULAR_STATE("Regular state", REGULAR_STATE_END),
    FINAL_STATE_END("Final state end"),
    FINAL_STATE("Final state", FINAL_STATE_END),
    VERTEX_END("Vertex end"),
    EVENT("Event"),
    PROPERTY("Property"),
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
    private YakinduTokenType endTokenType;
    private boolean isEndToken = false;

    YakinduTokenType(String description) {
        this.description = description;
    }

    /**
     * Creates a statechart token type that may be an end token.
     * @param endTokenType the corresponding end token type to this token.
     *                     If not null it indicates that this token is nested,
     *                     i.e. it contains child tokens.
     */
    YakinduTokenType(String description, YakinduTokenType endTokenType) {
        this(description);
        this.endTokenType = endTokenType;
        endTokenType.isEndToken = true;
    }

    public String getDescription() {
        return description;
    }

    public boolean isEndToken() {
        return this.isEndToken;
    }

}
