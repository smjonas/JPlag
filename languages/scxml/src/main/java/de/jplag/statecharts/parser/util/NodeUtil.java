package de.jplag.statecharts.parser.util;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public final class NodeUtil {

    public static List<Node> getChildNodes(Node root, String childName) {
        List<Node> filteredChildren = new ArrayList<>();
        NodeList children = root.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeName().equals(childName)) {
                filteredChildren.add(children.item(i));
            }
        }
        return filteredChildren;
    }

    public static Node getFirstChild(Node root, String childName) {
        List<Node> children = getChildNodes(root, childName);
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
