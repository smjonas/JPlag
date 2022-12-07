package de.jplag.xml.parser;

import de.jplag.statecharts.StatechartTokenType;
import de.jplag.xml.parser.model.OnExit;
import de.jplag.xml.parser.model.executable_content.Assign;
import de.jplag.xml.util.AbstractStatechartVisitor;

import static de.jplag.statecharts.StatechartTokenType.*;


/**
 * Visits a metamodel containment tree and extracts the relevant token.
 *
 * @author Timur Saglam
 */
public class SimpleStatechartTokenGenerator extends AbstractStatechartVisitor {
    private final de.jplag.xml.parser.ScxmlParserAdapter adapter;

    /**
     * Creates the visitor.
     *
     * @param adapter is the parser adapter which receives the generated tokens.
     */
    public SimpleStatechartTokenGenerator(ScxmlParserAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void visitStatechart(de.jplag.xml.parser.model.Statechart statechart) {
        adapter.addToken(STATECHART, statechart);
        for (de.jplag.xml.parser.model.State state : statechart.states()) {
            visitState(state);
        }
    }

    @Override
    public void visitState(de.jplag.xml.parser.model.State state) {
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
            for (de.jplag.xml.parser.model.Transition transition : state.transitions()) {
                visitTransition(transition);
            }
        }
        visitOnEntry(state.onEntry());
        visitOnExit(state.onExit());
    }

    @Override
    public void visitOnEntry(de.jplag.xml.parser.model.OnEntry onEntry) {
        if (onEntry == null) {
            return;
        }
        for (de.jplag.xml.parser.model.executable_content.ExecutableContent content : onEntry.contents()) {
            visitExecutableContent(content);
        }
    }

    @Override
    public void visitOnExit(OnExit onExit) {
        if (onExit == null) {
            return;
        }
        for (de.jplag.xml.parser.model.executable_content.ExecutableContent content : onExit.contents()) {
            visitExecutableContent(content);
        }
    }

    @Override
    public void visitTransition(de.jplag.xml.parser.model.Transition transition) {
        adapter.addToken(TRANSITION, transition);
    }

    @Override
    public void visitExecutableContent(de.jplag.xml.parser.model.executable_content.ExecutableContent content) {
        StatechartTokenType type = null;
        if (content instanceof de.jplag.xml.parser.model.executable_content.SimpleExecutableContent simpleContent) {
            visitSimpleExecutableContent(simpleContent);
            return;
        }

        if (content instanceof Assign) {
            type = ASSIGNMENT;
        } else if (content instanceof de.jplag.xml.parser.model.executable_content.Script) {
            type = SCRIPT;
        } else if (content instanceof de.jplag.xml.parser.model.executable_content.Send) {
            type = SEND;
        } else if (content instanceof de.jplag.xml.parser.model.executable_content.Cancel) {
            type = CANCEL;
        }
        adapter.addToken(type, content);
    }

    @Override
    public void visitSimpleExecutableContent(de.jplag.xml.parser.model.executable_content.SimpleExecutableContent content) {
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
