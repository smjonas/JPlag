package de.jplag.yakindu.util;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.yakindu.base.types.Declaration;
import org.yakindu.base.types.Property;
import org.yakindu.base.types.impl.DeclarationImpl;
import org.yakindu.sct.model.sgraph.*;
import org.yakindu.sct.model.sgraph.impl.*;

import java.net.ProtocolException;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

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

    protected void visit(EObject object) {
        if (object instanceof Vertex vertex) {
            visitVertex(vertex);
            return;
        }
        Map<Class<? extends EObject>, Consumer<EObject>> visitorMap = Map.of(
            StatechartImpl.class, e -> visitStatechart((Statechart) e),
            RegionImpl.class, e -> visitRegion((Region) e),
            DeclarationImpl.class, e -> visitDeclaration((Declaration) e),
            ReactionImpl.class, e -> visitReaction((Reaction) e),
            TransitionImpl.class, e -> visitTransition((Transition) e)
        );
        visitorMap.get(object.getClass()).accept(object);
    }

    protected void visitCompositeElement(CompositeElement compositeElement) {
        if (compositeElement instanceof Statechart) {
            visit((Statechart) compositeElement);
        } else if (compositeElement instanceof State) {
            visitState((State) compositeElement);
        }
    }

    public abstract void visitStatechart(Statechart statechart);

    protected abstract void visitRegion(Region region);

    protected abstract void visitDeclaration(Declaration declaration);

    public abstract void visitState(State state);

    protected abstract void visitVertex(Vertex vertex);

    public abstract void visitReaction(Reaction reaction);

    protected abstract void visitTransition(Transition transition);

    protected abstract void visitChoice(Choice choice);

    protected abstract void visitEntry(Entry entry);

}
