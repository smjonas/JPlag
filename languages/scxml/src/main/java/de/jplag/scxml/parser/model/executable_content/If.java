package de.jplag.scxml.parser.model.executable_content;

import de.jplag.scxml.parser.util.NodeUtil;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public record If(String cond, List<ExecutableContent> contents, List<ElseIf> elseIfs,
                 SimpleExecutableContent else_) implements ExecutableContent {

    private static final Set<String> ALLOWED_CONTENTS = Set.of(
            "raise", "if", "elseif", "else", "foreach", "log", "assign", "script", "send", "cancel"
    );

    public If(String cond, ExecutableContent... contents) {
        this(cond, new ArrayList<>(List.of(contents)), new ArrayList<>(), null);
    }

    public If(Node node) {
        this(NodeUtil.getAttribute(node, "cond"),
                NodeUtil.getChildNodes(node, ALLOWED_CONTENTS).stream().map(ExecutableContent::fromNode).toList(),
                NodeUtil.getChildNodes(node, "elseif").stream().map(ElseIf::new).toList(),
                NodeUtil.getFirstChild(node, "else") == null ? null : new SimpleExecutableContent(SimpleExecutableContent.Type.ELSE)
        );
    }

    @Override
    public String toString() {
        return "If";
    }
}
