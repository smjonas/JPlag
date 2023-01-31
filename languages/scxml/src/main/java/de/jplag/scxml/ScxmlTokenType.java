package de.jplag.scxml;

import de.jplag.TokenType;

/**
 * Ecore meta-metamodel token type. Defines which tokens can be extracted from a metamodel.
 */
public enum ScxmlTokenType implements TokenType {

    TRANSITION("Transition"),
    TRANSITION_END("Transition end", true),
    GUARDED_TRANSITION("Guarded transition"),
    TIMED_TRANSITION("Timed transition"),
    STATE("State begin"),
    STATE_END("State end", true),
    REGION("Region"),
    INITIAL_STATE("Initial state"),
    PARALLEL_STATE("Parallel state"),
    ON_ENTRY("OnEntry"),
    ON_EXIT("OnExit"),
    ACTION_END("Action end", true),
    // Simple executable content
    RAISE("Raise"),
    IF("If"),
    IF_END("If end", true),
    ELSE_IF("Else if"),
    ELSE("Else"),
    FOREACH("For each"),
    LOG("Log"),
    // Other executable content
    ASSIGNMENT("Assignment"),
    CANCEL("Cancel"),
    SCRIPT("Script"),
    SEND("Send");

    private final String description;
    private boolean isEndToken = false;

    ScxmlTokenType(String description) {
        this.description = description;
    }

    /**
     * Creates a statechart token type that may be an end token.
     * @param isEndToken indicates that the token is an end token
     */
    ScxmlTokenType(String description, boolean isEndToken) {
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
