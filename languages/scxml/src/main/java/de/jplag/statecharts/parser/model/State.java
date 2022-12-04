package de.jplag.statecharts.parser.model;

import java.util.List;

public record State(String id, List<Transition> transitions, List<State> substates, boolean initial, OnEntry onEntry,
                    OnExit onExit) implements StatechartElement {

    public State(String id, List<Transition> transitions, List<State> substates, boolean initial, OnEntry onEntry, OnExit onExit) {
        this.id = id;
        this.transitions = transitions != null && transitions.isEmpty() ? null : transitions;
        this.substates = substates != null && substates.isEmpty() ? null : substates;
        this.initial = initial;
        this.onEntry = onEntry;
        this.onExit = onExit;
    }

    public State(String id, List<Transition> transitions, OnEntry onEntry, OnExit onExit) {
        this(id, transitions, null, false, onEntry, onExit);
    }

    public State(String id, List<Transition> transitions, OnEntry onEntry) {
        this(id, transitions, onEntry, null);
    }

    public State(String id, List<Transition> transitions, boolean initial) {
        this(id, transitions, null, initial, null, null);
    }

    public State(String id, List<State> substates) {
        this(id, null, substates, false, null, null);
    }

    public boolean isRegion() {
        return substates != null && !substates.isEmpty();
    }
}
