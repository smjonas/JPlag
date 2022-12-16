package de.jplag.statecharts.util;

import de.jplag.statecharts.parser.model.*;
import de.jplag.statecharts.parser.model.executable_content.Action;
import de.jplag.statecharts.parser.model.executable_content.ExecutableContent;
import de.jplag.statecharts.parser.model.executable_content.If;
import de.jplag.statecharts.parser.model.executable_content.SimpleExecutableContent;

public class ScxmlView extends AbstractStatechartVisitor {

    private final StringBuilder builder = new StringBuilder();
    private int depth = 0;

//    public void writeToFile(String suffix) {
//        File treeViewFile = new File(file + suffix);
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(treeViewFile));) {
//            if (!treeViewFile.createNewFile()) {
//                logger.warn("Overwriting tree view file: {}", treeViewFile);
//            }
//            writer.append(builder.toString());
//        } catch (IOException exception) {
//            logger.error("Could not write tree view file!", exception);
//        }
//    }

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
    public void visitAction(Action action) {
        addElement(action);
    }

    @Override
    protected void visitIf(If if_) {
        addElement(if_);
    }

    @Override
    public void visitTransition(Transition transition) {
        addElement(transition);
    }

    @Override
    public void visitExecutableContent(ExecutableContent content) {
        addElement(content);
    }

    @Override
    public void visitSimpleExecutableContent(SimpleExecutableContent content) {
        addElement(content);
    }
}
