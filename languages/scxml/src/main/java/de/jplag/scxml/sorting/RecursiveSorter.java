package de.jplag.scxml.sorting;

import de.jplag.scxml.parser.ScxmlParserAdapter;
import de.jplag.scxml.parser.model.StatechartElement;
import de.jplag.scxml.util.AbstractStatechartVisitor;
import de.jplag.scxml.util.PeekAdapter;

import java.util.List;

public class RecursiveSorter implements Sorter {

    private final AbstractStatechartVisitor visitor;

    public RecursiveSorter(AbstractStatechartVisitor visitor) {
        this.visitor = visitor;
    }

    // Lexicographically sorts the list of StatechartElements by the token streams they were to produce.
    @Override
    public <T extends StatechartElement> List<T> sort(List<T> objects) {
        objects.sort((v1, v2) -> {
            List<Integer> v1TokenOrdinals = visitor.peekTokens(v1);
            List<Integer> v2TokenOrdinals = visitor.peekTokens(v2);
            return PeekAdapter.compareTokenTypeLists(v1TokenOrdinals, v2TokenOrdinals);
        });
        return objects;
    }
}
