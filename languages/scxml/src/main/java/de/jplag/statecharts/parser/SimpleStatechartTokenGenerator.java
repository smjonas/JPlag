package de.jplag.statecharts.parser;

import de.jplag.statecharts.util.AbstractStatechartVisitor;

/**
 * Visits a metamodel containment tree and extracts the relevant token.
 *
 * @author Timur Saglam
 */
public class SimpleStatechartTokenGenerator extends AbstractStatechartVisitor {
    private final ScxmlParserAdapter adapter;

    /**
     * Creates the visitor.
     *
     * @param adapter is the parser adapter which receives the generated tokens.
     */
    public SimpleStatechartTokenGenerator(ScxmlParserAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void visitStatechart(Statechart statechart) {
        parser.addToken(STATECHART);
        for (State state : statechart.states()) {
            visitState();
        }
    }

    @Override
    public void visitState(State state) {
        if (state.initial()) {
            parser.addToken(INITIAL_STATE);
        }
        if (state.parallel()) {
            parser.addToken(PARALLEL_STATE);
        }
        if (state.transitions() != null) {
            for (Transition transition : state.transitions()) {
                visitTransition(transition);
            }
        }
        visitOnEntry(state.onEntry());
        visitOnExit(state.onExit());
    }

    @Override
    public void visitOnEntry(OnEntry onEntry) {
        if (onEntry == null) {
            return;
        }
        for (ExecutableContent content : onEntry.executableContent) {
            visitExecutableContent(content);
        }
    }

    @Override
    public void visitOnExit(OnExit onExit) {
        if (onExit == null) {
            return;
        }
        for (ExecutableContent content : onEntry.executableContent) {
            visitExecutableContent(content);
        }
    }

    @Override
    public void visitTransition(Transition transition) {
        parser.addToken(TRANSITION);
    }

    @Override
    public void visitExecutableContent(ExecutableContent content) {
        Token token;
        switch(content.getClass()) {
            case SimpleExecutableContent.class: visitSimpleExecutableContent((SimpleExecutableContent) content);
            case Assign.class: token = ASSIGNMENT;
            case Script.class: token = SCRIPT;
            case Send.class: token = SEND;
            case Cancel.class: token = CANCEL;
        }
        parser.addToken(token);
    }

    @Override
    public void visitSimpleExecutableContent(SimpleExecutableContent content) {
        Token token;
        switch(content.type()) {
            case RAISE: token = RAISE;
            case IF: token = IF;
            case ELSEIF: token = ELSEIF;
            case ELSE: token = ELSE;
            case FOREACH: token = FOREACH;
            case LOG: token = LOG;
        }
        parser.addToken(token);
    }
}
