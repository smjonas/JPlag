package de.jplag.statecharts.parser.model.executable_content;

import de.jplag.statecharts.parser.model.OnEntry;
import de.jplag.statecharts.parser.model.StatechartElement;

import java.util.Arrays;

public record Action(Type type, ExecutableContent... contents) implements ExecutableContent {

    public enum Type {
        ON_ENTRY,
        ON_EXIT,
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Action other)) {
            return false;
        }
        // This is necessary because otherwise the ExecutableContent
        // varargs would be compared as instances of their base classes instead of
        // their subclasses such as SimpleExecutableContent or Assignment.
        // Only the subclasses are records and implement the equals() method.
        return this.type == other.type && Arrays.equals(this.contents, other.contents);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(contents);
    }

    @Override
    public String toString() {
        return "Action";
    }
}
