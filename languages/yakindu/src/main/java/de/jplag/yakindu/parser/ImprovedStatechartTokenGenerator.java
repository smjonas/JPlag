package de.jplag.yakindu.parser;

import de.jplag.yakindu.StatechartTokenType;
import de.jplag.scxml.parser.ScxmlParserAdapter;
import de.jplag.scxml.parser.model.State;
import de.jplag.scxml.parser.model.Transition;
import de.jplag.scxml.parser.model.executable_content.Action;
import de.jplag.scxml.parser.model.executable_content.ExecutableContent;

import java.util.List;

/**
 * Visits a metamodel containment tree and extracts the relevant token.
 *
 * @author Timur Saglam
 */
public class ImprovedStatechartTokenGenerator extends SimpleStatechartTokenGenerator {

    /**
     * Creates the visitor.
     *
     * @param adapter is the parser adapter which receives the generated tokens.
     */
    public ImprovedStatechartTokenGenerator(ScxmlParserAdapter adapter) {
        super(adapter);
    }

    protected void visitStateAttributes(State state) {
        if (state.initial()) {
            adapter.addToken(StatechartTokenType.INITIAL_STATE, state);
        }
        if (state.parallel()) {
            adapter.addToken(StatechartTokenType.PARALLEL_STATE, state);
        }
    }

    @Override
    public void visitState(State state) {
        adapter.addToken(state.isRegion() ? StatechartTokenType.REGION : StatechartTokenType.STATE, state);
        depth++;
        visitStateAttributes(state);
        visitActions(state.actions());

        for (Transition transition : state.transitions()) {
            visitTransition(transition);
        }

        for (State substate : state.substates()) {
            visitState(substate);
        }
        depth--;
        adapter.addToken(StatechartTokenType.STATE_END, state);
    }

    @Override
    public void visitActions(List<Action> actions) {
        List<Action> onEntries = actions.stream().filter(a -> a.type() == Action.Type.ON_ENTRY).toList();
        List<Action> onExits = actions.stream().filter(a -> a.type() == Action.Type.ON_EXIT).toList();
        if (!onEntries.isEmpty()) {
            // Only extract a single ON_ENTRY token even if the state contains multiple.
            // Functionally, this makes no difference.
            adapter.addToken(StatechartTokenType.ON_ENTRY, null);
            List<ExecutableContent> onEntryContents = onEntries.stream().flatMap(a -> a.contents().stream()).toList();
            depth++;
            for (ExecutableContent content : onEntryContents) {
                visitExecutableContent(content);
            }
            depth--;
            adapter.addToken(StatechartTokenType.ACTION_END, null);
        }

        if (!onExits.isEmpty()) {
            adapter.addToken(StatechartTokenType.ON_EXIT, null);
            List<ExecutableContent> onExitContents = onExits.stream().flatMap(a -> a.contents().stream()).toList();
            depth++;
            for (ExecutableContent content : onExitContents) {
                visitExecutableContent(content);
            }
            depth--;
            adapter.addToken(StatechartTokenType.ACTION_END, null);
        }
    }

    @Override
    public void visitTransition(Transition transition) {
        if (transition.isTimed()) {
            adapter.addToken(StatechartTokenType.TIMED_TRANSITION, transition);
        } else if (transition.isGuarded()) {
            adapter.addToken(StatechartTokenType.GUARDED_TRANSITION, transition);
        } else {
            adapter.addToken(StatechartTokenType.TRANSITION, transition);
        }

        depth++;
        for (ExecutableContent content : transition.contents()) {
            visitExecutableContent(content);
        }
        depth--;
        adapter.addToken(StatechartTokenType.TRANSITION_END, transition);
    }
}
