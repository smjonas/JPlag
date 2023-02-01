package de.jplag.yakindu.parser;

import de.jplag.yakindu.YakinduTokenType;
import de.jplag.yakindu.util.AbstractStatechartVisitor;
import org.yakindu.sct.model.sgraph.*;

import static de.jplag.yakindu.YakinduTokenType.*;


/**
 * Visits a metamodel containment tree and extracts the relevant token.
 *
 * @author Timur Saglam
 */
public class SimpleStatechartTokenGenerator extends AbstractStatechartVisitor {
    protected final YakinduParserAdapter adapter;

    // TODO: read documentation (e.g. about Reactions)


    /**
     * Creates the visitor.
     *
     * @param adapter is the parser adapter which receives the generated tokens.
     */
    public SimpleStatechartTokenGenerator(YakinduParserAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void visitRegion(Region region) {
        adapter.addToken(YakinduTokenType.REGION);
        depth++;
        for (Vertex vertex : region.getVertices()) {
            visitVertex(vertex);
        }
        depth--;
        adapter.addToken(END_REGION);
    }

    @Override
    public void visitVertex(Vertex vertex) {
        if (vertex instanceof Choice) {
           visitChoice((Choice) vertex);
        } else if (vertex instanceof Entry) {
            visitEntry((Entry) vertex);
        } else if (vertex instanceof Exit) {
            visitExit((Exit) vertex);
        } else if (vertex instanceof Synchronization) {
            visitSynchronization((Synchronization) vertex);
        }

        depth++;
        if (vertex.getOutgoingTransitions() != null) {
            for (Transition transition : vertex.getOutgoingTransitions()) {
                visitTransition(transition);
            }
        }
        depth--;
        adapter.addToken(VERTEX_END);
    }

    @Override
    public void visitTransition(Transition transition) {
        adapter.addToken(YakinduTokenType.TRANSITION);
    }

    // TODO: sub tokens based on ChoiceKind in ImprovedTokenGenerator
    @Override
    public void visitChoice(Choice choice) {
        adapter.addToken(CHOICE);
    }

    @Override
    public void visitEntry(Entry entry) {
        adapter.addToken(ENTRY);
    }

    @Override
    public void visitExit(Exit exit) {
        adapter.addToken(EXIT);
    }

    @Override
    public void visitSynchronization(Synchronization synchronization) {
        adapter.addToken(SYNCHRONIZATION);
    }
}
