package de.jplag.statecharts.parser.model;

import de.jplag.statecharts.parser.model.executable_content.ExecutableContent;

public record OnExit(ExecutableContent... contents) implements StatechartElement {

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (ExecutableContent content : contents) {
            b.append(content.getClass()).append(", ");
        }
        return "OnEntry{" +
                "contents=" + b +
                '}';
    }
}
