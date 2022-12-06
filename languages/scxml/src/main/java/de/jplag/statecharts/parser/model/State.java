package de.jplag.statecharts.parser.model;

import java.util.List;

public record State(String id, List<Transition> transitions, List<State> substates, OnEntry onEntry,
                    OnExit onExit, boolean initial, boolean parallel) implements StatechartElement {

    public State(String id, List<Transition> transitions, List<State> substates, boolean initial, OnEntry onEntry, OnExit onExit) {
        this.id = id;
        this.transitions = transitions != null && transitions.isEmpty() ? null : transitions;
        this.substates = substates != null && substates.isEmpty() ? null : substates;
        this.initial = initial;
        this.parallel = parallel;
        this.onEntry = onEntry;
        this.onExit = onExit;
    }

    public State(String id) {
        this.id = id;
    }

    public State setParallel() {
        this.parallel = true;
        return this;
    }

    public State setInitial() {
        this.initial = true;
        return this;
    }

    public State addSubstates(State... substates) {
        this.substates = substates;
        return this;
    }

    public State addTransitions(Transition... transitions) {
        this.transitions = transitions;
        return this;
    }

    public State addOnEntry(OnEntry onEntry) {
        this.onEntry = onEntry;
        return this;
    }

    public State addOnExit(OnExit onExit) {
        this.onExit = onExit;
        return this;
    }

    public boolean isRegion() {
        return substates != null && !substates.isEmpty();
    }
}
