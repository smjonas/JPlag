package de.jplag.yakindu.util;

import de.jplag.yakindu.parser.YakinduParserAdapter;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.yakindu.base.types.Declaration;
import org.yakindu.base.types.impl.DeclarationImpl;
import org.yakindu.sct.model.sgraph.*;
import org.yakindu.sct.model.sgraph.impl.ReactionImpl;
import org.yakindu.sct.model.sgraph.impl.RegionImpl;
import org.yakindu.sct.model.sgraph.impl.StatechartImpl;
import org.yakindu.sct.model.sgraph.impl.TransitionImpl;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractYakinduVisitor {

    protected int depth;
    protected YakinduParserAdapter adapter;

    public AbstractYakinduVisitor(YakinduParserAdapter adapter) {
        this.adapter = adapter;
    }

    /**
     * Returns the current depth in the containment tree from the starting point.
     *
     * @return the depth in tree node levels.
     */
    public int getCurrentTreeDepth() {
        return depth;
    }

    private List<Integer> peekTokens(EObject object) {
        YakinduParserAdapter prevAdapter = this.adapter;
        PeekAdapter peekAdapter = new PeekAdapter();
        // Switch out the main adapter for the peek adapter
        // so that the main token stream is not affected
        this.adapter = peekAdapter;
        visit(object);
        this.adapter = prevAdapter;
        return peekAdapter.getTokenTypes();
    }

    protected <T extends EObject> EList<T> sort(EList<T> objects) {
        ECollections.sort(objects, (v1, v2) -> PeekAdapter.compareTokenTypeLists(peekTokens(v1), peekTokens(v2)));
        return objects;
    }

    protected void visitReactiveElement(ReactiveElement element) {
        for (Reaction reaction : sort(element.getLocalReactions())) {
            visitReaction(reaction);
        }
    }

    protected void visitCompositeElement(CompositeElement element) {
        for (Region region : sort(element.getRegions())) {
            visitRegion(region);
        }
    }

    protected void visit(EObject object) {
        if (object instanceof Vertex vertex) {
            visitVertex(vertex);
            return;
        }
        Map<Class<? extends EObject>, Consumer<EObject>> visitorMap = Map.of(
            StatechartImpl.class, e -> visitStatechart((Statechart) e),
            RegionImpl.class, e -> visitRegion((Region) e),
            TransitionImpl.class, e -> visitTransition((Transition) e),
            ReactionImpl.class, e -> visitReaction((Reaction) e)
        );
        visitorMap.get(object.getClass()).accept(object);
    }

    public abstract void visitStatechart(Statechart statechart);

    protected abstract void visitRegion(Region region);

    protected abstract void visitState(State state);

    protected abstract void visitVertex(Vertex vertex);

    public abstract void visitReaction(Reaction reaction);

    protected abstract void visitTransition(Transition transition);

    protected abstract void visitChoice(Choice choice);

    protected abstract void visitEntry(Entry entry);

}
