package de.jplag.yakindu.parser;

import de.jplag.yakindu.util.AbstractYakinduVisitor;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.yakindu.base.types.Declaration;
import org.yakindu.base.types.Event;
import org.yakindu.base.types.Property;
import org.yakindu.sct.model.sgraph.*;

import static de.jplag.yakindu.YakinduTokenType.*;

/*
a
  b
    c
  b
=> Sorting the child elements of a by comparing their string representations,
(here: bc, b), so the first element b would come before the second because b < bc

vs.
a
  b
  b
    c
=> a b b c

*/

/**
 * Visits a metamodel containment tree and extracts the relevant token.
 *
 * @author Timur Saglam
 */
public class SimpleYakinduTokenGenerator extends AbstractYakinduVisitor {
    protected YakinduParserAdapter adapter;
    protected YakinduParserAdapter mainAdapter;

    /**
     * Creates the visitor.
     *
     * @param adapter is the parser adapter which receives the generated tokens.
     */
    public SimpleYakinduTokenGenerator(YakinduParserAdapter adapter) {
        this.mainAdapter = adapter;
        this.adapter = mainAdapter;
    }

    private String peekTokens(EObject object) {
        YakinduParserAdapter prevAdapter = this.adapter;
        PeekAdapter peekAdapter = new PeekAdapter();
        // Switch out the main adapter for the peek adapter
        // so that the main token stream is not affected
        this.adapter = peekAdapter;
        visit(object);
        this.adapter = prevAdapter;
        return peekAdapter.getTokenListRepresentation();
    }

    private <T extends EObject> EList<T> sort(EList<T> objects) {
        ECollections.sort(objects, (v1, v2) -> peekTokens(v1).compareTo(peekTokens(v2)));
        return objects;
    }

    @Override
    public void visitStatechart(Statechart statechart) {
        for (Region region : sort(statechart.getRegions())) {
            visitRegion(region);
        }
    }

    @Override
    protected void visitDeclaration(Declaration declaration) {
        if (declaration instanceof Property) {
            adapter.addToken(PROPERTY, declaration);
        } else if (declaration instanceof Event) {
            adapter.addToken(EVENT, declaration);
        }
    }

    @Override
    public void visitRegion(Region region) {
        adapter.addToken(REGION, region);
        depth++;
        for (Vertex vertex : sort(region.getVertices())) {
            visitVertex(vertex);
        }
        depth--;
        adapter.addToken(REGION_END, region);
    }

    @Override
    public void visitState(State state) {
        adapter.addToken(STATE, state);
        depth++;
        for (Region region : sort(state.getRegions())) {
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
            for (Transition transition : sort(vertex.getOutgoingTransitions())) {
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
