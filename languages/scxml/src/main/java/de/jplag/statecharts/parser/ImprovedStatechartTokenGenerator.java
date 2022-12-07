package de.jplag.statecharts.parser;

import de.jplag.statecharts.util.AbstractStatechartVisitor;

/**
 * Visits a metamodel containment tree and extracts the relevant token.
 *
 * @author Timur Saglam
 */
public class ImprovedStatechartTokenGenerator extends SimpleStatechartVisitor {
    private final ScxmlParserAdapter adapter;

    /**
     * Creates the visitor.
     *
     * @param adapter is the parser adapter which receives the generated tokens.
     */
    public ImprovedStatechartTokenGenerator(ScxmlParserAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void visitStatechart(Statechart statechart) {
        parser.addToken(STATECHART);
        for (State state : statechart.states()) {
            visitState();
        }
    }

    /**
    * Checks if the transition is timed:
    * To model a timed transition, Yakindu adds onentry.send, onexit.cancel
    * and transition element with matching IDs.
    **/
    private int getTimedTransitionsCount(State state) {
        if (state.transitions() == null && state.onExits() == null && state.onEntries() == null) {
            return 0;
        }
        List<List<ExecutableContent>> onExitContents = state.onExits().stream().map(e -> e.contents());
        List<Cancel> onExitCancellations = onExitContents.flatMap(List::stream).filter(c -> c instanceof Cancel).map(c -> (Cancel) c).collect(Collectors.toList());
        if (onExitCancellations.isEmpty()) {
            return 0;
        }

        int timedTransitionsCount = 0;
        List<String> cancelSendIds = onExitCancellations.map(c -> c.sendid());
        for (String id : cancelSendIds) {
            // First check if there is a matching transition for the sendid
            if (!state.transitions().stream().filter(c -> c.event().equals(id)).isEmpty()) {
                // Then check if there is also a matching send element in onentry
                List<List<ExecutableContent>> onEntryContents = state.onEntries().stream().map(e -> e.contents());
                Stream<> onEntrySendEvents = onEntryContents.flatMap(List::stream).filter(c -> c instanceof Send).map(s -> ((Send) s).event());
                if (onEntrySendEvents.anyMatch(s -> s.equals(id))) {
                    timedTransitionsCount++;
                }
            }
        }
        return timedTransitionsCount;
    }

    @Override
    public void visitState(State state) {
        // TODO: make onEntry and onExit a list
        if (state.initial()) {
            parser.addToken(INITIAL_STATE);
        }
        if (state.parallel()) {
            parser.addToken(PARALLEL_STATE);
        }
        int timedTransitionsCount = getTimedTransitionsCount(state);
        for (int i = 0; i < timedTransitionsCount; i++) {
            parser.addToken(TIMED_TRANSITION);
        }

        int totalTransitionsCount = state.transitions().size();
        for (int i = 0; i < totalTransitionsCount - timedTransitionsCount; i++) {
            parser.addToken(TRANSITION);
        }

        visitOnEntry(state.onEntry());
        visitOnExit(state.onExit());
    }

    @Override
    public void visitTransition(Transition transition) {
        // Transition tokens are already extracted in visitState
    }
}
