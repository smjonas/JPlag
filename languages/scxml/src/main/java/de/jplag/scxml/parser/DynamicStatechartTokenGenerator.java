package de.jplag.scxml.parser;

import de.jplag.scxml.parser.model.State;
import de.jplag.scxml.parser.model.Transition;
import de.jplag.scxml.parser.model.executable_content.ExecutableContent;

import static de.jplag.scxml.ScxmlTokenType.*;

/**
 * Visits a metamodel containment tree and extracts the relevant tokens.
 *
 * @author Jonas Strittmatter
 */
public class DynamicStatechartTokenGenerator extends SimpleStatechartTokenGenerator {

    /**
     * Creates the visitor.
     *
     * @param adapter is the parser adapter which receives the generated tokens.
     */
    public DynamicStatechartTokenGenerator(ScxmlParserAdapter adapter) {
        super(adapter);
    }

    protected void visitStateAttributes(State state) {
        if (state.initial()) {
            adapter.addToken(INITIAL_STATE, state);
        }
        if (state.parallel()) {
            adapter.addToken(PARALLEL_STATE, state);
        }
    }

    @Override
    public void visitState(State state) {
        adapter.addToken(state.isRegion() ? REGION : STATE, state);
        depth++;
        visitStateAttributes(state);
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
    public void visitTransition(Transition transition) {
        if (transition.isTimed()) {
            adapter.addToken(TIMED_TRANSITION, transition);
        } else if (transition.isGuarded()) {
            adapter.addToken(GUARDED_TRANSITION, transition);
        } else {
            adapter.addToken(TRANSITION, transition);
        }

        depth++;
        for (ExecutableContent content : transition.contents()) {
            visitExecutableContent(content);
        }
        depth--;
        adapter.addToken(TRANSITION_END, transition);
    }
}
