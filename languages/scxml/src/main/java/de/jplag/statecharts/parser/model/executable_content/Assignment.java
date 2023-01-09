package de.jplag.statecharts.parser.model.executable_content;

public record Assignment() implements ExecutableContent {

    @Override
    public String toString() {
        return "Assignment";
    }
}
