package de.jplag.statecharts.parser.model;

import de.jplag.statecharts.parser.model.executable_content.Cancel;
import de.jplag.statecharts.parser.model.executable_content.ExecutableContent;
import de.jplag.statecharts.parser.model.executable_content.Send;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

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
        updateTimedTransitions();
    }

    public State(String id) {
        this(id, null, null, null, null, false, false);
    }

    public boolean isRegion() {
        return substates != null && !substates.isEmpty();
    }

    public boolean isSimple() {
        return !initial && !parallel;
    }

    /**
     * Sets the timed attribute of each transition of this state that is timed.
     * To model a timed transition, Yakindu adds onentry.send, onexit.cancel
     * and transition elements with matching IDs.
     **/
    private void updateTimedTransitions() {
        if (this.transitions() == null || this.onExits() == null || this.onEntries() == null) {
            return;
        }
        int[] x = new int[]{1,2,3};
        Stream<ExecutableContent[]> onExitContents = this.onExits().stream().map(OnExit::contents);
        List<Cancel> onExitCancellations = onExitContents.flatMap(Arrays::stream).filter(c -> c instanceof Cancel).map(c -> (Cancel) c).toList();
        if (onExitCancellations.isEmpty()) {
            return;
        }

        Stream<String> cancelSendIds = onExitCancellations.stream().map(Cancel::sendid);
        for (String id : cancelSendIds.toList()) {
            // First check if there is a matching transition for the sendid
            for (Transition transition : this.transitions()) {
                if (transition.event().equals(id)) {
                    // Then check if there is also a matching send element in onentry
                    Stream<ExecutableContent[]> onEntryContents = this.onEntries().stream().map(OnEntry::contents);
                    Stream<String> onEntrySendEvents = onEntryContents.flatMap(Arrays::stream).filter(c -> c instanceof Send).map(s -> ((Send) s).event());
                    if (onEntrySendEvents.anyMatch(s -> s.equals(id))) {
                        transition.setTimed();
                    }
                }
            }
        }
    }

    public List<Transition> getTimedTransitions() {
        return transitions().stream().filter(Transition::isTimed).toList();
    }

    @Override
    public String toString() {
        return String.format("%s: %s", id, isRegion() ? "Region" : "State");
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
