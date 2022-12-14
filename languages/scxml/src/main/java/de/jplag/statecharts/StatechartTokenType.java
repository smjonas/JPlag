package de.jplag.statecharts;

import de.jplag.TokenType;

/**
 * Ecore meta-metamodel token type. Defines which tokens can be extracted from a metamodel.
 */
public enum StatechartTokenType implements TokenType {

    STATECHART("Statechart"),
    STATECHART_END("Statechart end"),
    TRANSITION("Transition"),
    TRANSITION_END("Transition end"),
    TIMED_TRANSITION("Timed transition"),
    STATE("State begin"),
    STATE_END("State end"),
    REGION("Region"),
    INITIAL_STATE("Initial state"),
    PARALLEL_STATE("Parallel state"),
    ON_ENTRY("OnEntry"),
    ON_EXIT("OnExit"),
    ACTION_END("Action end"),
    // Simple executable content
    RAISE("Raise"),
    IF("If"),
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

    StatechartTokenType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
