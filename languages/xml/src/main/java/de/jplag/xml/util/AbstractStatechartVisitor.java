package de.jplag.xml.util;

import de.jplag.xml.parser.model.StatechartElement;
import de.jplag.xml.parser.model.executable_content.ExecutableContent;
import de.jplag.xml.parser.model.executable_content.SimpleExecutableContent;

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

        if (element instanceof de.jplag.xml.parser.model.Statechart statechart) {
            visitStatechart(statechart);
        } else if (element instanceof de.jplag.xml.parser.model.State state) {
            visitState(state);
        } else if (element instanceof de.jplag.xml.parser.model.OnEntry onEntry) {
            visitOnEntry(onEntry);
        } else if (element instanceof de.jplag.xml.parser.model.OnExit onExit) {
            visitOnExit(onExit);
        } else if (element instanceof ExecutableContent content) {
            visitExecutableContent(content);
        }
    }

    public void visit() {

    }

    protected abstract void visitStatechart(de.jplag.xml.parser.model.Statechart statechart);

    protected abstract void visitState(de.jplag.xml.parser.model.State state);

    protected abstract void visitOnEntry(de.jplag.xml.parser.model.OnEntry onEntry);

    protected abstract void visitOnExit(de.jplag.xml.parser.model.OnExit onExit);

    protected abstract void visitExecutableContent(ExecutableContent content);

    protected abstract void visitSimpleExecutableContent(SimpleExecutableContent content);

    protected abstract void visitTransition(de.jplag.xml.parser.model.Transition transition);

}
