package de.jplag.scxml;

import de.jplag.ParsingException;
import de.jplag.scxml.parser.ScxmlParser;
import de.jplag.scxml.parser.model.State;
import de.jplag.scxml.parser.model.Statechart;
import de.jplag.scxml.parser.model.Transition;
import de.jplag.scxml.parser.model.executable_content.*;
import de.jplag.scxml.util.StateBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ScxmlParserTest {

    private static final Path BASE_PATH = Path.of("src", "test", "resources", "de", "jplag", "statecharts");
    private File baseDirectory = BASE_PATH.toFile();

    private static final String[] TEST_SUBJECTS = {"simple_state.scxml", "timed_transition.scxml", "statechart.scxml"};
    private ScxmlLanguage language;

    @BeforeEach
    public void setUp() {
        language = new ScxmlLanguage();
        // FileUtil.assertDirectory(baseDirectory, TEST_SUBJECTS);
    }

    @Test
    void canParseSimpleTransition() throws ParsingException, ParserConfigurationException, SAXException, IOException {
        File testFile = new File(baseDirectory, TEST_SUBJECTS[0]);
        Statechart actual = new ScxmlParser().parse(testFile);

        State start = new StateBuilder("Start").setInitial().addTransitions(new Transition("Blinking", "user.press_button")).build();
        State mainRegion = new StateBuilder("main_region").addSubstates(start).build();
        Statechart expected = new Statechart("Statechart", List.of(mainRegion));
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void canParseTimedTransition() throws ParsingException, ParserConfigurationException, SAXException, IOException {
        File testFile = new File(baseDirectory, TEST_SUBJECTS[1]);
        Statechart actual = new ScxmlParser().parse(testFile);

        State start = new StateBuilder("Start").addTransitions(Transition.makeTimed(new Transition("Next", List.of(new Script("print('Hello');"))))).build();
        Statechart expected = new Statechart("Statechart", List.of(start));
        // Should remove all elements that are part of the
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void canParseComplexStatechart() throws ParsingException, ParserConfigurationException, SAXException, IOException {
        File testFile = new File(baseDirectory, TEST_SUBJECTS[2]);
        Statechart actual = new ScxmlParser().parse(testFile);

        State start = new StateBuilder("Start").setInitial()
                .addTransitions(new Transition("Blinking", "user.press_button", List.of(new Assignment()))).build();

        State light = new StateBuilder("Light")
                .addTransitions(new Transition("Dark"))
                .addOnEntry(new If("true", new Assignment())).build();

        State dark = new StateBuilder("Dark")
                .addTransitions(
                        new Transition("Start", null, "t == 5"),
                        new Transition("Light", "C")
                )
                .addOnEntry(new Send("A", "1s"))
                .addOnExit(new Cancel("B")).build();

        State blinking = new StateBuilder("Blinking")
                .addSubstates(light, dark)
                .addTransitions(new Transition("Start", "user.press_button"))
                .addOnEntry(new Assignment()).build();

        State mainRegion = new StateBuilder("main_region").addSubstates(start, blinking).build();
        Statechart expected = new Statechart("Statechart", List.of(mainRegion));
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

}










