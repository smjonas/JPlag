package de.jplag.scxml.parser;

import de.jplag.scxml.parser.model.StatechartElement;
import org.w3c.dom.Node;

public interface ScxmlElementVisitor {

    StatechartElement visitRoot(Node node);

    StatechartElement visitState(Node node);

    StatechartElement visitAction(Node node);

    StatechartElement visitInitialTransition(Node node);

    StatechartElement visitTransition(Node node);
}
