package de.jplag.scxml.parser.model.executable_content;

import de.jplag.scxml.parser.util.NodeUtil;
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
