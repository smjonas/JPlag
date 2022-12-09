package de.jplag.statecharts.util;

public class ScxmlView extends AbstractStatechartVisitor {

    private final StringBuilder builder;
    private int depth = 0;

    public void writeToFile(String suffix) {
        File treeViewFile = new File(file + suffix);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(treeViewFile));) {
            if (!treeViewFile.createNewFile()) {
                logger.warn("Overwriting tree view file: {}", treeViewFile);
            }
            writer.append(viewBuilder.toString());
        } catch (IOException exception) {
            logger.error("Could not write tree view file!", exception);
        }
    }

    private void addElement(StatechartElement element) {
        builder.append("  ".repeat(depth)).append(element.toString()).append("\n");
    }

    @Override
    public void visitStatechart(Statechart statechart) {
        addElement(statechart);
        depth++;
        for (State state : statechart.states()) {
            visitState(state);
        }
        depth--;
    }

    @Override
    public void visitState(State state) {
        addElement(state);
        if (state.isRegion()) {
            depth++;
            for (State substate : state.substates()) {
                visitState(substate);
            }
            depth--;
        }
    }

    @Override
    public void visitOnEntry(OnEntry onEntry) {
        addElement(onEntry);
        for (content : onEntry.contents()) {
            visitExecutableContent(content);
        }
    }

    @Override
    public void visitOnExit(OnExit onExit) {
        addElement(onEntry);
    }

    @Override
    public void visitTransition(Transition transition) {
        addElement(transition);
    }

    @Override
    public void visitExecutableContent(ExecutableContent content) {
        // TODO: default implementation in BaseClass
        addElement(transition);
    }

    @Override
    public void visitSimpleExecutableContent(SimpleExecutableContent content) {
        addElement(transition);
    }
}
