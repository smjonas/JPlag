package de.jplag;

import de.jplag.yakindu.parser.YakinduParserAdapter;
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
    private static final String[] TEST_SUBJECTS = { "from_proposal.ysc"  /*"simple.sct"*/};
    private File baseDirectory;

    @BeforeEach
    public void setUp() {
        baseDirectory = BASE_PATH.toFile();
        //FileUtil.assertDirectory(baseDirectory, TEST_SUBJECTS);
    }

    @Test
    public void testSimpleTokenExtractionStrategy() throws ParsingException {
        File testFile = new File(baseDirectory, TEST_SUBJECTS[0]);
//        File testFile = new File(baseDirectory, "");
        YakinduParserAdapter adapter = new YakinduParserAdapter();
        List<Token> tokens = adapter.parse(Set.of(testFile));
        List<TokenType> tokenTypes = tokens.stream().map(Token::getType).toList();

        assertEquals(List.of(
                REGION, STATE, TRANSITION, VERTEX_END, STATE, REGION,
                STATE, TRANSITION, VERTEX_END, STATE, TRANSITION, TRANSITION,
                VERTEX_END, ENTRY, TRANSITION, VERTEX_END, REGION_END,
                TRANSITION, VERTEX_END, ENTRY, TRANSITION, VERTEX_END, REGION_END, FILE_END
        ), tokenTypes);
    }
}











