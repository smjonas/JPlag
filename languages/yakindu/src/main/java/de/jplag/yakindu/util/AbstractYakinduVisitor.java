package de.jplag.yakindu.util;

import org.eclipse.emf.common.util.EList;
import org.yakindu.sct.model.sgraph.*;

/**
 * Visitor for the containment tree of an EMF Metamodel.
 *
 * @author Timur Saglam
 */
public abstract class AbstractYakinduVisitor {

    protected int depth;

    /**
     * Returns the current depth in the containment tree from the starting point.
     *
     * @return the depth in tree node levels.
     */
    public int getCurrentTreeDepth() {
        return depth;
    }

    public void visit(Statechart statechart) {
        for (Scope scope : statechart.getScopes()) {
            visitScope(scope);
        }
        for (Region region : statechart.getRegions()) {
            visitRegion(region);
        }
    }

    protected abstract void visitScope(Scope scope);

    protected abstract void visitRegion(Region region);

    protected abstract void visitVertex(Vertex vertex);

    public abstract void visitReaction(Reaction reaction);

    protected abstract void visitTransition(Transition transition);

    protected abstract void visitChoice(Choice choice);

    protected abstract void visitEntry(Entry entry);

}