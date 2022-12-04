package de.jplag.statecharts.parser.model;

import java.util.List;

public record Statechart(List<State> states) implements StatechartElement {}