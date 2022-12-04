package de.jplag.statecharts.parser;

import de.jplag.ParsingException;
import de.jplag.statecharts.parser.model.State;
import de.jplag.statecharts.parser.model.Statechart;
import de.jplag.statecharts.parser.model.StatechartElement;
import de.jplag.statecharts.parser.model.Transition;
import de.jplag.statecharts.parser.util.NodeUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * An SCXML parser implementation based on a SAX ("Simple API for XML") parser.
 *
 * @author Jonas Strittmatter
 */
public class ScxmlParser extends ScxmlElementVisitor {

    enum ScxmlElement {
        ROOT("scxml"),
        STATE("state"),
        PARALLEL("parallel"),
        TRANSITION("transition");

        public String getElementName() {
            return elementName;
        }

        private final String elementName;

        ScxmlElement(String elementName) {
            this.elementName = elementName;
        }
    }

    private Statechart statechart;
    private List<String> initialStateTargets = new ArrayList<>();

    private final PositionalXmlReader xmlReader;
    private static final List<String> scxmlElementNames = Stream.of(ScxmlElement.values())
            .map(ScxmlElement::getElementName)
            .toList();

    public ScxmlParser() {
        xmlReader = new PositionalXmlReader();
    }

    public Statechart parse(File file) throws ParsingException {
        try {
            Document document = xmlReader.readXML(file);
            return visitRoot(document.getDocumentElement());
        } catch(IOException | SAXException | ParserConfigurationException e) {
            throw new ParsingException(file, "failed to create parser");
        } catch (AssertionError e) {
            throw new ParsingException(file, "failed to parse statechart, msg=" + e.getMessage());
        }
    }

    @Override
    public Statechart visitRoot(Node node) {
        List<Node> stateNodes = NodeUtil.getChildNodes(node, "state");
        List<State> states = stateNodes.stream().map(this::visitState).collect(Collectors.toList());
        return new Statechart(states);
    }

    @Override
    public State visitState(Node node) {
        String id = NodeUtil.getAttribute(node, "id");
        assert id != null : "state element must have id attribute";

        boolean initial = NodeUtil.getAttribute(node, "initial") != null || initialStateTargets.contains(id);

        // Store all encountered initial states for later
        List<Node> initialChildren = NodeUtil.getChildNodes(node, "initial");
        if (!initialChildren.isEmpty()) {
            Node child = initialChildren.get(initialChildren.size() - 1);
            initialStateTargets.add(visitInitialTransition(child).target);
        }
        return new State(initial, NodeUtil.getAttribute(node, "onentry"), NodeUtil.getAttribute());
    }

    @Override
    public StatechartElement visitOnEntry(Node node) {
        return null;
    }

    @Override
    public StatechartElement visitOnExit(Node node) {
        return null;
    }

    @Override
    public Transition visitInitialTransition(Node node) {
        List<Node> transitionNodes = NodeUtil.getChildNodes(node, "transition");
        assert !transitionNodes.isEmpty() : "initial element must contain transition child";
        Transition transition = visitTransition(transitionNodes.get(0));
        assert transition.isInitial() : "transition is not an initial transition";
        return transition;
    }

    @Override
    public Transition visitTransition(Node node) {
        return new Transition(
            NodeUtil.getAttribute(node, "target"),
            NodeUtil.getAttribute(node, "cond"),
            NodeUtil.getAttribute(node, "event")
        );
    }
}

















