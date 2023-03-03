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

    private String numberToChar(int n) {
        assert n >= 0 && n < 26 : "numberToChar: argument is " + n;
        return String.valueOf((char)(n + 65));
    }

    public List<Integer> getTokenTypes() {
        return tokenTypes;
    }

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
