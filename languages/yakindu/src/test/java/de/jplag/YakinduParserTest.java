package de.jplag;

import de.jplag.yakindu.YakinduTokenType;
import de.jplag.yakindu.parser.YakinduParserAdapter;
import net.bytebuddy.description.ByteCodeElement;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.util.*;
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
    public void testEnhancedTokenExtractionStrategy() throws ParsingException {
        File testFileOriginal = new File(baseDirectory, TEST_SUBJECTS[0]);
        YakinduParserAdapter adapter = new YakinduParserAdapter();
        List<Token> tokens = adapter.parse(Set.of(testFileOriginal));

        List<TokenType> expectedTokenTypes = List.of(
                REGION,
                // Blinking
                STATE, TRANSITION, VERTEX_END,
                ORTHOGONAL_COMPOSITE_STATE,
                // blinking2
                REGION, STATE, TRANSITION, TRANSITION, VERTEX_END, REGION_END,
                // blinking1 > Dark
                REGION, STATE, TRANSITION, TRANSITION, VERTEX_END,
                // blinking1 > Light
                STATE, TRANSITION, VERTEX_END,
                // blinking1 Entry
                SHALLOW_HISTORY_ENTRY, TRANSITION, VERTEX_END, REGION_END,
                TRANSITION,
                VERTEX_END,
                // main region entry
                INITIAL_ENTRY, TRANSITION, VERTEX_END,
                REGION_END, FILE_END
        );

        // Token types for the simple token extraction strategy
        /* List<TokenType> expectedTokenTypes = List.of(
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
        */

        List<TokenType> tokenTypes = tokens.stream().map(Token::getType).toList();
        assertEquals(expectedTokenTypes, tokenTypes);

        File testFileReordered = new File(baseDirectory, TEST_SUBJECTS[1]);
        List<Token> tokensReordered = adapter.parse(Set.of(testFileReordered));

        // Check that reordering elements on the same level does not change the token stream
        List<TokenType> tokenTypesReordered = tokensReordered.stream().map(Token::getType).toList();
        assertEquals(expectedTokenTypes, tokenTypesReordered);
    }

    @Test
    @Disabled
    public void testEnhancedTokenGeneratorCoverage() throws ParsingException {
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

    @Test
    public void testAti() throws ParsingException {
        File testFileOriginal = new File(baseDirectory, "Ati.ysc");
        File testFileObfuscated = new File(baseDirectory, "Ati_obfuscated.ysc");

        YakinduParserAdapter adapter = new YakinduParserAdapter();
        List<Token> tokens = adapter.parse(Set.of(testFileOriginal));
        List<Token> tokensObfuscated = adapter.parse(Set.of(testFileObfuscated));
        System.out.println(tokens);
        System.out.println(tokensObfuscated);
        System.out.println(tokens.size() + ":" + tokensObfuscated.size());
        for (TokenType x : tokensObfuscated.stream().map(Token::getType).toList()) {
            System.out.println(x);
        }
    }

    @Test
    void testNumberOfTokensExtracted() throws ParsingException {
        List<Integer> tokenCounts = new ArrayList<>();
        YakinduParserAdapter adapter = new YakinduParserAdapter();
        String inputFolder = "/home/jonas/Desktop/statecharts-eval/eval/src/test/resources/original/2021_assignments/";
        for (File file : new File(inputFolder).listFiles()) {
            if (!file.getName().contains("tmp") && !file.getName().contains("scxml") && !file.getName().contains("yakinduview")) {
                List<Token> tokens = adapter.parse(Set.of(file));
                System.out.println(file.getName() + ":" + tokens.size());
                tokenCounts.add(tokens.size());
            }
        }
        System.out.println(tokenCounts);
    }
}











