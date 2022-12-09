package de.jplag.statecharts.parser.model;

public record Transition(String target, String event, String cond) implements StatechartElement {

    private boolean timed = false;

    public Transition(String target, String event) {
        this(target, event, null);
    }

    public Transition(String target) {
        this(target, null, null);
    }

    public boolean isInitial() {
        return target != null && event == null && cond == null;
    }

    public boolean isTimed() {
        return timed;
    }

    public boolean setTimed() {
        this.timed = true;
    }

    @Override
    public String toString() {
        assert !isInitial();
        String prefix = isTimed() ? "Timed t" : "T";
        return String.format(
            "%sransition (-> %s) (event='%s', cond='%s')",
            prefix, target, event != null ? cond : "" : event,
            cond != null ? cond : ""
        );
    }
}
