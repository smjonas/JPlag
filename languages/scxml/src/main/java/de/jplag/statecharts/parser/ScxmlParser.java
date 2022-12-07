package de.jplag.statecharts.parser;

import de.jplag.ParsingException;
import de.jplag.statecharts.parser.model.*;
import de.jplag.statecharts.parser.model.executable_content.ExecutableContent;
import de.jplag.statecharts.parser.util.NodeUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An SCXML parser implementation based on a SAX ("Simple API for XML") parser.
 *
 * @author Jonas Strittmatter
 */
public class ScxmlParser implements ScxmlElementVisitor {

    private final DocumentBuilder builder;
    private final List<String> initialStateTargets = new ArrayList<>();

    public ScxmlParser() throws ParserConfigurationException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
    }

    public Statechart parse(File file) throws IOException, SAXException, ParsingException {
        Document document = builder.parse(file);
        try {
            return visitRoot(document.getDocumentElement());
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

        //assert initialStateTargets.isEmpty() : initialStateTargets.toString();
        boolean initial = NodeUtil.getAttribute(node, "initial") != null || initialStateTargets.contains(id);

        // Store all encountered initial states for later
        Node child = NodeUtil.getFirstChild(node, "initial");
        if (child != null) {
            initialStateTargets.add(visitInitialTransition(child).target());
        }

        OnEntry onEntry = visitOnEntry(NodeUtil.getFirstChild(node, "onentry"));
        OnExit onExit = visitOnExit(NodeUtil.getFirstChild(node, "onexit"));
        List<Transition> transitions = NodeUtil.getChildNodes(node, "transition").stream().map(this::visitTransition).toList();
        List<State> states = NodeUtil.getChildNodes(node, "state").stream().map(this::visitState).toList();
        return new State(id, transitions, states, onEntry, onExit, initial, false);
    }

    private ExecutableContent[] parseExecutableContents(Node node) {
        List<Node> children = NodeUtil.getChildNodes(node, ExecutableContent.ALLOWED_ELEMENTS);
        return children.stream().map(ExecutableContent::fromNode).toArray(ExecutableContent[]::new);
    }

    @Override
    public OnEntry visitOnEntry(Node node) {
        return node == null ? null : new OnEntry(parseExecutableContents(node));
    }

    @Override
    public OnExit visitOnExit(Node node) {
        return node == null ? null : new OnExit(parseExecutableContents(node));
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
                NodeUtil.getAttribute(node, "event"),
                NodeUtil.getAttribute(node, "cond")
        );
    }
}

















