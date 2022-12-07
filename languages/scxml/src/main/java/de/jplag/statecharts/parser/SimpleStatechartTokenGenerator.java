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
    private final ScxmlParserAdapter adapter;

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
    }

    @Override
    public void visitState(State state) {
        boolean isSimpleState = !state.initial() && !state.parallel();
        if (isSimpleState) {
            adapter.addToken(STATE, state);
        } else {
            if (state.initial()) {
                adapter.addToken(INITIAL_STATE, state);
            }
            if (state.parallel()) {
                adapter.addToken(PARALLEL_STATE, state);
            }
        }

        if (state.transitions() != null) {
            for (Transition transition : state.transitions()) {
                visitTransition(transition);
            }
        }

        if (state.onEntries() != null) {
            for (OnEntry onEntry : state.onEntries()) {
                visitOnEntry(onEntry);
            }
        }

        if (state.onExits() != null) {
            for (OnExit onExit : state.onExits()) {
                visitOnExit(onExit);
            }
        }
    }

    @Override
    public void visitOnEntry(OnEntry onEntry) {
        if (onEntry == null) {
            return;
        }
        for (ExecutableContent content : onEntry.contents()) {
            visitExecutableContent(content);
        }
    }

    @Override
    public void visitOnExit(OnExit onExit) {
        if (onExit == null) {
            return;
        }
        for (ExecutableContent content : onExit.contents()) {
            visitExecutableContent(content);
        }
    }

    @Override
    public void visitTransition(Transition transition) {
        adapter.addToken(TRANSITION, transition);
    }

    @Override
    public void visitExecutableContent(ExecutableContent content) {
        StatechartTokenType type = null;
        if (content instanceof SimpleExecutableContent simpleContent) {
            visitSimpleExecutableContent(simpleContent);
            return;
        }

        if (content instanceof Assign) {
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
            case IF -> IF;
            case ELSEIF -> ELSE_IF;
            case ELSE -> ELSE;
            case FOREACH -> FOREACH;
            case LOG -> LOG;
        };
        adapter.addToken(type, content);
    }
}
