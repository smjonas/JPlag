package de.jplag.statecharts.parser.model;

import de.jplag.statecharts.parser.model.executable_content.ExecutableContent;

import java.util.Arrays;

public record OnEntry(ExecutableContent... contents) implements StatechartElement {


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof OnEntry other)) {
            return false;
        }
        // This is necessary because otherwise the ExecutableContent
        // varargs would be compared as instances of their base classes instead of
        // their subclasses such as SimpleExecutableContent or Assignment.
        // Only the subclasses are records and implement the equals() method.
        return Arrays.equals(this.contents, other.contents);
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
