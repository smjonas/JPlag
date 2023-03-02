package de.jplag.statecharts_eval;

import de.jplag.JPlagComparison;
import de.jplag.JPlagResult;
import de.jplag.exceptions.ExitException;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;

import java.util.*;

class YakinduOriginalEval {

    private static final String LANGUAGE = "yakindu";

    private static final List<String> LINES_HEADER = List.of(
            "first", "second", "language", "extraction_strategy", "min_token_length", "avg_similarity", "max_similarity"
    );

    private List<String> createLine(
            String first, String second, String language, String extractionStrategy,
            int minTokenLength, double similarity, double maxSimilarity
    ) {
        return new ArrayList<>(List.of(
                TupleType.of(first, second).toString(), first, second, language, extractionStrategy,
                Integer.toString(minTokenLength), Double.toString(similarity), Double.toString(maxSimilarity)
        ));
    }

    // For plotting the min_token_type against the similarity distribution.
    // Variables: min_token_type, extraction_strategy (simple vs. handcrafted)
    @Test
    public void evalSimilarityDistribution() throws ExitException {

        List<List<String>> lines = new ArrayList<>();
        lines.add(LINES_HEADER);
        final String EXTRACTION_STRATEGY = "simple";

        for (final int min_token_match : Set.of(2, 4, 6, 8, 10, 12, 14, 16, 18)) {
            JPlagResult jplagResult = Util.runJPlag("yakindu", "original", min_token_match);
            assert jplagResult.getAllComparisons().size() == Util.getTotalAmountOfUniqueTuples(Util.ORIGINAL_SUBMISSIONS_COUNT_2020);

            for (JPlagComparison tuple : jplagResult.getAllComparisons()) {
                String firstFilename = FilenameUtils.removeExtension(tuple.firstSubmission().getName());
                String secondFilename = FilenameUtils.removeExtension(tuple.secondSubmission().getName());

                lines.add(createLine(
                        firstFilename, secondFilename, LANGUAGE, EXTRACTION_STRATEGY,
                        min_token_match, tuple.similarity(), tuple.maximalSimilarity()
                ));
            }
        }
        Util.writeCSVFile("target", "yakindu_similarity_dist", lines);
    }
}
