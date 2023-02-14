package de.jplag.endtoend;

import de.jplag.*;
import de.jplag.cli.LanguageLoader;
import de.jplag.endtoend.helper.TestSuiteHelper;
import de.jplag.endtoend.model.ExpectedResult;
import de.jplag.endtoend.model.Options;
import de.jplag.endtoend.model.ResultDescription;
import de.jplag.exceptions.ExitException;
import de.jplag.options.JPlagOptions;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.util.*;
import java.util.function.DoubleSupplier;
import java.util.stream.Collectors;

import static de.jplag.endtoend.EndToEndGeneratorTest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class YakinduEval {

    private static final Path BASE_SUBMISSION_DIR = Path.of("src", "test", "resources", "2020");
    private static final Path MOVE_SUBMISSION_DIR =  BASE_SUBMISSION_DIR.resolve("move");
    private static final Path DELETE_SUBMISSION_DIR = BASE_SUBMISSION_DIR.resolve("delete");

    private static final int MIN_TOKEN_MATCH = 6;

    private boolean isPlagiarizedPair(JPlagComparison jPlagComparison) {
        return isPlagiarizedPair(jPlagComparison.firstSubmission(), jPlagComparison.secondSubmission())
                || isPlagiarizedPair(jPlagComparison.secondSubmission(), jPlagComparison.firstSubmission());
    }

    private boolean isPlagiarizedPair(Submission firstSubmission, Submission secondSubmission) {
        String firstFilename = FilenameUtils.removeExtension(firstSubmission.getName());
        String secondFilename = FilenameUtils.removeExtension(secondSubmission.getName());
        return (firstFilename + "_obfuscated").equals(secondFilename);
    }

    @Test
    public void eval() throws ExitException {
        Language language = LanguageLoader.getLanguage("yakindu").orElseThrow();
        File submissionDirectory = MOVE_SUBMISSION_DIR.toFile();
        JPlagOptions jplagOptions = new JPlagOptions(language, Set.of(submissionDirectory), Set.of())
                .withMinimumTokenMatch(MIN_TOKEN_MATCH);
        JPlagResult jplagResult = new JPlag(jplagOptions).run();
        List<JPlagComparison> comparisons = jplagResult.getAllComparisons();

        List<JPlagComparison> plagiarismTuples = comparisons.stream().filter(this::isPlagiarizedPair).toList();
        assertEquals(21, plagiarismTuples.size());
        List<JPlagComparison> nonPlagiarismTuples = comparisons.stream().filter(jPlagComparison -> !isPlagiarizedPair(jPlagComparison)).toList();

        JPlagComparison lowestSimilarityPlagiarized = plagiarismTuples.stream().min(Comparator.comparingDouble(JPlagComparison::similarity)).orElse(null);
        System.out.printf("Plagiarized pair with lowest similarity (%.2f) is %s%n", lowestSimilarityPlagiarized.similarity(), lowestSimilarityPlagiarized);

        double avgSimilarityPlagiarized = plagiarismTuples.stream().mapToDouble(JPlagComparison::similarity).average().orElse(0.0);
        double avgSimilarityNonPlagiarized = nonPlagiarismTuples.stream().mapToDouble(JPlagComparison::similarity).average().orElse(0.0);
        System.out.println("Avg similarity plagiarized: " + avgSimilarityPlagiarized);
        System.out.println("Avg similarity non-plagiarized: " + avgSimilarityNonPlagiarized);
    }
}
