package de.jplag.statecharts.parser.model;

import java.util.List;

public record Statechart(State... states) implements StatechartElement {}
