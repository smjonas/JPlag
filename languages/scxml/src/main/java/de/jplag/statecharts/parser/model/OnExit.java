package de.jplag.statecharts.parser.model;

import de.jplag.statecharts.parser.model.executable_content.ExecutableContent;

import java.util.Arrays;

public record OnExit(ExecutableContent... contents) implements StatechartElement {

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof OnExit other)) {
            return false;
        }
        return Arrays.equals(this.contents, other.contents);
    }

    // @Override
    // public String toString() {
    //     StringBuilder b = new StringBuilder();
    //     for (ExecutableContent content : contents) {
    //         b.append(content.getClass()).append(", ");
    //     }
    //     return "OnEntry{" +
    //             "contents=" + b +
    //             '}';
    // }

    @Override
    public String toString() {
        return "OnExit";
    }

}
