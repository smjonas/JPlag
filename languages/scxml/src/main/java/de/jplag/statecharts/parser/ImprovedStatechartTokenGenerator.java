package de.jplag.statecharts.parser;

import de.jplag.statecharts.parser.model.State;
import de.jplag.statecharts.parser.model.Transition;
import de.jplag.statecharts.parser.model.executable_content.Action;
import de.jplag.statecharts.parser.model.executable_content.ExecutableContent;

import java.util.List;

import static de.jplag.statecharts.StatechartTokenType.*;

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
            adapter.addToken(INITIAL_STATE, state);
        }
        if (state.parallel()) {
            adapter.addToken(PARALLEL_STATE, state);
        }
    }

    @Override
    public void visitState(State state) {
        adapter.addToken(state.isRegion() ? REGION : STATE, state);
        depth++;
        visitStateAttributes(state);
        visitStateContents(state);
    }

    @Override
    public void visitActions(List<Action> actions) {
        List<Action> onEntries = actions.stream().filter(a -> a.type() == Action.Type.ON_ENTRY).toList();
        List<Action> onExits = actions.stream().filter(a -> a.type() == Action.Type.ON_EXIT).toList();
        if (!onEntries.isEmpty()) {
            // Only extract a single ON_ENTRY token even if the state contains multiple.
            // Functionally, this makes no difference.
            adapter.addToken(ON_ENTRY, null);
            List<ExecutableContent> onEntryContents = onEntries.stream().flatMap(a -> a.contents().stream()).toList();
            depth++;
            for (ExecutableContent content : onEntryContents) {
                visitExecutableContent(content);
            }
            depth--;
            adapter.addToken(ACTION_END, null);
        }

        if (!onExits.isEmpty()) {
            adapter.addToken(ON_EXIT, null);
            List<ExecutableContent> onExitContents = onExits.stream().flatMap(a -> a.contents().stream()).toList();
            depth++;
            for (ExecutableContent content : onExitContents) {
                visitExecutableContent(content);
            }
            depth--;
            adapter.addToken(ACTION_END, null);
        }
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
