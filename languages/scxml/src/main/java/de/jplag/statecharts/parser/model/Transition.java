package de.jplag.statecharts.parser.model;

public record Transition(String target, String event, String cond) implements StatechartElement {

    public Transition(String target, String event) {
        this(target, event, null);
    }

    public Transition(String target) {
        this(target, null, null);
    }

    public boolean isInitial() {
        return target != null && event == null && cond == null;
    }

    @Override
    public String toString() {
        assert !isInitial();
        return String.format(
            "Transition (-> %s) (event='%s', cond='%s')",
            target, event != null ? cond : "" : event,
            cond != null ? cond : ""
        );
    }
}
