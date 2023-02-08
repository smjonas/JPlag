package de.jplag.scxml.parser.model.executable_content;

import de.jplag.scxml.parser.model.StatechartElement;
import de.jplag.scxml.parser.util.NodeUtil;
import org.w3c.dom.Node;

import java.util.Set;

public interface ExecutableContent extends StatechartElement {

    Set<String> ALLOWED_XML_ELEMENTS = Set.of(
            "raise", "if", "elseif", "else", "foreach", "log", "assign", "script", "send", "cancel"
    );

    String EVENT_ATTRIBUTE = "event";
    String SEND_ID_ATTRIBUTE = "sendid";
    String DELAY_ATTRIBUTE = "delay";

    static ExecutableContent fromNode(Node node) throws IllegalArgumentException {
        return switch (node.getNodeName()) {
            case "raise" -> new SimpleExecutableContent(SimpleExecutableContent.Type.RAISE);
            case "if" -> new If(node);
            case "elseif" -> new ElseIf(node);
            case "else" -> new SimpleExecutableContent(SimpleExecutableContent.Type.ELSE);
            case "foreach" -> new SimpleExecutableContent(SimpleExecutableContent.Type.FOREACH);
            case "log" -> new SimpleExecutableContent(SimpleExecutableContent.Type.LOG);
            case "assign" -> new Assignment();
            case "script" -> new Script(node.getTextContent());
            case "send" -> new Send(NodeUtil.getAttribute(node, EVENT_ATTRIBUTE), NodeUtil.getAttribute(node, DELAY_ATTRIBUTE));
            case "cancel" -> new Cancel(NodeUtil.getAttribute(node, SEND_ID_ATTRIBUTE));
            default -> throw new IllegalArgumentException("invalid node " + node.getNodeName());
        };
    }
}
