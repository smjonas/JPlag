package de.jplag.scxml.parser;

import de.jplag.scxml.ScxmlTokenType;
import de.jplag.scxml.parser.model.State;
import de.jplag.scxml.parser.model.Statechart;
import de.jplag.scxml.parser.model.Transition;
import de.jplag.scxml.parser.model.executable_content.*;
import de.jplag.scxml.util.AbstractStatechartVisitor;

import java.util.List;

import static de.jplag.scxml.ScxmlTokenType.*;


/**
 * Visits a metamodel containment tree and extracts the relevant token.
 *
 * @author Timur Saglam
 */
public class SimpleStatechartTokenGenerator extends AbstractStatechartVisitor {
    protected final ScxmlParserAdapter adapter;

    /**
     * Creates the visitor.
     *
     * @param adapter is the parser adapter which receives the generated tokens.
     */
    public SimpleStatechartTokenGenerator(ScxmlParserAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void visitStatechart(Statechart statechart) {
        for (State state : statechart.states()) {
            visitState(state);
        }
    }

    protected void visitStateContents(State state) {
        visitActions(state.actions());
        for (Transition transition : state.transitions()) {
            visitTransition(transition);
        }
        for (State substate : state.substates()) {
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
        for (Action action : actions) {
            adapter.addToken(action.type() == Action.Type.ON_ENTRY ? ON_ENTRY : ON_EXIT, action);
            depth++;
            for (ExecutableContent content : action.contents()) {
                visitExecutableContent(content);
            }
            depth--;
            adapter.addToken(ACTION_END);
        }
    }

    @Override
    public void visitTransition(Transition transition) {
        adapter.addToken(TRANSITION, transition);
        depth++;
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
