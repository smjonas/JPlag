package de.jplag.scxml.parser;

import de.jplag.scxml.StatechartTokenType;
import de.jplag.scxml.parser.model.State;
import de.jplag.scxml.parser.model.Statechart;
import de.jplag.scxml.parser.model.Transition;
import de.jplag.scxml.parser.model.executable_content.*;
import de.jplag.scxml.util.AbstractStatechartVisitor;

import java.util.List;


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

    @Override
    public void visitState(State state) {
        adapter.addToken(StatechartTokenType.STATE, state);
        visitActions(state.actions());

        depth++;
        for (Transition transition : state.transitions()) {
            visitTransition(transition);
        }

        for (State substate : state.substates()) {
            visitState(substate);
        }
        depth--;
        adapter.addToken(StatechartTokenType.STATE_END, state);
    }

    @Override
    public void visitActions(List<Action> actions) {
        for (Action action : actions) {
            adapter.addToken(action.type() == Action.Type.ON_ENTRY ? StatechartTokenType.ON_ENTRY : StatechartTokenType.ON_EXIT, action);
            depth++;
            for (ExecutableContent content : action.contents()) {
                visitExecutableContent(content);
            }
            depth--;
            adapter.addToken(StatechartTokenType.ACTION_END);
        }
    }

    @Override
    public void visitTransition(Transition transition) {
        adapter.addToken(StatechartTokenType.TRANSITION, transition);
        depth++;
        for (ExecutableContent content : transition.contents()) {
            visitExecutableContent(content);
        }
        depth--;
        adapter.addToken(StatechartTokenType.TRANSITION_END);
    }

    @Override
    public void visitIf(If if_) {
        adapter.addToken(StatechartTokenType.IF, if_);
        for (ExecutableContent content : if_.contents()) {
            visitExecutableContent(content);
        }
        for (ExecutableContent content : if_.elseIfs()) {
            visitExecutableContent(content);
        }
        if (if_.else_() != null) {
            adapter.addToken(StatechartTokenType.ELSE, if_.else_());
        }
        adapter.addToken(StatechartTokenType.IF_END, if_);
    }

    @Override
    public void visitExecutableContent(ExecutableContent content) {
        if (content instanceof If if_) {
            visitIf(if_);
            return;
        }

        StatechartTokenType type = null;
        if (content instanceof Assignment) {
            type = StatechartTokenType.ASSIGNMENT;
        } else if (content instanceof Script) {
            type = StatechartTokenType.SCRIPT;
        } else if (content instanceof Send) {
            type = StatechartTokenType.SEND;
        } else if (content instanceof Cancel) {
            type = StatechartTokenType.CANCEL;
        }
        adapter.addToken(type, content);
    }

    @Override
    public void visitSimpleExecutableContent(SimpleExecutableContent content) {
        StatechartTokenType type = switch (content.type()) {
            case RAISE -> StatechartTokenType.RAISE;
            case ELSE -> StatechartTokenType.ELSE;
            case FOREACH -> StatechartTokenType.FOREACH;
            case LOG -> StatechartTokenType.LOG;
        };
        adapter.addToken(type, content);
    }

}
