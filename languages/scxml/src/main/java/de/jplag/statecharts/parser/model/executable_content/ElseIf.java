package de.jplag.statecharts.parser.model.executable_content;

import de.jplag.statecharts.parser.util.NodeUtil;
import org.w3c.dom.Node;

public record ElseIf(String condition) implements ExecutableContent {

    public ElseIf(Node node) {
        this(NodeUtil.getAttribute(node, "cond"));
    }

    @Override
    public String toString() {
        return "ElseIf";
    }
}
