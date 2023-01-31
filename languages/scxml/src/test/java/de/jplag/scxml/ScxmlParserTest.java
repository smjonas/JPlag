package de.jplag.scxml;

import de.jplag.ParsingException;
import de.jplag.Token;
import de.jplag.TokenType;
import de.jplag.scxml.parser.ScxmlParser;
import de.jplag.scxml.parser.ScxmlParserAdapter;
import de.jplag.scxml.parser.model.State;
import de.jplag.scxml.parser.model.Statechart;
import de.jplag.scxml.parser.model.Transition;
import de.jplag.scxml.parser.model.executable_content.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static de.jplag.SharedTokenType.FILE_END;
import static de.jplag.scxml.StatechartTokenType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ScxmlParserTest {
    private static final Path BASE_PATH = Path.of("src", "test", "resources", "de", "jplag", "statecharts");
    private static final String[] TEST_SUBJECTS = {"simple_state.scxml", "timed_transition.scxml", "statechart.scxml"};
    private final Logger logger = LoggerFactory.getLogger(ScxmlParserTest.class);
    private Language language;
    private File baseDirectory;

    @BeforeEach
    public void setUp() {
        language = new Language();
        baseDirectory = BASE_PATH.toFile();
        //FileUtil.assertDirectory(baseDirectory, TEST_SUBJECTS);
    }

    @Test
    void canParseSimpleTransition() throws ParsingException, ParserConfigurationException, SAXException, IOException {
        File testFile = new File(BASE_PATH.toFile(), TEST_SUBJECTS[0]);
        Statechart actual = new ScxmlParser().parse(testFile);

        State start = State.builder("Start").setInitial().addTransitions(new Transition("Blinking", "user.press_button")).build();
        State mainRegion = State.builder("main_region").addSubstates(start).build();
        Statechart expected = new Statechart("Statechart", List.of(mainRegion));
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void canParseTimedTransition() throws ParsingException, ParserConfigurationException, SAXException, IOException {
        File testFile = new File(BASE_PATH.toFile(), TEST_SUBJECTS[1]);
        Statechart actual = new ScxmlParser().parse(testFile);

        State start = State.builder("Start").addTransitions(Transition.makeTimed(new Transition("Next", List.of(new Script("print('Hello');"))))).build();
        Statechart expected = new Statechart("Statechart", List.of(start));
        // Should remove all elements that are part of the
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void canParseComplexStatechart() throws ParsingException, ParserConfigurationException, SAXException, IOException {
        File testFile = new File(BASE_PATH.toFile(), TEST_SUBJECTS[2]);
        Statechart actual = new ScxmlParser().parse(testFile);

        State start = State.builder("Start").setInitial()
                .addTransitions(new Transition("Blinking", "user.press_button", List.of(new Assignment()))).build();

        State light = State.builder("Light")
                .addTransitions(new Transition("Dark"))
                .addOnEntry(new If("true", new Assignment())).build();

        State dark = State.builder("Dark")
                .addTransitions(
                        new Transition("Start", null, "t == 5"),
                        new Transition("Light", "C")
                )
                .addOnEntry(new Send("A", "1s"))
                .addOnExit(new Cancel("B")).build();

        State blinking = State.builder("Blinking")
                .addSubstates(light, dark)
                .addTransitions(new Transition("Start", "user.press_button"))
                .addOnEntry(new Assignment()).build();

        State mainRegion = State.builder("main_region").addSubstates(start, blinking).build();
        Statechart expected = new Statechart("Statechart", List.of(mainRegion));
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void testSimpleTokenExtractionStrategy() throws ParsingException {
        File testFile = new File(BASE_PATH.toFile(), TEST_SUBJECTS[2]);
        ScxmlParserAdapter adapter = new ScxmlParserAdapter();
        List<Token> tokens = adapter.parse(Set.of(testFile));
        List<TokenType> tokenTypes = tokens.stream().map(Token::getType).toList();
        assertEquals(List.of(
                STATE, STATE, TRANSITION, ASSIGNMENT, TRANSITION_END, STATE_END, STATE, ON_ENTRY, ASSIGNMENT, ACTION_END, TRANSITION,
                TRANSITION_END, STATE, ON_ENTRY, IF, ASSIGNMENT, IF_END, ACTION_END, TRANSITION, TRANSITION_END, STATE_END, STATE, ON_ENTRY, SEND, ACTION_END,
                ON_EXIT, CANCEL, ACTION_END, TRANSITION, TRANSITION_END, TRANSITION, TRANSITION_END, STATE_END, STATE_END, STATE_END, FILE_END
        ), tokenTypes);
    }

    @AfterEach
    public void tearDown() {
//        FileUtil.clearFiles(new File(BASE_PATH.toString()), Language.VIEW_FILE_SUFFIX);
    }

}










