package de.jplag.yakindu.parser;

import de.jplag.yakindu.YakinduTokenType;
import de.jplag.yakindu.sorting.NoOpSorter;
import de.jplag.yakindu.sorting.RecursiveSorter;
import de.jplag.yakindu.sorting.SimpleSorter;
import de.jplag.yakindu.sorting.Sorter;
import de.jplag.yakindu.util.AbstractYakinduVisitor;
import org.yakindu.sct.model.sgraph.*;

import static de.jplag.yakindu.YakinduTokenType.*;

/**
 * Visits a metamodel containment tree and extracts the relevant token.
 *
 * @author Timur Saglam
 */
public class SimpleYakinduTokenGenerator extends AbstractYakinduVisitor {

    private Sorter sorter;

    public SimpleYakinduTokenGenerator(YakinduParserAdapter adapter) {
        super(adapter);
        this.sorter = new NoOpSorter();
    }


    protected void visitCompositeElement(CompositeElement element) {
        for (Region region : element.getRegions()) {
            visitRegion(region);
        }
    }

    protected void visitReactiveElement(ReactiveElement element) {
        for (Reaction reaction : sorter.sort(element.getLocalReactions())) {
            visitReaction(reaction);
        }
    }

    @Override
    public void visitStatechart(Statechart statechart) {
        visitReactiveElement(statechart);
        visitCompositeElement(statechart);
    }

    @Override
    public void visitRegion(Region region) {
        adapter.addToken(REGION, region);
        depth++;
        for (Vertex vertex : sorter.sort(region.getVertices())) {
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
            for (Transition transition : vertex.getOutgoingTransitions()) {
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
        if (!hasTrigger && !hasEffect) {
            return;
        }

        YakinduTokenType type;
        if (hasTrigger && hasEffect) {
            type = TRIGGER_EFFECT;
        } else if (hasTrigger) {
            type = TRIGGER;
        } else {
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
