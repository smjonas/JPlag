package de.jplag.statecharts;

import de.jplag.ParsingException;
import de.jplag.statecharts.parser.ScxmlParser;
import de.jplag.statecharts.parser.model.*;
import de.jplag.statecharts.parser.model.executable_content.Assign;
import de.jplag.statecharts.parser.model.executable_content.Cancel;
import de.jplag.statecharts.parser.model.executable_content.Send;
import de.jplag.testutils.FileUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScxmlParserTest {
    private final Logger logger = LoggerFactory.getLogger(ScxmlParserTest.class);

    private static final Path BASE_PATH = Path.of("src", "test", "resources", "de", "jplag", "statecharts");
    private static final String[] TEST_SUBJECTS = {"simple_state.scxml", "statechart.scxml"};

    private de.jplag.Language language;
    private File baseDirectory;

    @BeforeEach
    public void setUp() {
        language = new StatechartLanguage();
        baseDirectory = BASE_PATH.toFile();
        FileUtil.assertDirectory(baseDirectory, TEST_SUBJECTS);
    }

    @Test
    void canParseSimpleTransition() throws ParsingException, ParserConfigurationException, SAXException, IOException {
        File testFile = new File(BASE_PATH.toFile(), TEST_SUBJECTS[0]);
        Statechart actual = new ScxmlParser().parse(testFile);

        State start = new State("Start", List.of(new Transition("Blinking", "user.press_button")), true);
        State mainRegion = new State("main_region", List.of(start));
        Statechart expected = new Statechart(List.of(mainRegion));
        assertEquals(expected, actual);
    }

    @Test
    void canParseComplexStatechart() throws ParsingException, ParserConfigurationException, SAXException, IOException {
        File testFile = new File(BASE_PATH.toFile(), TEST_SUBJECTS[1]);
        Statechart actual = new ScxmlParser().parse(testFile);

        State start = new State("Start", List.of(new Transition("Blinking", "user.press_button")), true);
        State light = new State("Light", List.of(new Transition("Dark")), new OnEntry(List.of(new Assign())));
        State dark = new State("Dark",  List.of(
            new Transition("Start", null, "t == 5"),
            new Transition("Light", "Dark_t_1_timeEvent_0")
        ),
           new OnEntry(List.of(new Send("Dark_t_1_timeEvent_0", "1s"))),
           new OnExit(List.of(new Cancel("Dark_t_1_timeEvent_0")))
        );

        State blinking = new State("Blinking",  List.of(new Transition("Start", "user.press_button")),
            List.of(light, dark), false,
            new OnEntry(List.of(new Assign())),
            null
        );
        State mainRegion = new State("main_region", List.of(start, blinking));
        Statechart expected = new Statechart(List.of(mainRegion));
        assertEquals(expected, actual);

//        List<Token> result = language.parse(Set.of(testFile));

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
