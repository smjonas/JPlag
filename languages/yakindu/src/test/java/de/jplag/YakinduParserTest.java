package de.jplag;

import de.jplag.ParsingException;
import de.jplag.testutils.FileUtil;
import de.jplag.yakindu.parser.YakinduParserAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.sgraph.resource.ResourceUtil;
import de.jplag.Token;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

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
        YakinduParserAdapter adapter = new YakinduParserAdapter();
        List<Token> tokens = adapter.parse(Set.of(testFile));
        int i = 1;
    }
}











