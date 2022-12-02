package de.jplag.statecharts.parser.model;

public class Transition {

    public final State target;

    public Transition(State target) {
        this.target = target;
    }
}
