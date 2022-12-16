package de.jplag.statecharts.parser.model.executable_content;

import de.jplag.statecharts.parser.model.StatechartElement;
import de.jplag.statecharts.parser.util.NodeUtil;
import org.w3c.dom.Node;

import java.util.Set;

import static de.jplag.statecharts.parser.model.executable_content.SimpleExecutableContent.Type;

public interface ExecutableContent extends StatechartElement {

    final Set<String> ALLOWED_ELEMENTS = Set.of(
            "raise", "if", "elseif", "else", "foreach", "log", "assign", "script", "send", "cancel"
    );

    static ExecutableContent fromNode(Node node) {
        return switch (node.getNodeName()) {
            case "raise" -> new SimpleExecutableContent(Type.RAISE);
            case "if" -> new If(node);
            case "elseif" -> new ElseIf(node);
            case "else" -> new SimpleExecutableContent(Type.ELSE);
            case "foreach" -> new SimpleExecutableContent(Type.FOREACH);
            case "log" -> new SimpleExecutableContent(Type.LOG);
            case "assign" -> new Assignment();
            case "script" -> new Script();
            case "send" -> new Send(NodeUtil.getAttribute(node, "event"), NodeUtil.getAttribute(node, "delay"));
            case "cancel" -> new Cancel(NodeUtil.getAttribute(node, "sendid"));
            default -> throw new AssertionError("invalid node " + node.getNodeName());
        };
    }
}
