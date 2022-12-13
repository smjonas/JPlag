package de.jplag.statecharts.parser.model;

public record Transition(String target, String event, String cond, boolean timed) implements StatechartElement {

    public static Transition makeTimed(Transition transition) {
        return new Transition(transition.target, transition.event, transition.cond, true);
    }

    public Transition(String target, String event, String cond) {
        this(target, event, cond, false);
    }

    public Transition(String target, String event) {
        this(target, event, null);
    }

    public Transition(String target) {
        this(target, null);
    }

    public boolean isInitial() {
        return target != null && event == null && cond == null;
    }

    public boolean isTimed() {
        return timed;
    }

    @Override
    public String toString() {
        assert !isInitial();
        String prefix = isTimed() ? "Timed t" : "T";
        return String.format(
            "%sransition (-> %s) (event='%s', cond='%s')",
            prefix, target, event != null ? (cond != null ? event : "") : "",
            cond != null ? cond : ""
        );
    }
}
