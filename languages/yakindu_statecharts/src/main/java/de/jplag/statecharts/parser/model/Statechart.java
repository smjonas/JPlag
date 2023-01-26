package de.jplag.statecharts.parser.model;

import java.util.List;

public record Statechart(String name, List<State> states) implements StatechartElement {

     @Override
     public String toString() {
         return String.format("%s: Statechart {");
     }
}
