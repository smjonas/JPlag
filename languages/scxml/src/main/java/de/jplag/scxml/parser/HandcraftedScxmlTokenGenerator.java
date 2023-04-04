package de.jplag.scxml.parser;

import de.jplag.scxml.ScxmlTokenType;
import de.jplag.scxml.parser.ScxmlParserAdapter;
import de.jplag.scxml.parser.SimpleScxmlTokenGenerator;
import de.jplag.scxml.parser.model.State;
import de.jplag.scxml.parser.model.Transition;
import de.jplag.scxml.parser.model.executable_content.Action;
import de.jplag.scxml.parser.model.executable_content.ExecutableContent;

import java.util.Comparator;
import java.util.List;

import static de.jplag.scxml.ScxmlTokenType.*;

/**
 * Visits a metamodel containment tree and extracts the relevant token.
 *
 * @author Timur Saglam
 */
public class HandcraftedScxmlTokenGenerator extends SimpleScxmlTokenGenerator {

    /**
     * Creates the visitor.
     *
     * @param adapter is the parser adapter which receives the generated tokens.
     */
    public HandcraftedScxmlTokenGenerator(ScxmlParserAdapter adapter) {
        super(adapter);
    }

    protected void visitStateAttributes(State state) {
        if (state.initial()) {
            adapter.addToken(INITIAL_STATE, state);
        }
        if (state.parallel()) {
            adapter.addToken(PARALLEL_STATE, state);
        }
    }

    @Override
    protected List<State> sortStates(List<State> states) {
        states.sort((s1, s2) -> Boolean.compare(s1.isRegion(), s2.isRegion()));
        return states;
    }

    @Override
    public void visitState(State state) {
        adapter.addToken(state.isRegion() ? REGION : STATE, state);
        depth++;
        visitStateAttributes(state);
        visitStateContents(state);
    }

    @Override
    public List<Transition> sortTransitions(List<Transition> transitions) {
        // Sorts the transitions based on the isGuarded and isTimed attributes
        // that determine the type of the first extracted token.
        transitions.sort((t1, t2) -> {
            if (t1.isTimed() == t2.isTimed()) {
                return Boolean.compare(t1.isGuarded(), t2.isGuarded());
            }
            return Boolean.compare(t1.isTimed(), t2.isTimed());
        });
        return transitions;
    }

    @Override
    public void visitTransition(Transition transition) {
        if (transition.isTimed()) {
            adapter.addToken(TIMED_TRANSITION, transition);
        } else if (transition.isGuarded()) {
            adapter.addToken(GUARDED_TRANSITION, transition);
        } else {
            adapter.addToken(TRANSITION, transition);
        }

        depth++;
        for (ExecutableContent content : transition.contents()) {
            visitExecutableContent(content);
        }
        depth--;
        adapter.addToken(TRANSITION_END, transition);
    }
}
