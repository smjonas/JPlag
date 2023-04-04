package de.jplag.yakindu.sorting;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import java.util.List;

public class NoOpSorter implements Sorter {

    // Return the list of EObjects unchanged
    @Override
    public <T extends EObject> List<T> sort(EList<T> objects) {
        return objects;
    }
}
