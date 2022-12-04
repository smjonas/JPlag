package de.jplag.statecharts.parser.model;

import de.jplag.statecharts.parser.model.executable_content.ExecutableContent;
import de.jplag.statecharts.parser.model.executable_content.SimpleExecutableContent;

import java.util.List;

public class OnEntry extends StatechartElement {

    private List<ExecutableContent> contents;

    public OnEntry(List<ExecutableContent> contents) {
        this.contents = contents;
    }
}
