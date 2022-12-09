package de.jplag.statecharts.parser;

import de.jplag.statecharts.parser.model.*;
import de.jplag.statecharts.parser.model.executable_content.Cancel;
import de.jplag.statecharts.parser.model.executable_content.ExecutableContent;
import de.jplag.statecharts.parser.model.executable_content.Send;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static de.jplag.statecharts.StatechartTokenType.*;

/**
 * Visits a metamodel containment tree and extracts the relevant token.
 *
 * @author Timur Saglam
 */
public class ImprovedStatechartTokenGenerator extends SimpleStatechartTokenGenerator {

    /**
     * Creates the visitor.
     *
     * @param adapter is the parser adapter which receives the generated tokens.
     */
    public ImprovedStatechartTokenGenerator(ScxmlParserAdapter adapter) {
        super(adapter);
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
        if (state.isRegion()) {
            adapter.addToken(REGION, state);
        } else {
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
        }

        int timedTransitionsCount = state.getTimedTransitionsCount();
        for (int i = 0; i < timedTransitionsCount; i++) {
            adapter.addToken(TIMED_TRANSITION, state);
        }

        int totalTransitionsCount = state.transitions().size();
        for (int i = 0; i < totalTransitionsCount - timedTransitionsCount; i++) {
            adapter.addToken(TRANSITION, state);
        }

        // TODO: use single class for OnEntry and OnExit
        for (OnEntry onEntry : state.onEntries()) {
            visitOnEntry(onEntry);
        }
        for (OnExit onExit : state.onExits()) {
            visitOnExit(onExit);
        }
    }

    @Override
    public void visitTransition(Transition transition) {
        // Transition tokens are already extracted in visitState
    }
}
