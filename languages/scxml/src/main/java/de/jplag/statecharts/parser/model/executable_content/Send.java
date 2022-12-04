package de.jplag.statecharts.parser.model.executable_content;

public record Send(String event, String delay) implements ExecutableContent {
}