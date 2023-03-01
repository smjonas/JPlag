package de.jplag.yakindu;

import de.jplag.TokenType;
import org.eclipse.emf.ecore.EObject;
import org.yakindu.sct.model.sgraph.Region;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Transition;

import java.util.List;
import java.util.Set;

/**
 * Ecore meta-metamodel token type. Defines which tokens can be extracted from a metamodel.
 */
public enum YakinduTokenType implements TokenType {

    REGION("Region") {
        @Override
        public String getDescriptionPrefix(EObject eObject) {
            return ((Region) eObject).getName() + ": ";
        }
    },
    REGION_END("Region end", Set.of(REGION)),
    //    STATE_END("State end", Set.of(STATE, REGULAR_STATE, FINAL_STATE)),
//    REGULAR_STATE_END("Regular state end"),
//    FINAL_STATE_END("Final state end"),
    EVENT("Event"),
    PROPERTY("Property"),  // In the metamodel diagram this is the Variable class
    TRANSITION("Transition"),
    TRIGGER("Trigger"),
    EFFECT("Effect"),
    // Vertex tokens
    // Subclasses of RegularState
    FINAL_STATE("Final state"),
    STATE("State") {
        @Override
        public String getDescriptionPrefix(EObject eObject) {
            return ((State) eObject).getName() + ": ";
        }
    },
    ORTHOGONAL_STATE("Orthogonal state"),
    //ORTHOGONAL_STATE("Orthogonal state"),
    //ORTHOGONAL_STATE("Orthogonal state"),
    //ORTHOGONAL_STATE("Orthogonal state"),
    //ORTHOGONAL_STATE("Orthogonal state"),
    // Pseudostate + subclasses
    CHOICE("Choice"),
    DYNAMIC_CHOICE("Dynamic choice"),
    STATIC_CHOICE("Static choice"),
    ENTRY("Entry"),
    INITIAL_ENTRY("Initial entry"),
    SHALLOW_HISTORY_ENTRY("Shallow history entry"),
    DEEP_HISTORY_ENTRY("Deep history entry"),
    EXIT("Exit"),
    SYNCHRONIZATION("Synchronization"),
    VERTEX_END("Vertex end", Set.of(FINAL_STATE, STATE, CHOICE, DYNAMIC_CHOICE, STATIC_CHOICE, ENTRY, INITIAL_ENTRY, SHALLOW_HISTORY_ENTRY, DEEP_HISTORY_ENTRY, EXIT, SYNCHRONIZATION));

    private final String description;
    private boolean isLeaf = true;
    private boolean isEndToken = false;

    YakinduTokenType(String description) {
        this.description = description;
    }

    /**
     * Creates a Yakindu token type that is an end token.
     *
     * @param startTokens the set of start tokens that are closed by this token.
     *                    The isLeaf attribute of all the startTokens is thus set to false.
     */
    YakinduTokenType(String description, Set<YakinduTokenType> startTokens) {
        this(description);
        this.isEndToken = true;
        for (YakinduTokenType tokenType : startTokens) {
            tokenType.isLeaf = false;
        }
    }

    public String getDescriptionPrefix(EObject eObject) {
        return "";
    }

    public String getDescription() {
        return description;
    }

    public boolean isLeaf() {
        return this.isLeaf;
    }

    public boolean isEndToken() {
        return this.isEndToken;
    }
}
