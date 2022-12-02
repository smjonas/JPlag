package de.jplag.statecharts.util;

/**
 * Visitor for the containment tree of an EMF Metamodel.
 *
 * @author Timur Saglam
 */
public abstract class AbstractStatechartVisitor {

    protected AbstractStatechartVisitor() {
    }

    private int currentTreeDepth;

    /**
     * Returns the current depth in the containment tree from the starting point.
     *
     * @return the depth in tree node levels.
     */
    public int getCurrentTreeDepth() {
        return currentTreeDepth;
    }

    /**
     * Visits an EObject and all nodes in the containment tree below. Note that multiple visitor method may be called for a
     * single element. For example <code>visitEClass()</code> and <code>visitEObject()</code>.
     */
    public final void visit() {

    }

}
