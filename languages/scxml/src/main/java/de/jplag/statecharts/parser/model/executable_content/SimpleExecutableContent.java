package de.jplag.statecharts.parser.model.executable_content;

/**
 * Represents simple executable content as defined in
 * <a href="https://www.w3.org/TR/scxml/#executable">sections 4.2 - 4.7</a> of the SCXML specification.
 * Other executable content <a href="https://www.w3.org/TR/scxml/#profile-dependentexecutablecontent">(section 4.8)</a>
 * is defined in the subclasses {@link Send}
 * TODO: add other subclasses
 */
public record SimpleExecutableContent(Type type) implements ExecutableContent {

    public enum Type {
        RAISE,
        IF,
        ELSEIF,
        ELSE,
        FOREACH,
        LOG
    }
}
