package de.jplag.statecharts;

import de.jplag.ParsingException;
import de.jplag.Token;
import de.jplag.testutils.FileUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

class ScxmlParserTest {
    private final Logger logger = LoggerFactory.getLogger(ScxmlParserTest.class);

    private static final Path BASE_PATH = Path.of("src", "test", "resources", "de", "jplag", "statecharts");
    private static final String[] TEST_SUBJECTS = {"Ati.scxml"};

    private de.jplag.Language language;
    private File baseDirectory;

    @BeforeEach
    public void setUp() {
        language = new StatechartLanguage();
        baseDirectory = BASE_PATH.toFile();
        FileUtil.assertDirectory(baseDirectory, TEST_SUBJECTS);
    }

    @Test
    void testScxmlParser() throws ParsingException {
        File testFile = new File(BASE_PATH.toFile(), TEST_SUBJECTS[0]);
        List<Token> result = language.parse(Set.of(testFile));

//        logger.debug(TokenPrinter.printTokens(result, baseDirectory, Optional.of(Language.VIEW_FILE_SUFFIX)));
//        List<TokenType> tokenTypes = result.stream().map(Token::getType).toList();
//        logger.info("Parsed token types: " + tokenTypes.stream().map(TokenType::getDescription).toList().toString());
//        assertEquals(43, tokenTypes.size());
//        assertEquals(10, new HashSet<>(tokenTypes).size());
//
//        var bookstoreTokens = TokenUtils.tokenTypesByFile(result, testFiles.get(0));
//        var bookstoreRenamedTokens = TokenUtils.tokenTypesByFile(result, testFiles.get(2));
//        var bookstoreExtendedTokens = TokenUtils.tokenTypesByFile(result, testFiles.get(1));
//        assertTrue(bookstoreTokens.size() < bookstoreExtendedTokens.size());
//        assertIterableEquals(bookstoreTokens, bookstoreRenamedTokens);
    }

    @AfterEach
    public void tearDown() {
//        FileUtil.clearFiles(new File(BASE_PATH.toString()), Language.VIEW_FILE_SUFFIX);
    }

}
