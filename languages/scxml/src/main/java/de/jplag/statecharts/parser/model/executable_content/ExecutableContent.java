package de.jplag.statecharts.parser.model.executable_content;

import de.jplag.statecharts.parser.model.StatechartElement;
import de.jplag.statecharts.parser.util.NodeUtil;
import org.w3c.dom.Node;

import java.util.Set;

import static de.jplag.statecharts.parser.model.executable_content.SimpleExecutableContent.*;

public class ExecutableContent extends StatechartElement {

    public static final Set<String> ALLOWED_ELEMENTS = Set.of(
        "raise", "if", "elseif", "else", "foreach", "log", "assign", "script", "send", "cancel"
    );

    public static ExecutableContent fromNode(Node node) {
        return switch (node.getNodeName()) {
            case "raise" -> new SimpleExecutableContent(Type.RAISE);
            case "if" -> new SimpleExecutableContent(Type.IF);
            case "elseif" -> new SimpleExecutableContent(Type.ELSEIF);
            case "else" -> new SimpleExecutableContent(Type.ELSE);
            case "foreach" -> new SimpleExecutableContent(Type.FOREACH);
            case "log" -> new SimpleExecutableContent(Type.LOG);
            case "assign" -> new Assign();
            case "script" -> new Script();
            case "send" -> new Send(NodeUtil.getAttribute(node, "event"), NodeUtil.getAttribute(node, "delay"));
            case "cancel" -> new Cancel();
        };
    }
}
