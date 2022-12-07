package de.jplag.xml.parser;

import de.jplag.xml.parser.model.StatechartElement;
import org.w3c.dom.Node;

public interface ScxmlElementVisitor {

    StatechartElement visitRoot(Node node);

    StatechartElement visitState(Node node);

    StatechartElement visitOnEntry(Node node);

    StatechartElement visitOnExit(Node node);

    StatechartElement visitInitialTransition(Node node);

    StatechartElement visitTransition(Node node);
}
