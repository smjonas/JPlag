package de.jplag.scxml.sorting;

import de.jplag.scxml.parser.model.StatechartElement;

import java.util.List;

public interface Sorter {

    <T extends StatechartElement> List<T> sort(List<T> objects);
}
