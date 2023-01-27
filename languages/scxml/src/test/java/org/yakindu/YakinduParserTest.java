package org.yakindu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.sgraph.resource.ResourceUtil;

import java.io.File;
import java.nio.file.Path;

class YakinduParserTest {
    private static final Path BASE_PATH = Path.of("src", "test", "resources", "org", "yakindu");
    private static final String[] TEST_SUBJECTS = {"simple.sct"};
    private File baseDirectory;

    @BeforeEach
    public void setUp() {
        baseDirectory = BASE_PATH.toFile();
        //FileUtil.assertDirectory(baseDirectory, TEST_SUBJECTS);
        ResourceUtil.registerModelExtension("sct");
    }

    @Test
    public void test() {
        File testFile = new File(BASE_PATH.toFile(), TEST_SUBJECTS[0]);
        Statechart statechart = ResourceUtil.loadStatechart(testFile);
        System.out.println(statechart.toString());
    }
}











