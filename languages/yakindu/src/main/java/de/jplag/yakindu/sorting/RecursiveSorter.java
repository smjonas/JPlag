package de.jplag.yakindu.sorting;

import de.jplag.yakindu.util.AbstractYakinduVisitor;
import de.jplag.yakindu.util.PeekAdapter;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import java.util.List;

public class RecursiveSorter implements Sorter {

    private final AbstractYakinduVisitor visitor;

    public RecursiveSorter(AbstractYakinduVisitor visitor) {
        this.visitor = visitor;
    }

    // Lexicographically sorts the list of EObjects by the token streams they were to produce.
    @Override
    public <T extends EObject> List<T> sort(EList<T> objects) {
        ECollections.sort(objects, (v1, v2) -> {
            List<Integer> v1TokenOrdinals = visitor.peekTokens(v1);
            List<Integer> v2TokenOrdinals = visitor.peekTokens(v2);
            return PeekAdapter.compareTokenTypeLists(v1TokenOrdinals, v2TokenOrdinals);
        });
        return objects;
    }
}
