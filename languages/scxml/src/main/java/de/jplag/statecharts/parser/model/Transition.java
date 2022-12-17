package de.jplag.statecharts.parser.model;

import de.jplag.statecharts.parser.model.executable_content.ExecutableContent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record Transition(String target, String event, String cond, List<ExecutableContent> contents, boolean timed) implements StatechartElement {

    public static Transition makeTimed(Transition transition) {
        return new Transition(transition.target, null, transition.cond, transition.contents, true);
    }

    public Transition(String target, String event, List<ExecutableContent> contents) {
        this(target, event, null, contents, false);
    }

    public Transition(String target, List<ExecutableContent> contents) {
        this(target, null, contents);
    }

    public Transition(String target, String event, String cond) {
        this(target, event, cond, new ArrayList<>(), false);
    }

    public Transition(String target, String event) {
        this(target, event, new ArrayList<>());
    }

    public Transition(String target) {
        this(target, (String) null);
    }

    public boolean isInitial() {
        return target != null && event == null && cond == null;
    }

    public boolean isTimed() {
        return timed;
    }

    @Override
    public String toString() {
        // assert !isInitial();
        String prefix = isTimed() ? "Timed t" : "T";
        return String.format(
            "%sransition (-> %s) (event='%s', cond='%s'), %s",
            prefix, target, event != null ? (cond != null ? event : "") : "",
            cond != null ? cond : "", contents
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(target, event, cond, contents, timed);
    }
}
