package de.jplag.yakindu.util;

import de.jplag.yakindu.YakinduTokenType;
import de.jplag.yakindu.parser.YakinduParserAdapter;
import org.eclipse.emf.ecore.EObject;

import java.util.ArrayList;
import java.util.List;

public class PeekAdapter extends YakinduParserAdapter {

    private final List<Integer> tokenTypes = new ArrayList<>();

    @Override
    public void addToken(YakinduTokenType type, EObject source) {
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
