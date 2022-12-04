package de.jplag.statecharts.parser.model.executable_content;

public class Send extends ExecutableContent {

    public final String event;
    public final String delay;

    public Send(String event, String delay) {
        this.event = event;
        this.delay = delay;
    }
}
