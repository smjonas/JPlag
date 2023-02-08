package de.jplag.scxml;

import de.jplag.ParsingException;
import de.jplag.Token;
import de.jplag.TokenType;
import de.jplag.scxml.parser.ScxmlParserAdapter;
import de.jplag.testutils.FileUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import static de.jplag.SharedTokenType.FILE_END;
import static de.jplag.scxml.ScxmlTokenType.*;
import static de.jplag.scxml.ScxmlTokenType.STATE_END;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScxmlTokenGeneratorTest {

    private static final Path BASE_PATH = Path.of("src", "test", "resources", "de", "jplag", "statecharts");
    private static final String[] TEST_SUBJECTS = {"statechart.scxml"};
    private final File baseDirectory = BASE_PATH.toFile();

    @Test
    void testSimpleTokenExtractionStrategy() throws ParsingException {
        File testFile = new File(baseDirectory, TEST_SUBJECTS[0]);
        ScxmlParserAdapter adapter = new ScxmlParserAdapter();
        List<Token> tokens = adapter.parse(Set.of(testFile));
        List<TokenType> tokenTypes = tokens.stream().map(Token::getType).toList();

        assertEquals(List.of(
                STATE, STATE, TRANSITION, ASSIGNMENT, TRANSITION_END, STATE_END, STATE, ON_ENTRY, ASSIGNMENT,
                ACTION_END, TRANSITION, TRANSITION_END, STATE, ON_ENTRY, IF, ASSIGNMENT, IF_END, ACTION_END, TRANSITION,
                TRANSITION_END, STATE_END, STATE, ON_ENTRY, SEND, ACTION_END, ON_EXIT, CANCEL, ACTION_END, TRANSITION,
                TRANSITION_END, TRANSITION, TRANSITION_END, STATE_END, STATE_END, STATE_END, FILE_END
        ), tokenTypes);
    }

    @AfterEach
    public void tearDown() {
        FileUtil.clearFiles(baseDirectory, Language.VIEW_FILE_SUFFIX);
    }
}
