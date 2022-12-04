package de.jplag.statecharts.parser.model;

public class Transition extends StatechartElement {

    public final String target;
    public final String cond;
    public final String event;

    public Transition(String targetState, String cond, String event) {
        this.target = targetState;
        this.cond = cond;
        this.event = event;
    }

    public boolean isInitial() {
        return target != null && cond == null && event == null;
    }
}
