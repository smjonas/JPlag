package de.jplag.statecharts.parser.model;

import java.util.List;

public class Statechart extends StatechartElement {

    public List<State> states;

    public Statechart(List<State> states) {
        this.states = states;
    }
}
