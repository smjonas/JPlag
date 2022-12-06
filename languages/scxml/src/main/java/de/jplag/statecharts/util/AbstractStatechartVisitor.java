package de.jplag.statecharts.util;

import de.jplag.statecharts.parser.model.*;
import de.jplag.statecharts.parser.model.executable_content.*;

import java.util.Map;
import java.util.function.Consumer;

import static java.util.Map.entry;

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
     * Visits a StatechartElement and all nodes in the containment tree below. Note that multiple visitor method may be called for a
     * single element. For example <code>visitEClass()</code> and <code>visitEObject()</code>.
     */
    public final void visit(StatechartElement element) {
        Map<Class<?>, Consumer<StatechartElement>> visitByType = Map.ofEntries(
            entry(Statechart.class, this::visitStatechart),
            entry(State.class, this::visitState),
            entry(OnEntry.class, this::visitOnEntry),
            entry(OnExit.class, this::visitOnExit),
            entry(SimpleExecutableContent.class, this::visitSimpleExecutableContent),
            entry(Transition.class, this::visitTransition),
            entry(Assign.class, this::visitAssign),
            entry(Cancel.class, this::visitCancel),
            entry(Script.class, this::visitScript),
            entry(Send.class, this::visitSend)
        );
        visitByType.get(element.getClass()).accept(element);
    }

    protected abstract void visitStatechart(StatechartElement element);

    protected abstract void visitState(State element);

    protected abstract void visitOnEntry(OnEntry element);

    protected abstract void visitOnExit(OnExit element);

    protected abstract void visitSimpleExecutableContent(SimpleExecutableContent element);

    protected abstract void visitTransition(Transition element);

    protected abstract void visitAssign(Assign element);

    protected abstract void visitCancel(Cancel element);

    protected abstract void visitScript(Script element);

    protected abstract void visitSend(StatechartElement element);
}
