package de.jplag.scxml.parser.util;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public final class NodeUtil {

    public static List<Node> getChildNodes(Node root, Set<String> childNames) {
        List<Node> matchingChildren = new ArrayList<>();
        NodeList children = root.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (childNames.contains(child.getNodeName())) {
                matchingChildren.add(children.item(i));
            }
        }
        matchingChildren.sort(Comparator.comparing(Node::getNodeName));
        return matchingChildren;
    }

    public static List<Node> getChildNodes(Node root, String childName) {
        return getChildNodes(root, Set.of(childName));
    }

    public static List<Node> getNodesRecursive(Node root, String name) {
        List<Node> matchingNodes = new ArrayList<>();
        if (root.getNodeName().equals(name)) {
            matchingNodes.add(root);
        }
        NodeList children = root.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            matchingNodes.addAll(getNodesRecursive(child, name));
        }
        return matchingNodes;
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
