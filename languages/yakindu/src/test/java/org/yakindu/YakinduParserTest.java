package org.yakindu;

import de.jplag.yakindu.parser.YakinduParserAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.sgraph.resource.ResourceUtil;
import de.jplag.Token;

import java.io.File;
import java.nio.file.Path;
import java.util.Set;

class YakinduParserTest {
    private static final Path BASE_PATH = Path.of("src", "test", "resources", "org", "yakindu");
    private static final String[] TEST_SUBJECTS = { "from_proposal.yst"  /*"simple.sct"*/};
    private File baseDirectory;

    @BeforeEach
    public void setUp() {
        baseDirectory = BASE_PATH.toFile();
        //FileUtil.assertDirectory(baseDirectory, TEST_SUBJECTS);
    }

    @Test
    public void testLoadingStatechart() {
        File testFile = new File(BASE_PATH.toFile(), TEST_SUBJECTS[0]);
        Statechart statechart = ResourceUtil.loadStatechart(testFile);
        System.out.println(statechart.toString());
    }

    @Test
    public void testSimpleTokenExtractionStrategy() {
        File testFile = new File(BASE_PATH.toFile(), TEST_SUBJECTS[0]);
        YakinduParserAdapter adapter = new YakinduParserAdapter();
        List<Token> tokens = adapter.parse(Set.of(testFile));
    }
}











