package de.jplag.yakindu.parser;

import de.jplag.Token;
import de.jplag.yakindu.YakinduTokenType;
import org.yakindu.sct.model.sgraph.*;

import static de.jplag.yakindu.YakinduTokenType.*;


public class DynamicYakinduTokenGenerator extends SimpleYakinduTokenGenerator {

    /**
     * Creates the visitor.
     *
     * @param adapter is the parser adapter which receives the generated tokens.
     */
    public DynamicYakinduTokenGenerator(YakinduParserAdapter adapter) {
        super(adapter);
    }

    //@Override
    //public void visitState(State state) {
    //    YakinduTokenType type;
    //    if (state.isOrthogonal()) {
    //        type = ORTHOGONAL_STATE;
    //    } else if
    //}

    @Override
    public void visitChoice(Choice choice) {
        YakinduTokenType type = switch (choice.getKind()) {
            case DYNAMIC -> DYNAMIC_CHOICE;
            case STATIC -> STATIC_CHOICE;
        };
        adapter.addToken(type, choice);
    }

    @Override
    public void visitEntry(Entry entry) {
        YakinduTokenType token = switch (entry.getKind()) {
            case INITIAL -> INITIAL_ENTRY;
            case SHALLOW_HISTORY -> SHALLOW_HISTORY_ENTRY;
            case DEEP_HISTORY -> DEEP_HISTORY_ENTRY;
        };
        adapter.addToken(token, entry);
    }
}
