package de.jplag.scxml.parser;

import de.jplag.ParsingException;
import de.jplag.scxml.parser.model.State;
import de.jplag.scxml.parser.model.Statechart;
import de.jplag.scxml.parser.model.StatechartElement;
import de.jplag.scxml.parser.model.Transition;
import de.jplag.scxml.parser.model.executable_content.Action;
import de.jplag.scxml.parser.model.executable_content.ExecutableContent;
import de.jplag.scxml.parser.util.NodeUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * An SCXML parser implementation based on a SAX ("Simple API for XML") parser.
 *
 * @author Jonas Strittmatter
 */
public class ScxmlParser implements ScxmlElementVisitor {

    private static final String STATE_ELEMENT = "state";
    private static final String PARALLEL_STATE_ELEMENT = "parallel";
    private static final String INITIAL_ELEMENT = "initial";
    private static final String ONENTRY_ELEMENT = "onentry";
    private static final String ONEXIT_ELEMENT = "onexit";
    private static final String TRANSITION_ELEMENT = "transition";

    private static final String NAME_ATTRIBUTE = "name";
    private static final String ID_ATTRIBUTE = "id";
    private static final String INITIAL_ATTRIBUTE = "initial";
    private static final String TARGET_ATTRIBUTE = "target";
    private static final String EVENT_ATTRIBUTE = "event";
    private static final String CONDITION_ATTRIBUTE = "cond";

    private final DocumentBuilder builder;
    private final List<String> initialStateTargets = new ArrayList<>();

    public ScxmlParser() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
    }

    public Statechart parse(File file) throws IOException, SAXException, ParsingException {
        Document document = builder.parse(file);
        try {
            Element element = document.getDocumentElement();
            // We perform two passes through the document:
            // In the first pass, we iterate over all <initial> elements within states to resolve initial states.
            // In the second pass, we visit the whole document.
            // This approach is necessary because an initial state may occur in the document prior to
            // the transitions pointing to it.
            resolveInitialStates(element);
            return visitRoot(element);
        } catch (IllegalArgumentException e) {
            throw new ParsingException(file, "failed to parse statechart: " + e.getMessage());
        }
    }

    private void resolveInitialStates(Node root) {
        List<Node> initialElements = NodeUtil.getNodesRecursive(root, INITIAL_ELEMENT);
        List<Transition> transitions = initialElements.stream().map(this::visitInitialTransition).toList();
        initialStateTargets.addAll(transitions.stream().map(Transition::target).toList());
    }

    @Override
    public Statechart visitRoot(Node node) {
        String name = NodeUtil.getAttribute(node, NAME_ATTRIBUTE);
        assert name != null : "statechart element must have name attribute";

        List<Node> stateNodes = NodeUtil.getChildNodes(node, Set.of(STATE_ELEMENT, PARALLEL_STATE_ELEMENT));
        List<State> states = stateNodes.stream().map(this::visitState).collect(Collectors.toList());
        return new Statechart(name, states);
    }

    @Override
    public State visitState(Node node) {
        String id = NodeUtil.getAttribute(node, ID_ATTRIBUTE);
        assert id != null : "state element must have id attribute";

        boolean initial = initialStateTargets.contains(id) || NodeUtil.getAttribute(node, INITIAL_ATTRIBUTE) != null;
        boolean parallel = node.getNodeName().equals(PARALLEL_STATE_ELEMENT);

        Node child = NodeUtil.getFirstChild(node, INITIAL_ELEMENT);
        assert !(parallel && child != null) : "parallel state " + id + " must not have initial element";

        ArrayList<Action> actions = new ArrayList<>(NodeUtil.getChildNodes(node, Set.of(ONENTRY_ELEMENT, ONEXIT_ELEMENT)).stream().map(this::visitAction).toList());
        ArrayList<Transition> transitions = new ArrayList<>(NodeUtil.getChildNodes(node, TRANSITION_ELEMENT).stream().map(this::visitTransition).toList());
        List<State> states = new ArrayList<>(NodeUtil.getChildNodes(node, Set.of(STATE_ELEMENT, PARALLEL_STATE_ELEMENT)).stream().map(this::visitState).toList());
        return new State(id, transitions, states, actions, initial, parallel);
    }

    private List<ExecutableContent> parseExecutableContents(Node node) throws IllegalArgumentException {
        List<Node> children = NodeUtil.getChildNodes(node, ExecutableContent.ALLOWED_XML_ELEMENTS);
        return children.stream().map(ExecutableContent::fromNode).toList();
    }

    @Override
    public Action visitAction(Node node) throws IllegalArgumentException {
        if (node == null) {
            return null;
        }
        Action.Type type = node.getNodeName().equals(ONENTRY_ELEMENT) ? Action.Type.ON_ENTRY : Action.Type.ON_EXIT;
        return new Action(type, parseExecutableContents(node));
    }

    @Override
    public Transition visitInitialTransition(Node node) {
        List<Node> transitionNodes = NodeUtil.getChildNodes(node, TRANSITION_ELEMENT);
        assert !transitionNodes.isEmpty() : "initial element must contain transition child";
        Transition transition = visitTransition(transitionNodes.get(0));
        assert transition.isInitial() : "transition is not an initial transition";
        return transition;
    }

    @Override
    public Transition visitTransition(Node node) throws IllegalArgumentException {
        return new Transition(
                NodeUtil.getAttribute(node, TARGET_ATTRIBUTE),
                NodeUtil.getAttribute(node, EVENT_ATTRIBUTE),
                NodeUtil.getAttribute(node, CONDITION_ATTRIBUTE),
                parseExecutableContents(node),
                // Set timed attribute to false initially, may be updated later in the State class
                false
        );
    }
}
