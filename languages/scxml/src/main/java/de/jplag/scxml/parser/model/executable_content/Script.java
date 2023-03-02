package de.jplag.scxml.parser.model.executable_content;

public record Script(String code) implements ExecutableContent {

    @Override
    public String toString() {
        return "Script";
    }
}
