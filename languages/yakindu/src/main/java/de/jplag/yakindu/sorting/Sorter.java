package de.jplag.yakindu.sorting;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import java.util.List;

public interface Sorter {

    <T extends EObject> List<T> sort(EList<T> objects);
}
