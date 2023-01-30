package de.jplag.statecharts.parser;

import de.jplag.statecharts.StatechartTokenType;
import de.jplag.statecharts.parser.model.State;
import de.jplag.statecharts.parser.model.Statechart;
import de.jplag.statecharts.parser.model.Transition;
import de.jplag.statecharts.parser.model.executable_content.*;
import de.jplag.statecharts.util.AbstractStatechartVisitor;

import java.util.List;

import static de.jplag.statecharts.StatechartTokenType.*;


/**
 * Visits a metamodel containment tree and extracts the relevant token.
 *
 * @author Timur Saglam
 */
public class SimpleStatechartTokenGenerator extends AbstractStatechartVisitor {
    protected final ScxmlParserAdapter adapter;

    // TODO: read documentation (e.g. about Reactions)


    /**
     * Creates the visitor.
     *
     * @param adapter is the parser adapter which receives the generated tokens.
     */
    public SimpleStatechartTokenGenerator(ScxmlParserAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void visitStatechart(Statechart statechart) {
        for (Region region : statechart.regions) {
            visitRegion(region);
        }
    }

    @Override
    public void visitRegion(Region region) {
        adapter.addToken(REGION);
        depth++;
        for (Vertex vertex : region.vertices) {
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
        if (vertex.outgoingTransitions != null) {
            for (Transition transition : vertex.outgoingTransitions) {
                visitTransition(transition);
            }
        }
        depth--;
        adapter.addToken(VERTEX_END);
    }

    @Override
    public void visitTransition(Transition transition) {
        adapter.addToken(TRANSITION);
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
