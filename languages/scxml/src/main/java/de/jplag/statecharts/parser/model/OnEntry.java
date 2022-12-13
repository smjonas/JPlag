package de.jplag.statecharts.parser.model;

import de.jplag.statecharts.parser.model.executable_content.ExecutableContent;

import java.util.Arrays;

public record OnEntry(ExecutableContent... contents) implements StatechartElement {

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof OnEntry other)) {
            return false;
        }

        for (int i = 0; i < this.contents.length; i++) {
            if (!this.contents[i].equals(other.contents[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(contents);
    }

    // @Override
    // public String toString() {
    //     String c = "";
    //     for (ExecutableContent content : contents) {
    //         c += content.getClass() + ", ";
    //     }
    //     return "OnEntry{" +
    //             "contents=" + c +
    //             '}';
    // }

    public String toString() {
        return "OnEntry";
    }
}
