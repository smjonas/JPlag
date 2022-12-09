package de.jplag.statecharts.parser.model;

import java.util.Arrays;
import java.util.List;

public record State(String id, List<Transition> transitions, List<State> substates, List<OnEntry> onEntries,
                    List<OnExit> onExits, boolean initial, boolean parallel) implements StatechartElement {

    public State(String id, List<Transition> transitions, List<State> substates, List<OnEntry> onEntries, List<OnExit> onExits, boolean initial, boolean parallel) {
        this.id = id;
        this.transitions = transitions != null && transitions.isEmpty() ? null : transitions;
        this.substates = substates != null && substates.isEmpty() ? null : substates;
        this.onEntries = onEntries != null && onEntries.isEmpty() ? null : onEntries;
        this.onExits = onExits != null && onExits.isEmpty() ? null : onExits;
        this.initial = initial;
        this.parallel = parallel;
    }

    public State(String id) {
        this(id, null, null, null, null, false, false);
    }

    public boolean isRegion() {
        return substates != null && !substates.isEmpty();
    }

    /**
     * Returns the number of timed transitions within the state element.
     * To model a timed transition, Yakindu adds onentry.send, onexit.cancel
     * and transition elements with matching IDs.
     **/
    public int getTimedTransitionsCount() {
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
    public String toString() {
        return id + ": State";
    }

    public static Builder builder(String id) {
        return new Builder(id);
    }

    public static class Builder {
        private final String id;
        private List<Transition> transitions;
        private List<State> substates;
        private List<OnEntry> onEntries;
        private List<OnExit> onExits;
        private boolean initial;
        private boolean parallel;

        public Builder(String id) {
            this.id = id;
        }

        public Builder setParallel() {
            parallel = true;
            return this;
        }

        public Builder setInitial() {
            initial = true;
            return this;
        }

        public Builder addTransitions(Transition... transitions) {
            this.transitions = Arrays.asList(transitions);
            return this;
        }

        public Builder addSubstates(State... substates) {
            this.substates = Arrays.asList(substates);
            return this;
        }

        public Builder addOnEntry(OnEntry onEntry) {
            this.onEntries = List.of(onEntry);
            return this;
        }

        public Builder addOnExit(OnExit onExit) {
            this.onExits = List.of(onExit);
            return this;
        }

        public State build() {
            return new State(id, transitions, substates, onEntries, onExits, initial, parallel);
        }
    }
}
