package de.jplag.yakindu.parser;

import de.jplag.TokenType;
import de.jplag.yakindu.YakinduTokenType;
import de.jplag.yakindu.util.AbstractYakinduVisitor;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.yakindu.base.types.Declaration;
import org.yakindu.base.types.Event;
import org.yakindu.base.types.Property;
import org.yakindu.sct.model.sgraph.*;

import java.util.List;

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

    public SimpleYakinduTokenGenerator(YakinduParserAdapter adapter) {
        super(adapter);
    }

    @Override
    public void visitStatechart(Statechart statechart) {
        visitReactiveElement(statechart);
        visitCompositeElement(statechart);
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

    protected void visitStateContents(State state) {
        depth++;
        visitReactiveElement(state);
        visitCompositeElement(state);
        depth--;
    }

    @Override
    public void visitState(State state) {
        adapter.addToken(STATE, state);
        visitStateContents(state);
        // No end token since that gets already added in visitVertex
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
        boolean hasTrigger = reaction.getTrigger() != null;
        boolean hasEffect = reaction.getEffect() != null;

        YakinduTokenType type = null;
        if (hasTrigger && hasEffect) {
            type = TRIGGER_EFFECT;
        } else if (hasTrigger) {
            type = TRIGGER;
        } else if (hasEffect) {
            type = EFFECT;
        }
        adapter.addToken(type, reaction);
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
