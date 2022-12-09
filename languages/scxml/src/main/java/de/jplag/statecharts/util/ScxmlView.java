package de.jplag.statecharts.util;

public class ScxmlView extends AbstractStatechartVisitor {

    private final StringBuilder builder;
    private int depth = 0;

    private void addElement(StatechartElement element) {
        builder.append(element.toString()).append("\n");
        if (element.hasChildren()) {
            builder.append("\t")
        }
    }

    @Override
    public void visitStatechart(Statechart statechart) {

    }

    @Override
    public void visitState(State state) {

    }

    @Override
    public void visitOnEntry(OnEntry onEntry) {

    }

    @Override
    public void visitOnExit(OnExit onExit) {

    }

    @Override
    public void visitTransition(Transition transition) {

    }

    @Override
    public void visitExecutableContent(ExecutableContent content) {

    }

    @Override
    public void visitSimpleExecutableContent(SimpleExecutableContent content) {

    }
}
