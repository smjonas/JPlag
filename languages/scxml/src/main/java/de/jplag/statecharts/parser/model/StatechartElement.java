package de.jplag.statecharts.parser.model;

public abstract class StatechartElement {

    protected final String elementName;

    protected StatechartElement(String elementName) {
        this.elementName = elementName;
    }
}
