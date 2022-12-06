package de.jplag.statecharts;

import de.jplag.TokenType;
import de.jplag.statecharts.parser.model.*;
import de.jplag.statecharts.parser.model.executable_content.*;

/**
 * Ecore meta-metamodel token type. Defines which tokens can be extracted from a metamodel.
 */
public enum StatechartTokenType implements TokenType {

    STATECHART("Statechart"),
    TRANSITION("Transition"),
    TIMED_TRANSITION("Timed transition"),
    STATE("State"),
    ON_ENTRY("OnEntry"),
    ON_EXIT("OnExit"),
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
