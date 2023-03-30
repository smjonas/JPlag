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
import java.util.*;
import java.util.stream.Collectors;

import static de.jplag.SharedTokenType.FILE_END;
import static de.jplag.scxml.ScxmlTokenType.*;
import static de.jplag.scxml.ScxmlTokenType.STATE_END;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import de.jplag.scxml.parser.model.StatechartElement;

public class ScxmlTokenGeneratorTest {

    private static final Path BASE_PATH = Path.of("src", "test", "resources", "de", "jplag", "statecharts");
    private static final String[] TEST_SUBJECTS = {"statechart.scxml", "statechart_reordered.scxml"};
    private final File baseDirectory = BASE_PATH.toFile();

    @Test
    void testSimpleTokenExtractionStrategy() throws ParsingException {
        File originalTestFile = new File(baseDirectory, TEST_SUBJECTS[0]);
        ScxmlParserAdapter adapter = new ScxmlParserAdapter();
        List<Token> originalTokens = adapter.parse(Set.of(originalTestFile));
        List<TokenType> originalTokenTypes = originalTokens.stream().map(Token::getType).toList();

        List<TokenType> expectedTokenTypes = List.of(
                STATE, STATE, TRANSITION, ASSIGNMENT, TRANSITION_END, STATE_END, STATE, ON_ENTRY, ASSIGNMENT,
                ACTION_END, TRANSITION, TRANSITION_END, STATE, ON_ENTRY, IF, ASSIGNMENT, IF_END, ACTION_END, TRANSITION,
                TRANSITION_END, STATE_END, STATE, ON_ENTRY, SEND, ACTION_END, ON_EXIT, CANCEL, ACTION_END, TRANSITION,
                TRANSITION_END, TRANSITION, TRANSITION_END, STATE_END, STATE_END, STATE_END, FILE_END
        );
        assertEquals(expectedTokenTypes, originalTokenTypes);

        File reorderedTestFile = new File(baseDirectory, TEST_SUBJECTS[1]);
        List<Token> reorderedTokens = adapter.parse(Set.of(reorderedTestFile));
        List<TokenType> reorderedTokenTypes =  reorderedTokens.stream().map(Token::getType).toList();
        assertEquals(expectedTokenTypes, reorderedTokenTypes);
    }

    @Test
    void testJPlagBug() throws ParsingException {
        File originalTestFile = new File(baseDirectory, "Har_obfuscated.scxml");
        ScxmlParserAdapter adapter = new ScxmlParserAdapter();
        List<Token> originalTokens = adapter.parse(Set.of(originalTestFile));
        List<TokenType> originalTokenTypes = originalTokens.stream().map(Token::getType).toList();
        System.out.println(originalTokenTypes);
    }

    private <T> List<T> getDifferences(List<T> list1, List<T> list2) {
        List<T> differences = new ArrayList<>();

        int i = 0;
        int j = 0;

        while (i < list1.size() && j < list2.size()) {
            if (!list1.get(i).equals(list2.get(j))) {
                differences.add(list1.get(i));
                i++;
            } else {
                i++;
                j++;
            }
        }

        while (i < list1.size()) {
            differences.add(list1.get(i));
            i++;
        }
        return differences;
    }

    @Test
    void testSorting() throws ParsingException {
        File originalTestFile = new File(baseDirectory, "Mic.scxml");
        File reorderedTestFile = new File(baseDirectory, "Mic_obfuscated.scxml");

        ScxmlParserAdapter adapter = new ScxmlParserAdapter();
        List<Token> originalTokens = adapter.parse(Set.of(originalTestFile));
        List<TokenType> originalTokenTypes = originalTokens.stream().map(Token::getType).toList();

        List<Token> reorderedTokens = adapter.parse(Set.of(reorderedTestFile));
        List<TokenType> reorderedTokenTypes = reorderedTokens.stream().map(Token::getType).toList();
        assertEquals(originalTokenTypes, reorderedTokenTypes);
    }

    @Test
    void testNumberOfTokensExtracted() throws ParsingException {
        List<Integer> tokenCounts = new ArrayList<>();
        ScxmlParserAdapter adapter = new ScxmlParserAdapter();
        String inputFolder = "/home/jonas/Desktop/statecharts-eval/eval/src/test/resources/original/2021_assignments/scxml";
        for (File file : new File(inputFolder).listFiles()) {
            if (!file.getName().contains("view")) {
                List<Token> tokens = adapter.parse(Set.of(file));
                System.out.println(file.getName() + ":" + tokens.size());
                tokenCounts.add(tokens.size());
            }
        }
        System.out.println(tokenCounts);
    }

    @Test
    void testFromProposal() throws ParsingException {
        File testFile = new File(baseDirectory, "FromProposal.scxml");
        ScxmlParserAdapter adapter = new ScxmlParserAdapter();
        List<Token> tokens = adapter.parse(Set.of(testFile));
        // System.out.println(tokens.stream().map(r -> ((ScxmlToken) r).getStatechartElement()).toList());
    }

    @AfterEach
    public void tearDown() {
        // FileUtil.clearFiles(baseDirectory, ScxmlLanguage.VIEW_FILE_SUFFIX);
    }
}
