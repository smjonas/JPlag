package de.jplag.statecharts.util;

import de.jplag.statecharts.parser.model.*;
import de.jplag.statecharts.parser.model.executable_content.Action;
import de.jplag.statecharts.parser.model.executable_content.ExecutableContent;
import de.jplag.statecharts.parser.model.executable_content.SimpleExecutableContent;

/**
 * Visitor for the containment tree of an EMF Metamodel.
 *
 * @author Timur Saglam
 */
public abstract class AbstractStatechartVisitor {

    private int currentTreeDepth;

    protected AbstractStatechartVisitor() {
    }

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
        // Map<Class<?>, Consumer<>> visitByType = Map.ofEntries(
        //     entry(OnEntry.class, this::visitOnEntry),
        //     entry(OnExit.class, this::visitOnExit),
        //     entry(SimpleExecutableContent.class, this::visitSimpleExecutableContent),
        //     entry(Transition.class, this::visitTransition),
        //     entry(Assign.class, this::visitAssign),
        //     entry(Cancel.class, this::visitCancel),
        //     entry(Script.class, this::visitScript),
        //     entry(Send.class, this::visitSend)
        // );
        // visitByType.get(element.getClass()).accept(element);

        if (element instanceof Statechart statechart) {
            visitStatechart(statechart);
        } else if (element instanceof State state) {
            visitState(state);
        } else if (element instanceof Action action) {
            visitAction(action);
        } else if (element instanceof SimpleExecutableContent simpleContent) {
            visitSimpleExecutableContent(simpleContent);
        } else if (element instanceof ExecutableContent content) {
            visitExecutableContent(content);
        }
    }

    protected abstract void visitStatechart(Statechart statechart);

    protected abstract void visitState(State state);

    protected abstract void visitAction(Action action);

    protected abstract void visitExecutableContent(ExecutableContent content);

    protected abstract void visitSimpleExecutableContent(SimpleExecutableContent content);

    protected abstract void visitTransition(Transition transition);

}
