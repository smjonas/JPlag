package de.jplag.statecharts.parser.model.executable_content;

public record Assign() implements ExecutableContent {

    @Override
    public String toString() {
        return "Assign";
    }

}
