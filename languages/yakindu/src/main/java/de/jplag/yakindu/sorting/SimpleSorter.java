package de.jplag.yakindu.sorting;

import de.jplag.yakindu.util.AbstractYakinduVisitor;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import java.util.List;

public class SimpleSorter implements Sorter {

    private final AbstractYakinduVisitor visitor;

    public SimpleSorter(AbstractYakinduVisitor visitor) {
        this.visitor = visitor;
    }

    // Sorts the list of EObjects by the ordinal
    // of the first token that were to be extracted
    @Override
    public <T extends EObject> List<T> sort(EList<T> objects) {
        ECollections.sort(objects, (v1, v2) -> {
            int v1FirstTokenOrdinal = visitor.peekTokens(v1).get(0);
            int v2FirstTokenOrdinal = visitor.peekTokens(v2).get(0);
            return Integer.compare(v1FirstTokenOrdinal, v2FirstTokenOrdinal);
        });
        return objects;
    }
}
