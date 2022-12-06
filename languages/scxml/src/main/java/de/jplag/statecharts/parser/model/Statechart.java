package de.jplag.statecharts.parser.model;

import java.util.Arrays;
import java.util.List;

public record Statechart(List<State> states) implements StatechartElement {

    @Override
    public String toString() {
        return "\nStatechart{" +
                "states=" + states.toString().replace("], ", "],\n") +
                "}\n";
    }
}
