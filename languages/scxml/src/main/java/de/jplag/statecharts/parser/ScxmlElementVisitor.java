package de.jplag.statecharts.parser;

import de.jplag.statecharts.parser.model.StatechartElement;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class ScxmlElementVisitor {

    public void visit(Element element) {
        switch (element.getNodeName()) {
            case "scxml" -> visitRoot(element);
            case "state" -> visitState(element);
        }
    }

    public abstract StatechartElement visitRoot(Node node);

    public abstract StatechartElement visitState(Node node);

    public abstract StatechartElement visitOnEntry(Node node);

    public abstract StatechartElement visitOnExit(Node node);

    public abstract StatechartElement visitInitialTransition(Node node);

    public abstract StatechartElement visitTransition(Node node);
}
