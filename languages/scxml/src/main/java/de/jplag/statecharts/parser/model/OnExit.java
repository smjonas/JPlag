package de.jplag.statecharts.parser.model;

import de.jplag.statecharts.parser.model.executable_content.ExecutableContent;

public record OnExit(ExecutableContent... contents) implements StatechartElement {

    @Override
    public String toString() {
        String c = "";
        for (ExecutableContent content : contents) {
            c += content.getClass() + ", ";
        }
        return "OnEntry{" +
                "contents=" + c +
                '}';
    }
}
