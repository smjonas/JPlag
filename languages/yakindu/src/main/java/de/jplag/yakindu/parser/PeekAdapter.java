package de.jplag.yakindu.parser;

import de.jplag.Token;
import de.jplag.yakindu.YakinduTokenType;
import org.eclipse.emf.ecore.EObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PeekAdapter extends YakinduParserAdapter {

    private final List<YakinduTokenType> tokens = new ArrayList<>();

    @Override
    public void addToken(YakinduTokenType type, EObject source) {
        tokens.add(type);
    }

    private String numberToChar(int n) {
        assert n >= 0 && n < 26 : "numberToChar: argument is " + n;
        return String.valueOf((char)(n + 65));
    }

    public String getTokenListRepresentation() {
        return tokens.stream().map(t -> numberToChar(t.ordinal())).collect(Collectors.joining());
    }
}
