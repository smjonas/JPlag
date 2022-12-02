package de.jplag.statecharts.parser;

import de.jplag.statecharts.util.AbstractStatechartVisitor;

/**
 * Visits a metamodel containment tree and extracts the relevant token.
 * @author Timur Saglam
 */
public class SimpleStatechartTokenGenerator extends AbstractStatechartVisitor {
    private final ScxmlParserAdapter adapter;

    /**
     * Creates the visitor.
     * @param adapter is the parser adapter which receives the generated tokens.
     */
    public SimpleStatechartTokenGenerator(ScxmlParserAdapter adapter) {
        this.adapter = adapter;
    }
}
