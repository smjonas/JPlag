package de.jplag.statecharts_eval;

import de.jplag.JPlagComparison;
import de.jplag.JPlagResult;
import de.jplag.exceptions.ExitException;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.util.*;

import static de.jplag.statecharts_eval.Util.BASE_SUBMISSION_DIR;

class YakinduEval {

    private static final List<String> TOOLS = List.of("scxml");

    private static final String[] PLAGIARISM_TYPES = new String[]{"insert10"};
    private static final List<String> LINES_HEADER = List.of(
            "plag_type", "first", "second", "tuple_type", "tool", "min_token_length", "avg_similarity", "max_similarity"
    );
    private static final List<String> STAT_LINES_HEADER = List.of(
            "plag_type", "min_token_length", "min", "max", "avg", "count"
    );

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
        List<List<String>> lines = new ArrayList<>();
        lines.add(LINES_HEADER);
        for (String tool : TOOLS) {
            System.out.println("Current ");
            for (String plagiarismType : PLAGIARISM_TYPES) {
                for (int token_len : List.of(2,4,6,8,10,12,14,16,18,20,22,24,26,30,32,34,36)) {
                    JPlagResult jplagResult = Util.runJPlag(tool, plagiarismType, token_len);
                    Map<TupleType, DoubleSummaryStatistics> statsAvgSimilarity = new HashMap<>();
                    Map<TupleType, DoubleSummaryStatistics> statsMaxSimilarity = new HashMap<>();

                    for (JPlagComparison tuple : jplagResult.getAllComparisons()) {
                        String firstFilename = FilenameUtils.removeExtension(tuple.firstSubmission().getName());
                        String secondFilename = FilenameUtils.removeExtension(tuple.secondSubmission().getName());
                        TupleType tupleType = TupleType.of(firstFilename, secondFilename);

                        if (tupleType != TupleType.UNRELATED) {
                            lines.add(createLine(
                                    plagiarismType, firstFilename, secondFilename, tupleType,
                                    tool, token_len, tuple.similarity(), tuple.maximalSimilarity()
                            ));

                            // statsAvgSimilarity.putIfAbsent(tupleType, new DoubleSummaryStatistics());
                            // statsMaxSimilarity.putIfAbsent(tupleType, new DoubleSummaryStatistics());
                            // statsAvgSimilarity.get(tupleType).accept(tuple.similarity());
                            // statsMaxSimilarity.get(tupleType).accept(tuple.maximalSimilarity());
                        }
                    }
                }
            }
        }
        Util.writeCSVFile("target", "experiment1_scxml_simple", lines);
        // "experiment1_yakindu_simple"
        // "experiment1_yakindu_handcrafted"
        //
    }

    @Test
    public void tearDown() {
        for (String subFolder : PLAGIARISM_TYPES) {
            File[] files = Path.of(BASE_SUBMISSION_DIR).resolve(subFolder).toFile().listFiles();
            for (File file : files) {
                if (file.getName().endsWith(".emfatic") || file.getName().endsWith(".yakinduview")) {
                    file.delete();
                }
            }
        }
    }
}
