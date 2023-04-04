package de.jplag.scxml.sorting;

import de.jplag.scxml.parser.model.StatechartElement;

import java.util.List;

public class NoOpSorter implements Sorter {

    // Return the list of StatechartElements unchanged
    @Override
    public <T extends StatechartElement> List<T> sort(List<T> objects) {
        return objects;
    }
}
