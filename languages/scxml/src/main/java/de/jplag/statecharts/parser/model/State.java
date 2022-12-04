package de.jplag.statecharts.parser.model;

import java.util.List;

public class State extends StatechartElement {

    public final boolean initial;
    public final OnEntry onEntry;
    public final OnExit onExit;
    public final List<Transition> transitions;

    public State(boolean initial, OnEntry onEntry, OnExit onExit, List<Transition> transitions) {
        this.initial = initial;
        this.onEntry = onEntry;
        this.onExit = onExit;
        this.transitions = transitions;
    }
}
