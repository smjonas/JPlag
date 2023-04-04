package de.jplag.scxml.parser;

import de.jplag.scxml.ScxmlTokenType;
import de.jplag.scxml.parser.model.State;
import de.jplag.scxml.parser.model.Statechart;
import de.jplag.scxml.parser.model.StatechartElement;
import de.jplag.scxml.parser.model.Transition;
import de.jplag.scxml.parser.model.executable_content.*;
import de.jplag.scxml.sorting.SimpleSorter;
import de.jplag.scxml.sorting.Sorter;
import de.jplag.scxml.util.AbstractStatechartVisitor;
import de.jplag.scxml.util.PeekAdapter;

import java.util.List;

import static de.jplag.scxml.ScxmlTokenType.*;

public class SimpleScxmlTokenGenerator extends AbstractStatechartVisitor {

    protected Sorter sorter;

    /**
     * Creates the visitor.
     *
     * @param adapter is the parser adapter which receives the generated tokens.
     */
    public SimpleScxmlTokenGenerator(ScxmlParserAdapter adapter) {
        super(adapter);
        this.sorter = new SimpleSorter(this);
    }

    public List<Integer> peekTokens(StatechartElement element) {
        ScxmlParserAdapter prevAdapter = this.adapter;
        PeekAdapter peekAdapter = new PeekAdapter();
        // Switch out the main adapter for the peek adapter
        // so that the main token stream is not affected
        this.adapter = peekAdapter;
        visit(element);
        this.adapter = prevAdapter;
        return peekAdapter.getTokenTypes();
    }

    @Override
    public void visitStatechart(Statechart statechart) {
        for (State state : sorter.sort(statechart.states()) ){
            visitState(state);
        }
    }

    protected void visitStateContents(State state) {
        visitActions(state.actions());
        for (Transition transition : sorter.sort(state.transitions())) {
            visitTransition(transition);
        }
        for (State substate : sorter.sort(state.substates())) {
            visitState(substate);
        }
        depth--;
        adapter.addToken(STATE_END, state);
    }

    @Override
    public void visitState(State state) {
        adapter.addToken(STATE, state);
        depth++;
        visitStateContents(state);
    }

    @Override
    public void visitActions(List<Action> actions) {
        // Group actions by their type
        List<Action> onEntries = actions.stream().filter(a -> a.type() == Action.Type.ON_ENTRY).toList();
        List<Action> onExits = actions.stream().filter(a -> a.type() == Action.Type.ON_EXIT).toList();
        visitActions(onEntries, ON_ENTRY);
        visitActions(onExits, ON_EXIT);
    }

    private void visitActions(List<Action> actions, ScxmlTokenType tokenType) {
        if (!actions.isEmpty()) {
            // Only extract a single ENTRY / EXIT token even if the state contains multiple.
            // Functionally, this makes no difference.
            adapter.addToken(tokenType, null);
            List<ExecutableContent> actionContents = actions.stream().flatMap(a -> a.contents().stream()).toList();
            depth++;
            // Do not sort executable content because the order is important
            for (ExecutableContent content : actionContents) {
                visitExecutableContent(content);
            }
            depth--;
            adapter.addToken(ACTION_END, null);
        }
    }

    @Override
    public void visitTransition(Transition transition) {
        adapter.addToken(TRANSITION, transition);
        depth++;
        // Do not sort executable content because the order is important
        for (ExecutableContent content : transition.contents()) {
            visitExecutableContent(content);
        }
        depth--;
        adapter.addToken(TRANSITION_END);
    }

    @Override
    public void visitIf(If if_) {
        adapter.addToken(IF, if_);
        for (ExecutableContent content : if_.contents()) {
            visitExecutableContent(content);
        }
        for (ExecutableContent content : if_.elseIfs()) {
            visitExecutableContent(content);
        }
        if (if_.else_() != null) {
            adapter.addToken(ELSE, if_.else_());
        }
        adapter.addToken(IF_END, if_);
    }

    @Override
    public void visitExecutableContent(ExecutableContent content) {
        if (content instanceof If if_) {
            visitIf(if_);
            return;
        }

        ScxmlTokenType type = null;
        if (content instanceof Assignment) {
            type = ASSIGNMENT;
        } else if (content instanceof Script) {
            type = SCRIPT;
        } else if (content instanceof Send) {
            type = SEND;
        } else if (content instanceof Cancel) {
            type = CANCEL;
        }
        adapter.addToken(type, content);
    }

    @Override
    public void visitSimpleExecutableContent(SimpleExecutableContent content) {
        ScxmlTokenType type = switch (content.type()) {
            case RAISE -> RAISE;
            case ELSE -> ELSE;
            case FOREACH -> FOREACH;
            // Don't extract a token for log elements
            case LOG -> null;
        };
        if (type != null) {
            adapter.addToken(type, content);
        }
    }

}
