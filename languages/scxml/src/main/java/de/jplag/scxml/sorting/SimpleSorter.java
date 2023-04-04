package de.jplag.scxml.sorting;

import de.jplag.scxml.parser.model.StatechartElement;
import de.jplag.scxml.util.AbstractStatechartVisitor;

import java.util.List;

public class SimpleSorter implements Sorter {

    private final AbstractStatechartVisitor visitor;

    public SimpleSorter(AbstractStatechartVisitor visitor) {
        this.visitor = visitor;
    }

    // Sorts the list of StatechartElements by the ordinal
    // of the first token that were to be extracted
    @Override
    public <T extends StatechartElement> List<T> sort(List<T> objects) {
        objects.sort((v1, v2) -> {
            int v1FirstTokenOrdinal = visitor.peekTokens(v1).get(0);
            int v2FirstTokenOrdinal = visitor.peekTokens(v2).get(0);
            return Integer.compare(v1FirstTokenOrdinal, v2FirstTokenOrdinal);
        });
        return objects;
    }
}
