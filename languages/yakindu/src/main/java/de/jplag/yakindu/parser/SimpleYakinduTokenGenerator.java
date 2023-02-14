package de.jplag.yakindu.parser;

import de.jplag.yakindu.YakinduTokenType;
import de.jplag.yakindu.util.AbstractYakinduVisitor;
import org.apache.commons.lang3.ArrayUtils;
import org.checkerframework.checker.units.qual.C;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.xtext.validation.impl.ConcreteSyntaxConstraintProvider;
import org.yakindu.base.types.Declaration;
import org.yakindu.base.types.Event;
import org.yakindu.base.types.Property;
import org.yakindu.sct.model.sgraph.*;

import java.util.Comparator;
import java.util.Map;

import static de.jplag.yakindu.YakinduTokenType.*;

/**
 * Visits a metamodel containment tree and extracts the relevant token.
 *
 * @author Timur Saglam
 */
public class SimpleYakinduTokenGenerator extends AbstractYakinduVisitor {
    protected final YakinduParserAdapter adapter;

    /**
     * Creates the visitor.
     *
     * @param adapter is the parser adapter which receives the generated tokens.
     */
    public SimpleYakinduTokenGenerator(YakinduParserAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected void visitDeclaration(Declaration declaration) {
        if (declaration instanceof Property) {
            adapter.addToken(PROPERTY, declaration);
        } else if (declaration instanceof Event) {
            adapter.addToken(EVENT, declaration);
        }
    }

    private int orderByVertexSubclass(Vertex vertex) {
        if (vertex instanceof State) {
            return 1;
        } else if (vertex instanceof FinalState) {
            return 2;
        } else if (vertex instanceof RegularState) {
            return 3;
        } else if (vertex instanceof Choice) {
            return 4;
        } else if (vertex instanceof Entry) {
            return 5;
        } else if (vertex instanceof Exit) {
            return 6;
        } else if (vertex instanceof Synchronization) {
            return 7;
        }
        throw new IllegalArgumentException("Unexpected subclass for vertex " + vertex);
    }

    @Override
    public void visitRegion(Region region) {
        adapter.addToken(REGION, region);
        depth++;

        var sortedVertices = region.getVertices();
        // For robustness against reordering, first sort by the number of outgoing transitions (the child elements of Vertex)
        ECollections.sort(sortedVertices, (v1, v2) -> {
            int v1TransitionsCount = v1.getOutgoingTransitions() == null ? 0 : v1.getOutgoingTransitions().size();
            int v2TransitionsCount = v2.getOutgoingTransitions() == null ? 0 : v2.getOutgoingTransitions().size();
            return v1TransitionsCount - v2TransitionsCount;
        });

        // Then, sort by the subclass
        ECollections.sort(sortedVertices, (v1, v2) -> orderByVertexSubclass(v1) - orderByVertexSubclass(v2));

        for (Vertex vertex : sortedVertices) {
            visitVertex(vertex);
        }
        depth--;
        adapter.addToken(REGION_END, region);
    }

    @Override
    public void visitState(State state) {
        adapter.addToken(STATE, state);
        depth++;
        for (Region region : state.getRegions()) {
            visitRegion(region);
        }
        depth--;
    }

    @Override
    public void visitVertex(Vertex vertex) {
        if (vertex instanceof State) {
            visitState((State) vertex);
        } else if (vertex instanceof FinalState) {
            adapter.addToken(FINAL_STATE, vertex);
        } else if (vertex instanceof RegularState) {
            adapter.addToken(REGULAR_STATE, vertex);
        } else if (vertex instanceof Choice choice) {
            visitChoice(choice);
        } else if (vertex instanceof Entry entry) {
            visitEntry(entry);
        } else if (vertex instanceof Exit) {
            adapter.addToken(EXIT, vertex);
        } else if (vertex instanceof Synchronization) {
            adapter.addToken(SYNCHRONIZATION, vertex);
        }

        depth++;
        if (vertex.getOutgoingTransitions() != null) {
            for (Transition transition : vertex.getOutgoingTransitions()) {
                visitTransition(transition);
            }
        }
        depth--;
        adapter.addToken(VERTEX_END, vertex);
    }

    @Override
    public void visitReaction(Reaction reaction) {
        if (reaction.getTrigger() != null) {
            adapter.addToken(TRIGGER, reaction);
        }
        if (reaction.getEffect() != null) {
            adapter.addToken(EFFECT, reaction);
        }
    }

    @Override
    public void visitTransition(Transition transition) {
        adapter.addToken(TRANSITION, transition);
        visitReaction(transition);
    }

    @Override
    public void visitChoice(Choice choice) {
        adapter.addToken(CHOICE, choice);
    }

    @Override
    public void visitEntry(Entry entry) {
        adapter.addToken(ENTRY, entry);
    }
}
