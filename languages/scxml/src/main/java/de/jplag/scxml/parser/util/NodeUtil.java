package de.jplag.scxml.parser.util;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public final class NodeUtil {

    public static List<Node> getChildNodes(Node root, Set<String> childNames) {
        List<Node> filteredChildren = new ArrayList<>();
        NodeList children = root.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (childNames.contains(child.getNodeName())) {
                filteredChildren.add(children.item(i));
            }
        }
        filteredChildren.sort(Comparator.comparing(Node::getNodeName));
        return filteredChildren;
    }

    public static List<Node> getChildNodes(Node root, String childName) {
        return getChildNodes(root, Set.of(childName));
    }

    public static Node getFirstChild(Node root, String childName) {
        List<Node> children = getChildNodes(root, Set.of(childName));
        return children.isEmpty() ? null : children.get(0);
    }

    public static String getAttribute(Node node, String name) {
        Node attribute = node.getAttributes().getNamedItem(name);
        if (attribute != null) {
            return attribute.getNodeValue();
        }
        return null;
    }
}
