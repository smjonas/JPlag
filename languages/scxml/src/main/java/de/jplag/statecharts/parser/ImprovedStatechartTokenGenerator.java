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

    /**
     * Checks if the transition is timed.
     * To model a timed transition, Yakindu adds onentry.send, onexit.cancel
     * and transition elements with matching IDs.
     **/
    private int getTimedTransitionsCount(State state) {
        if (state.transitions() == null || state.onExits() == null || state.onEntries() == null) {
            return 0;
        }
        int[] x = new int[]{1,2,3};
        Stream<ExecutableContent[]> onExitContents = state.onExits().stream().map(OnExit::contents);
        List<Cancel> onExitCancellations = onExitContents.flatMap(Arrays::stream).filter(c -> c instanceof Cancel).map(c -> (Cancel) c).toList();
        if (onExitCancellations.isEmpty()) {
            return 0;
        }

        int timedTransitionsCount = 0;
        Stream<String> cancelSendIds = onExitCancellations.stream().map(Cancel::sendid);
        for (String id : cancelSendIds.toList()) {
            // First check if there is a matching transition for the sendid
            if (state.transitions().stream().anyMatch(c -> c.event().equals(id))) {
                // Then check if there is also a matching send element in onentry
                Stream<ExecutableContent[]> onEntryContents = state.onEntries().stream().map(OnEntry::contents);
                Stream<String> onEntrySendEvents = onEntryContents.flatMap(Arrays::stream).filter(c -> c instanceof Send).map(s -> ((Send) s).event());
                if (onEntrySendEvents.anyMatch(s -> s.equals(id))) {
                    timedTransitionsCount++;
                }
            }
        }
        return timedTransitionsCount;
    }

    @Override
    public void visitState(State state) {
        if (state.initial()) {
            adapter.addToken(INITIAL_STATE, state);
        }
        if (state.parallel()) {
            adapter.addToken(PARALLEL_STATE, state);
        }
        int timedTransitionsCount = getTimedTransitionsCount(state);
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
