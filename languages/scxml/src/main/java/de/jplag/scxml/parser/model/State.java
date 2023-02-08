package de.jplag.scxml.parser.model;

import de.jplag.scxml.parser.model.executable_content.Action;
import de.jplag.scxml.parser.model.executable_content.Cancel;
import de.jplag.scxml.parser.model.executable_content.ExecutableContent;
import de.jplag.scxml.parser.model.executable_content.Send;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public record State(String id, List<Transition> transitions, List<State> substates, List<Action> actions,
                    boolean initial, boolean parallel) implements StatechartElement {

    public State(String id, List<Transition> transitions, List<State> substates, List<Action> actions, boolean initial, boolean parallel) {
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

    public static Builder builder(String id) {
        return new Builder(id);
    }

    public boolean isRegion() {
        return !substates.isEmpty();
    }

    public boolean isSimple() {
        return !initial && !parallel;
    }

    private Stream<Action> onEntries() {
        return actions.stream().filter(a -> a.type() == Action.Type.ON_ENTRY);
    }

    private Stream<Action> onExits() {
        return actions.stream().filter(a -> a.type() == Action.Type.ON_EXIT);
    }

    private List<Send> getOnEntrySends() {
        Stream<List<ExecutableContent>> onEntryContents = this.onEntries().map(Action::contents);
        return onEntryContents.flatMap(List::stream).filter(c -> c instanceof Send).map(s -> (Send) s).toList();
    }

    private void removeTimedTransitionElements(Action onEntry, Send send, Action onExit, Cancel cancel, Transition transition) {
        List<ExecutableContent> filteredContents = onEntry.contents().stream().filter(c -> !(c instanceof Send && c.equals(send))).toList();
        if (filteredContents.isEmpty()) {
            // Remove onEntry entirely if it is now empty
            actions.remove(onEntry);
        } else {
            // Only remove the matching onEntry.send
            Action filteredOnEntry = new Action(Action.Type.ON_ENTRY, filteredContents);
            actions.set(actions.indexOf(onEntry), filteredOnEntry);
        }

        // Do something similar for onExit
        filteredContents = onExit.contents().stream().filter(c -> !(c instanceof Cancel && c.equals(cancel))).toList();
        if (filteredContents.isEmpty()) {
            actions.remove(onExit);
        } else {
            Action filteredOnExit = new Action(Action.Type.ON_EXIT, filteredContents);
            actions.set(actions.indexOf(onExit), filteredOnExit);
        }

    }

    /**
     * Sets the timed attribute of each transition of this state that is timed.
     * To model a timed transition, Yakindu adds onentry.send, onexit.cancel
     * and transition elements with matching IDs.
     * These elements will be removed if they are part of a timed transition.
     **/
    private void updateTimedTransitions() {
        if (this.transitions().isEmpty() || this.actions().isEmpty()) {
            return;
        }
        List<Send> onEntrySends = getOnEntrySends();

        for (Action onExit : onExits().toList()) {
            for (Cancel cancel : onExit.contents().stream().filter(c -> c instanceof Cancel).map(c -> (Cancel) c).toList()) {
                String sendId = cancel.sendid();
                // First check if there is a matching transition for the sendid
                for (Transition transition : transitions) {
                    boolean foundTimedTransition = false;
                    if (transition.event() != null && transition.event().equals(sendId)) {
                        // Then check if there is also a matching send element in <onentry>
                        for (Action onEntry : onEntries().toList()) {
                            for (Send send : onEntrySends) {
                                if (send.event().equals(sendId)) {
                                    foundTimedTransition = true;
                                    // Finally, replace the transition
                                    removeTimedTransitionElements(onEntry, send, onExit, cancel, transition);
                                }
                            }
                        }
                    }
                    if (foundTimedTransition) {
                        transitions.set(transitions.indexOf(transition), Transition.makeTimed(transition));
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return String.format("%s: %s {", id, isRegion() ? "Region" : "State");
    }

}
