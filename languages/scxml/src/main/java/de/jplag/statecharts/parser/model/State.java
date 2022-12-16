package de.jplag.statecharts.parser.model;

import de.jplag.statecharts.parser.model.executable_content.Action;
import de.jplag.statecharts.parser.model.executable_content.Cancel;
import de.jplag.statecharts.parser.model.executable_content.ExecutableContent;
import de.jplag.statecharts.parser.model.executable_content.Send;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static de.jplag.statecharts.parser.model.executable_content.Action.Type.ON_ENTRY;
import static de.jplag.statecharts.parser.model.executable_content.Action.Type.ON_EXIT;

public record State(String id, ArrayList<Transition> transitions, List<State> substates, List<Action> actions,
                    boolean initial, boolean parallel) implements StatechartElement {

    public State(String id, ArrayList<Transition> transitions, List<State> substates, List<Action> actions, boolean initial, boolean parallel) {
        this.id = id;
        assert transitions != null : "State.transitions must not be null";
        this.transitions = transitions;
        this.substates = substates;
        this.actions = actions;
        this.initial = initial;
        this.parallel = parallel;
        updateTimedTransitions();
    }

    public State(String id) {
        this(id, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false, false);
    }

    public boolean isRegion() {
        return !substates.isEmpty();
    }

    public boolean isSimple() {
        return !initial && !parallel;
    }

    private Stream<Action> onEntries() {
        return actions.stream().filter(a -> a.type() == ON_ENTRY);
    }

    private Stream<Action> onExits() {
        return actions.stream().filter(a -> a.type() == ON_EXIT);
    }

    /**
     * Sets the timed attribute of each transition of this state that is timed.
     * To model a timed transition, Yakindu adds onentry.send, onexit.cancel
     * and transition elements with matching IDs.
     **/
    private void updateTimedTransitions() {
        if (this.transitions().isEmpty() || this.actions().isEmpty()) {
            return;
        }
        Stream<ExecutableContent[]> onExitContents = this.onExits().map(Action::contents);
        List<Cancel> onExitCancellations = onExitContents.flatMap(Arrays::stream).filter(c -> c instanceof Cancel).map(c -> (Cancel) c).toList();
        if (onExitCancellations.isEmpty()) {
            return;
        }

//STATECHART, STATE, INITIAL_STATE, TRANSITION, STATE, TRANSITION, ASSIGNMENT, STATE, TRANSITION, ASSIGNMENT, STATE, TRANSITION, TRANSITION, SEND, CANCEL, FILE_END
//xxxxxxxxxx, xxxxx, xxxxxxxxxxxxx, xxxxxxxxxx, xxxxx, TRANSITION, ASSIGNMENT, STATE, TRANSITION, ASSIGNMENT, STATE, TRANSITION, TRANSITION, SEND, CANCEL, FILE_END , STATE, INITIAL_STATE, TRANSITION, STATE, TRANSITION, ASSIGNMENT, STATE, TRANSITION, ASSIGNMENT, STATE, TRANSITION, TRANSITION, SEND, CANCEL, FILE_END
//xxxxxxxxxx, xxxxx, xxxxxxxxxxxxx, xxxxxxxxxx, xxxxx, ON_ENTRY, ASSIGNMENT, TRANSITION, STATE, ON_ENTRY, ASSIGNMENT, TRANSITION, STATE, ON_ENTRY, SEND, ON_EXIT, CANCEL, TRANSITION, TRANSITION, FILE_END>

        Stream<String> cancelSendIds = onExitCancellations.stream().map(Cancel::sendid);
        for (String id : cancelSendIds.toList()) {
            // First check if there is a matching transition for the sendid
            for (int i = 0; i < transitions.size(); i++) {
                Transition transition = transitions.get(i);
                if (transition.event() != null && transition.event().equals(id)) {
                    // Then check if there is also a matching send element in onentry
                    Stream<ExecutableContent[]> onEntryContents = this.onEntries().map(Action::contents);
                    Stream<String> onEntrySendEvents = onEntryContents.flatMap(Arrays::stream).filter(c -> c instanceof Send).map(s -> ((Send) s).event());
                    if (onEntrySendEvents.anyMatch(s -> s.equals(id))) {
                        transitions.set(i, Transition.makeTimed(transition));
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
        return ("State{" +
                "id='" + id + '\'' +
                ", transitions=" + transitions +
                ", substates=" + substates +
                ", onEntries=" + onEntries() +
                ", onExits=" + onExits() +
                ", initial=" + initial +
                ", parallel=" + parallel +
                "}").replace("], ", "],\n");
    }

    // @Override
    // public String toString() {
    //     return String.format("%s: %s", id, isRegion() ? "Region" : "State");
    // }

    public static Builder builder(String id) {
        return new Builder(id);
    }

    public static class Builder {
        private final String id;
        private ArrayList<Transition> transitions = new ArrayList<>();
        private List<State> substates = new ArrayList<>();
        private List<Action> actions = new ArrayList<>();
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
            this.transitions = new ArrayList<>(List.of(transitions));
            return this;
        }

        public Builder addSubstates(State... substates) {
            this.substates = Arrays.asList(substates);
            return this;
        }

        public Builder addOnEntry(ExecutableContent... contents) {
            this.actions.add(new Action(ON_ENTRY, contents));
            return this;
        }

        public Builder addOnExit(ExecutableContent... contents) {
            this.actions.add(new Action(ON_EXIT, contents));
            return this;
        }

        public State build() {
            return new State(id, transitions, substates, actions, initial, parallel);
        }
    }
}
