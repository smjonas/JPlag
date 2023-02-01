package de.jplag.yakindu.parser;

import de.jplag.yakindu.util.AbstractYakinduVisitor;
import org.yakindu.base.types.Declaration;
import org.yakindu.base.types.Event;
import org.yakindu.base.types.Property;
import org.yakindu.sct.model.sgraph.*;

import static de.jplag.yakindu.YakinduTokenType.*;


/**
 * Visits a metamodel containment tree and extracts the relevant token.
 *
 * @author Timur Saglam
 */
public class SimpleYakinduTokenGenerator extends AbstractYakinduVisitor {
    protected final YakinduParserAdapter adapter;

    // TODO: read documentation (e.g. about Reactions)


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
        }
    }

    @Override
    public void visitRegion(Region region) {
        adapter.addToken(REGION, region);
        depth++;
        for (Vertex vertex : region.getVertices()) {
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
        } else if (vertex instanceof Choice) {
            visitChoice((Choice) vertex);
        } else if (vertex instanceof Entry) {
            visitEntry((Entry) vertex);
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
