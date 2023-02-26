package de.jplag;

import de.jplag.yakindu.parser.YakinduParserAdapter;
import net.bytebuddy.description.ByteCodeElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import static de.jplag.SharedTokenType.FILE_END;
import static de.jplag.yakindu.YakinduTokenType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class YakinduParserTest {

    private static final Path BASE_PATH = Path.of("src", "test", "resources", "de", "jplag");
    private static final String[] TEST_SUBJECTS = {"from_proposal.ysc", "from_proposal_reordered.ysc"  /*"simple.sct"*/};
    private File baseDirectory;

    @BeforeEach
    public void setUp() {
        baseDirectory = BASE_PATH.toFile();
        //FileUtil.assertDirectory(baseDirectory, TEST_SUBJECTS);
    }

    @Test
    public void testSimpleTokenExtractionStrategy() throws ParsingException {
        File testFileOriginal = new File(baseDirectory, TEST_SUBJECTS[0]);
//        File testFile = new File(baseDirectory, "");
        YakinduParserAdapter adapter = new YakinduParserAdapter();
        List<Token> tokens = adapter.parse(Set.of(testFileOriginal));
        List<TokenType> tokenTypes = tokens.stream().map(Token::getType).toList();

        var expectedTokenTypes = List.of(
            REGION,
            // Blinking
            STATE,
            // blinking2
            REGION, STATE, TRANSITION, TRANSITION, VERTEX_END, REGION_END,
            // blinking1 > Dark
            REGION, STATE, TRANSITION, TRANSITION, VERTEX_END,
            // blinking1 > Light
            STATE, TRANSITION, VERTEX_END,
            // blinking1 Entry
            ENTRY, TRANSITION, VERTEX_END, REGION_END,
            TRANSITION,
            // Blinking end
            VERTEX_END,
            // Start
            STATE, TRANSITION, VERTEX_END,
            // main region entry
            ENTRY, TRANSITION, VERTEX_END,
            REGION_END, FILE_END
        );
        File testFileReordered = new File(baseDirectory, TEST_SUBJECTS[1]);
        List<Token> tokensReordered = adapter.parse(Set.of(testFileReordered));

        List<TokenType> tokenTypesReordered = tokensReordered.stream().map(Token::getType).toList();
        assertEquals(expectedTokenTypes, tokenTypesReordered);
    }
}











