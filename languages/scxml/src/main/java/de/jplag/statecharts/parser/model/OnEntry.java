package de.jplag.statecharts.parser.model;

import de.jplag.statecharts.parser.model.executable_content.ExecutableContent;

import java.util.List;

public record OnEntry(List<ExecutableContent> contents) implements StatechartElement {
}
