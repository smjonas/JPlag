package de.jplag.yakindu.parser;

import de.jplag.Token;
import de.jplag.yakindu.YakinduTokenType;
import org.eclipse.emf.ecore.EObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PeekAdapter extends YakinduParserAdapter {

    private final List<Integer> tokens = new ArrayList<>();

    @Override
    public void addToken(YakinduTokenType type, EObject source) {
        tokens.add(type.ordinal());
    }

    public List<Integer> getTokenTypeList() {
        return tokens;
    }

    public static int compareTokenTypeLists(List<Integer> first, List<Integer> second) {
        int size = Math.min(first.size(), second.size());
        for (int i = 0; i < size; i++) {
            int result = Integer.compare(first.get(i), second.get(i));
            if (result != 0) {
                return result;
            }
        }
        return Integer.compare(tokenOrdinals1.size(), tokenOrdinals2.size());
    }
}
