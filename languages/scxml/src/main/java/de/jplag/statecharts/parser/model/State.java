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

    public static Builder builder(String id) {
        return new Builder(id);
    }

    public boolean isRegion() {
        return substates != null && !substates.isEmpty();
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
