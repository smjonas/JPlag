package de.jplag.statecharts.parser.model;

import java.util.List;

public class State extends StatechartElement {

    public List<Transition> transitions;
    public final boolean initial;

    public State(boolean initial) {
        this.initial = initial;
    }
}
