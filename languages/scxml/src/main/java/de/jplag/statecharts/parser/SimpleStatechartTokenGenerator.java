package de.jplag.statecharts.parser;

import de.jplag.statecharts.StatechartTokenType;
import de.jplag.statecharts.parser.model.*;
import de.jplag.statecharts.parser.model.executable_content.*;
import de.jplag.statecharts.util.AbstractStatechartVisitor;

import static de.jplag.statecharts.StatechartTokenType.*;


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
        adapter.addToken(STATECHART, statechart);
        for (State state : statechart.states()) {
            visitState(state);
        }
        adapter.addToken(STATECHART_END, statechart);
    }

    @Override
    public void visitState(State state) {
        adapter.addToken(STATE, state);
        for (Action action : state.actions()) {
            visitAction(action);
        }

        for (Transition transition : state.transitions()) {
            visitTransition(transition);
        }

        for (State substate : state.substates()) {
            visitState(substate);
        }
        adapter.addToken(STATE_END, state);
    }

    @Override
    public void visitAction(Action action) {
        if (action == null) {
            return;
        }
        adapter.addToken(action.type() == Action.Type.ON_ENTRY ? ON_ENTRY : ON_EXIT, action);
        for (ExecutableContent content : action.contents()) {
            visitExecutableContent(content);
        }
        adapter.addToken(ACTION_END, action);
    }

    @Override
    public void visitTransition(Transition transition) {
        adapter.addToken(TRANSITION, transition);
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

        StatechartTokenType type = null;
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
        StatechartTokenType type = switch (content.type()) {
            case RAISE -> RAISE;
            case ELSE -> ELSE;
            case FOREACH -> FOREACH;
            case LOG -> LOG;
        };
        adapter.addToken(type, content);
    }

}
