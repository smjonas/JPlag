package de.jplag.emf.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import org.eclipse.emf.ecore.EPackage;

import de.jplag.emf.util.EMFUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yakindu.base.SGraphPackage;

import de.jplag.ParsingException;
import de.jplag.Token;
import de.jplag.TokenPrinter;
import de.jplag.testutils.FileUtil;
import org.eclipse.gmf.runtime.notation.NotationPackage;

class MinimalModelInstanceTest {
    private final Logger logger = LoggerFactory.getLogger(MinimalModelInstanceTest.class);

    private static final Path BASE_PATH = Path.of("src", "test", "resources", "de", "jplag", "statecharts");
    // private static final String[] TEST_SUBJECTS = {"bookStore.ecore", "bookStore.xml", "bookStore2.xml"};

    private Language language;
    private File baseDirectory;

    @BeforeEach
    public void setUp() {
        language = new Language();
        baseDirectory = BASE_PATH.toFile();
        // FileUtil.assertDirectory(baseDirectory, TEST_SUBJECTS);
    }

    @Test
    @DisplayName("Test tokens extracted from generated example instances")
    void testBookStoreInstances() {
        EPackage.Registry registry = EPackage.Registry.INSTANCE;
        registry.put("http://www.yakindu.org/sct/sgraph/2.0.0", SGraphPackage.eINSTANCE);
        registry.put("http://www.eclipse.org/gmf/runtime/1.0.2/notation", NotationPackage.eINSTANCE);


        File baseFile = new File(BASE_PATH.toString());
        List<File> baseFiles = new ArrayList<>(Arrays.asList(baseFile.listFiles()));
        var sortedFiles = new TreeSet<>(language.customizeSubmissionOrder(baseFiles));
        try {
            List<Token> tokens = language.parse(sortedFiles);
            assertNotEquals(0, tokens.size());
            logger.debug(TokenPrinter.printTokens(tokens, baseDirectory, Optional.of(de.jplag.emf.Language.VIEW_FILE_SUFFIX)));
            logger.info("Parsed tokens: " + tokens);
            assertEquals(7, tokens.size());
        } catch (ParsingException e) {
            Logger logger = LoggerFactory.getLogger(EMFUtil.class);

            fail("Parsing failed: " + e.getMessage(), e);
        }

    }

    @AfterEach
    public void tearDown() {
        FileUtil.clearFiles(new File(BASE_PATH.toString()), de.jplag.emf.Language.VIEW_FILE_SUFFIX);
    }

}
