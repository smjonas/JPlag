package de.jplag.scxml.util;

import de.jplag.scxml.parser.ScxmlParserAdapter;
import de.jplag.scxml.parser.model.State;
import de.jplag.scxml.parser.model.Statechart;
import de.jplag.scxml.parser.model.StatechartElement;
import de.jplag.scxml.parser.model.Transition;
import de.jplag.scxml.parser.model.executable_content.Action;
import de.jplag.scxml.parser.model.executable_content.ExecutableContent;
import de.jplag.scxml.parser.model.executable_content.If;
import de.jplag.scxml.parser.model.executable_content.SimpleExecutableContent;

import java.util.Collections;
import java.util.List;

/**
 * Visitor for the containment tree of an EMF Metamodel.
 *
 * @author Timur Saglam
 */
public abstract class AbstractStatechartVisitor {

    protected int depth;
    protected ScxmlParserAdapter adapter;

    public AbstractStatechartVisitor(ScxmlParserAdapter adapter) {
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

    private List<Integer> peekTokens(StatechartElement element) {
        ScxmlParserAdapter prevAdapter = this.adapter;
        PeekAdapter peekAdapter = new PeekAdapter();
        // Switch out the main adapter for the peek adapter
        // so that the main token stream is not affected
        this.adapter = peekAdapter;
        visit(element);
        this.adapter = prevAdapter;
        return peekAdapter.getTokenTypes();
    }

    protected <T extends StatechartElement> List<T> sort(List<T> objects) {
        objects.sort((v1, v2) -> PeekAdapter.compareTokenTypeLists(peekTokens(v1), peekTokens(v2)));
        return objects;
    }

    public final void visit(StatechartElement element) {
        if (element instanceof Statechart statechart) {
            visitStatechart(statechart);
        } else if (element instanceof State state) {
            visitState(state);
        } else if (element instanceof SimpleExecutableContent simpleContent) {
            visitSimpleExecutableContent(simpleContent);
        } else if (element instanceof ExecutableContent content) {
            visitExecutableContent(content);
        }
    }

    protected abstract void visitStatechart(Statechart statechart);

    protected abstract void visitState(State state);

    protected abstract void visitActions(List<Action> actions);

    protected abstract void visitIf(If if_);

    protected abstract void visitExecutableContent(ExecutableContent content);

    protected abstract void visitSimpleExecutableContent(SimpleExecutableContent content);

    protected abstract void visitTransition(Transition transition);

}
