package de.jplag.statecharts_eval;

import de.jplag.JPlag;
import de.jplag.Language;
import de.jplag.cli.LanguageLoader;
import de.jplag.exceptions.ExitException;
import de.jplag.options.JPlagOptions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Set;

class ScxmlEval {

    @Test
    public void evalMain() throws ExitException {
        Language language = LanguageLoader.getLanguage("scxml").orElseThrow();
        File submissionDir = new File("src/test/resources/statecharts");
        JPlagOptions jplagOptions = new JPlagOptions(language, Set.of(submissionDir), Set.of())
                .withMinimumTokenMatch(8);
        new JPlag(jplagOptions).run();
    }
}
