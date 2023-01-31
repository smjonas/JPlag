package de.jplag.yakindu.util;

import org.yakindu.sct.model.sgraph.*;

/**
 * Visitor for the containment tree of an EMF Metamodel.
 *
 * @author Timur Saglam
 */
public abstract class AbstractStatechartVisitor {

    protected int depth;

    /**
     * Returns the current depth in the containment tree from the starting point.
     *
     * @return the depth in tree node levels.
     */
    public int getCurrentTreeDepth() {
        return depth;
    }

    public abstract void visitStatechart(Statechart statechart);

    public abstract void visitRegion(Region region);

    public abstract void visitVertex(Vertex vertex);

    public abstract void visitTransition(Transition transition);

    // TODO: sub tokens based on ChoiceKind in ImprovedTokenGenerator
    public abstract void visitChoice(Choice choice);

    public abstract void visitEntry(Entry entry);

    public abstract void visitExit(Exit exit);

    public abstract void visitSynchronization(Synchronization synchronization);
}
