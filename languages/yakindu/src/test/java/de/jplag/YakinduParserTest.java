package de.jplag;

import de.jplag.yakindu.YakinduTokenType;
import de.jplag.yakindu.parser.YakinduParserAdapter;
import net.bytebuddy.description.ByteCodeElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static de.jplag.SharedTokenType.FILE_END;
import static de.jplag.yakindu.YakinduTokenType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

class YakinduParserTest {

    private static final Path BASE_PATH = Path.of("src", "test", "resources", "de", "jplag");
    private static final String[] TEST_SUBJECTS = {"from_proposal.ysc", "from_proposal_reordered.ysc", "token_coverage.ysc"  /*"simple.sct"*/};
    private File baseDirectory;

    @BeforeEach
    public void setUp() {
        baseDirectory = BASE_PATH.toFile();
        //FileUtil.assertDirectory(baseDirectory, TEST_SUBJECTS);
    }

    @Test
    public void testSimpleTokenExtractionStrategy() throws ParsingException {
        File testFileOriginal = new File(baseDirectory, TEST_SUBJECTS[0]);
        YakinduParserAdapter adapter = new YakinduParserAdapter();
        List<Token> tokens = adapter.parse(Set.of(testFileOriginal));

        List<TokenType> expectedTokenTypes = List.of(
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
        List<TokenType> tokenTypes = tokens.stream().map(Token::getType).toList();
        assertEquals(expectedTokenTypes, tokenTypes);

        File testFileReordered = new File(baseDirectory, TEST_SUBJECTS[1]);
        List<Token> tokensReordered = adapter.parse(Set.of(testFileReordered));

        // Check that reordering elements on the same level does not change the token stream
        List<TokenType> tokenTypesReordered = tokensReordered.stream().map(Token::getType).toList();
        assertEquals(expectedTokenTypes, tokenTypesReordered);
    }

    @Test
    public void testSimpleTokenGeneratorCoverage() throws ParsingException {
        File testFile = new File(baseDirectory, TEST_SUBJECTS[2]);
        YakinduParserAdapter adapter = new YakinduParserAdapter();
        Set<Token> tokens = new HashSet<>(adapter.parse(Set.of(testFile)));
        List<TokenType> tokenTypes = tokens.stream().map(Token::getType).toList();

        List<TokenType> expectedTokenTypes = List.of(
            REGION,
            REGION_END,
            // event, property?
            TRANSITION,
            STATE,
            FINAL_STATE,
            CHOICE,
            ENTRY,
            EXIT,
            SYNCHRONIZATION,
            VERTEX_END
        );
        assertThat(tokenTypes).containsAll(expectedTokenTypes);
    }


}











