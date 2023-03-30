package de.jplag.scxml.util;

import de.jplag.scxml.ScxmlTokenType;
import de.jplag.scxml.parser.ScxmlParserAdapter;
import de.jplag.scxml.parser.model.StatechartElement;

import java.util.ArrayList;
import java.util.List;

public class PeekAdapter extends ScxmlParserAdapter {

    private final List<Integer> tokenTypes = new ArrayList<>();

    @Override
    public void addToken(ScxmlTokenType type, StatechartElement source) {
        tokenTypes.add(type.ordinal());
    }

    public List<Integer> getTokenTypes() {
        return tokenTypes;
    }

    /**
     Lexicographically compares two lists of integer representations / ordinals of token types.
     @param first the first list of ordinals of token types
     @param second the second list of ordinals of token types
     @return 0 if the lists are equal, a negative integer if the first list is lexicographically
     less than the second list, or a positive integer if the first list is lexicographically
     greater than the second list
     */
    public static int compareTokenTypeLists(List<Integer> first, List<Integer> second) {
        int size = Math.min(first.size(), second.size());
        for (int i = 0; i < size; i++) {
            int result = Integer.compare(first.get(i), second.get(i));
            if (result != 0) {
                return result;
            }
        }
        return Integer.compare(first.size(), second.size());
    }
}
