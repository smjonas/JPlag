package de.jplag.statecharts_eval;

import de.jplag.JPlagComparison;
import de.jplag.JPlagResult;
import de.jplag.exceptions.ExitException;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;

import java.util.*;

class YakinduEval {

    private static final Set<String> TOOLS = Set.of("yakindu", "scxml");

    private static final String[] PLAGIARISM_TYPES = new String[]{"move", "swap", "delete", "insert"};
    private static final List<String> LINES_HEADER = List.of(
            "plag_type", "first", "second", "tuple_type", "tool", "min_token_length", "avg_similarity", "max_similarity"
    );
    private static final List<String> STAT_LINES_HEADER = List.of(
            "plag_type", "min_token_length", "min", "max", "avg", "count"
    );

    private static final int MIN_TOKEN_MATCH = 6;

    private boolean isPlagiarizedPair(JPlagComparison jPlagComparison) {
        String firstFilename = FilenameUtils.removeExtension(jPlagComparison.firstSubmission().getName());
        String secondFilename = FilenameUtils.removeExtension(jPlagComparison.secondSubmission().getName());
        return (firstFilename + "_obfuscated").equals(secondFilename) || firstFilename.equals(secondFilename + "_obfuscated");
    }

    private boolean isNonPlagiarizedPair(JPlagComparison jPlagComparison) {
        String firstFilename = FilenameUtils.removeExtension(jPlagComparison.firstSubmission().getName());
        String secondFilename = FilenameUtils.removeExtension(jPlagComparison.secondSubmission().getName());
        return !firstFilename.contains("obfuscated") && !secondFilename.contains("obfuscated");
    }

    private List<String> createLine(
            String plagType, String first, String second, TupleType tupleType,
            String tool, int minTokenLength, double similarity, double maxSimilarity
    ) {
        return new ArrayList<>(List.of(
                plagType, first, second, tupleType.toString(), tool,
                Integer.toString(minTokenLength), Double.toString(similarity), Double.toString(maxSimilarity)
        ));
    }

    @Test
    public void evalMain() throws ExitException {
        for (String tool : TOOLS) {
            List<List<String>> lines = new ArrayList<>();
            lines.add(LINES_HEADER);
            for (String plagiarismType : PLAGIARISM_TYPES) {
                JPlagResult jplagResult = Util.runJPlag(tool, plagiarismType, MIN_TOKEN_MATCH);
                Map<TupleType, DoubleSummaryStatistics> statsAvgSimilarity = new HashMap<>();
                Map<TupleType, DoubleSummaryStatistics> statsMaxSimilarity = new HashMap<>();

                for (JPlagComparison tuple : jplagResult.getAllComparisons()) {
                    String firstFilename = FilenameUtils.removeExtension(tuple.firstSubmission().getName());
                    String secondFilename = FilenameUtils.removeExtension(tuple.secondSubmission().getName());
                    TupleType tupleType = TupleType.of(firstFilename, secondFilename);

                    if (tupleType != TupleType.UNRELATED) {
                        lines.add(createLine(
                                plagiarismType, firstFilename, secondFilename, tupleType,
                                tool, MIN_TOKEN_MATCH, tuple.similarity(), tuple.maximalSimilarity()
                        ));

                        // statsAvgSimilarity.putIfAbsent(tupleType, new DoubleSummaryStatistics());
                        // statsMaxSimilarity.putIfAbsent(tupleType, new DoubleSummaryStatistics());
                        // statsAvgSimilarity.get(tupleType).accept(tuple.similarity());
                        // statsMaxSimilarity.get(tupleType).accept(tuple.maximalSimilarity());
                    }
                }
            }
            Util.writeCSVFile(tool, lines);
        }
    }
}
