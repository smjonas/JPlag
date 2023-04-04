package de.jplag.statecharts_eval;

import de.jplag.JPlagComparison;
import de.jplag.JPlagResult;
import de.jplag.exceptions.ExitException;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.*;

import static de.jplag.statecharts_eval.Util.BASE_SUBMISSION_DIR;
import static org.junit.jupiter.api.Assertions.assertEquals;

class YakinduEval {

    // private static final List<String> TOOLS = List.of("emf-model");
    private static final List<String> TOOLS = List.of("yakindu", "scxml");

    // private static final String[] PLAGIARISM_TYPES = new String[]{"insert10", "delete5", "move100", "rename100" };
    // private static final String[] PLAGIARISM_TYPES = new String[]{"insert10", "delete5" };
    private static final String[] PLAGIARISM_TYPES = new String[]{"move10", "rename10", "insert10", "delete5" };

    // private static final String[] PLAGIARISM_TYPES = new String[]{"rename100" };

/*    private static final List<String> EXPERIMENT1_LINES_HEADER = List.of(
            "plag_type", "first", "second", "tuple_type", "tool", "min_token_length", "avg_similarity", "max_similarity"
    );*/

    private static final List<String> EXPERIMENT23_LINES_HEADER = List.of(
           "year", "plag_type", "first", "second", "tuple_type", "tool", "min_token_length", "avg_similarity", "max_similarity"
    );

    private static final List<String> STAT_LINES_HEADER = List.of(
            "plag_type", "min_token_length", "min", "max", "avg", "count"
    );

    private List<String> createLineExperiment1(
            String plagType, String first, String second, TupleType tupleType,
            String tool, int minTokenLength, double similarity, double maxSimilarity
    ) {
        return new ArrayList<>(List.of(
                plagType, first, second, tupleType.toString(), tool,
                Integer.toString(minTokenLength), Double.toString(similarity), Double.toString(maxSimilarity)
        ));
    }

    private List<String> createLineExperiment23(
            int year, String plagType, String first, String second, TupleType tupleType,
            String tool, int minTokenLength, double similarity, double maxSimilarity
    ) {
        return new ArrayList<>(List.of(
                Integer.toString(year), plagType, first, second, tupleType.toString(), tool,
                Integer.toString(minTokenLength), Double.toString(similarity), Double.toString(maxSimilarity)
        ));
    }

    private static void deleteViewFiles(String root) {
        for (int year : List.of(2020, 2021)) {
            File[] files = new File(String.format(root, year)).listFiles();
            if (files == null) {
                return;
            }
            for (File file : files) {
                String fileName = file.getName();
                if (fileName.endsWith(".emfatic") || fileName.endsWith("view")) {
                    file.delete();
                }
            }
        }
    }

    @BeforeAll
    public static void setup() {
        for (String subFolder : PLAGIARISM_TYPES) {
            deleteViewFiles(BASE_SUBMISSION_DIR + "/" + subFolder);
            deleteViewFiles(BASE_SUBMISSION_DIR + "/SCXML/" + subFolder);
            deleteViewFiles(BASE_SUBMISSION_DIR + "/EMF_MODEL/" + subFolder);
        }
    }

    @Test
    public void evalMain() throws ExitException {

        List<List<String>> lines = new ArrayList<>();
        lines.add(EXPERIMENT23_LINES_HEADER);

        for (int year : new int[]{ 2020 , 2021 }) {
            for (String tool : TOOLS) {
                System.out.println(tool);
                for (String plagiarismType : PLAGIARISM_TYPES) {
                    System.out.println(plagiarismType);
                    for (int token_len : List.of(2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36)) {
                        String prefix = "";
                        if (tool.equals("emf-model")) {
                            prefix = "EMF_MODEL/";
                        } else if (tool.equals("scxml")) {
                            prefix = "SCXML/";
                        }

                        JPlagResult jplagResult = Util.runJPlag(year, tool, prefix + plagiarismType, token_len);
                        int n = 2 * Util.getSubmissionsCount(year) + (tool.equals("emf-model") ? 3 : 0);
                        assertEquals(Util.getTotalAmountOfUniqueTuples(n), jplagResult.getAllComparisons().size());
                        Map<TupleType, DoubleSummaryStatistics> statsAvgSimilarity = new HashMap<>();
                        Map<TupleType, DoubleSummaryStatistics> statsMaxSimilarity = new HashMap<>();

                        for (JPlagComparison tuple : jplagResult.getAllComparisons()) {
                            String firstFilename = FilenameUtils.removeExtension(tuple.firstSubmission().getName());
                            String secondFilename = FilenameUtils.removeExtension(tuple.secondSubmission().getName());
                            TupleType tupleType = TupleType.of(firstFilename, secondFilename);

                            if (tupleType != TupleType.UNRELATED) {
                                lines.add(createLineExperiment23(
                                        year, plagiarismType, firstFilename, secondFilename, tupleType,
                                        tool, token_len, tuple.similarity(), tuple.maximalSimilarity()
                                ));

                                // statsAvgSimilarity.putIfAbsent(tupleType, new DoubleSummaryStatistics());
                                // statsMaxSimilarity.putIfAbsent(tupleType, new DoubleSummaryStatistics());
                                // statsAvgSimilarity.get(tupleType).accept(tuple.similarity());
                                // statsMaxSimilarity.get(tupleType).accept(tuple.maximalSimilarity());
                            }
                        }
                        setup();
                    }
                }

            }
            Util.writeCSVFile(
                    "/home/jonas/Desktop/statecharts-eval/eval/plots/input",
                    "experiment1_emf", lines
            );
        }
        // experiment1_yakindu_simple X
        // experiment1_yakindu_handcrafted ?
        // experiment1_scxml_simple X
        // experiment1_scxml_handcrafted ?
        // experiment1_emfmodel X

        // experiment2_yakindu_sorting
        // experiment2_yakindu_no_sorting
        // experiment2_scxml_sorting
        // experiment2_scxml_no_sorting
    }

    private String getToolDescriptionSorting(String tool, boolean sorting) {
        String result = "";
        switch (tool) {
            case "scxml":
                result = "SCXML";
                break;
            case "yakindu":
                result = "Create";
                break;
            case "emf-model":
                result = "EMF Model";
                break;
        }
        return result + (sorting ? " (recursive sorting)" : " (no sorting)");
    }

    private String getToolDescriptionStrategy(String tool, String strategy) {
        assert strategy.equals("simple") || strategy.equals("handcrafted");
        String result = "";
        switch (tool) {
            case "scxml":
                result = "SCXML";
                break;
            case "yakindu":
                result = "Create";
                break;
            case "emf-model":
                result = "EMF Model";
                break;
        }
        return result + (strategy.equals("simple") ? " (simple)" : " (handcrafted)");
    }

    @Test
    public void experiment2() throws ExitException {
        List<List<String>> lines = new ArrayList<>();
        lines.add(EXPERIMENT23_LINES_HEADER);

        final boolean SORTING = true;
        for (int year : new int[]{ 2020, 2021 }) {
            for (String tool : TOOLS) {
                final int token_len = 10;

                for (String plagiarismType : PLAGIARISM_TYPES) {
                    String prefix = "";
                    if (tool.equals("emf-model")) {
                        prefix = "EMF_MODEL/";
                    } else if (tool.equals("scxml")) {
                        prefix = "SCXML/";
                    }

                    JPlagResult jplagResult = Util.runJPlag(year, tool, prefix + plagiarismType, token_len);
                    int n = 2 * Util.getSubmissionsCount(year) + (tool.equals("emf-model") ? 3 : 0);

                    if (year == 2020 && tool.equals("scxml") && plagiarismType.equals("rename10")) {
                        n = 2 * (Util.getSubmissionsCount(year) - 5);
                    }
                    System.out.println(plagiarismType);
                    String message = String.format("%s, %s (%d)", tool, plagiarismType, year);
                    assertEquals(Util.getTotalAmountOfUniqueTuples(n), jplagResult.getAllComparisons().size(), message);

                    for (JPlagComparison tuple : jplagResult.getAllComparisons()) {
                        String firstFilename = FilenameUtils.removeExtension(tuple.firstSubmission().getName());
                        String secondFilename = FilenameUtils.removeExtension(tuple.secondSubmission().getName());
                        TupleType tupleType = TupleType.of(firstFilename, secondFilename);

                        if (tupleType != TupleType.UNRELATED) {
                            lines.add(createLineExperiment23(
                                    year, plagiarismType, firstFilename, secondFilename, tupleType,
                                    getToolDescriptionSorting(tool, SORTING), token_len, tuple.similarity(), tuple.maximalSimilarity()
                            ));
                        }
                    }
                    setup();
                }
            }
            Util.writeCSVFile(
                "/home/jonas/Desktop/statecharts-eval/eval/plots/input/",
                // String.format("experiment2_handcrafted_%ssorting", SORTING ? "" : "no_"),
                "experiment2_handcrafted_recursive_sorting",
                lines
            );
        }
    }

    @Test
    public void experiment3() throws ExitException {
        List<List<String>> lines = new ArrayList<>();
        lines.add(EXPERIMENT23_LINES_HEADER);

        final String STRATEGY = "simple";
        for (int year : new int[]{ 2020, 2021 }) {
            for (String tool : TOOLS) {
                final int TOKEN_LEN = 10;

                for (String plagiarismType : PLAGIARISM_TYPES) {
                    String prefix = "";
                    if (tool.equals("emf-model")) {
                        prefix = "EMF_MODEL/";
                    } else if (tool.equals("scxml")) {
                        prefix = "SCXML/";
                    }

                    JPlagResult jplagResult = Util.runJPlag(year, tool, prefix + plagiarismType, TOKEN_LEN);

                    int n = 2 * Util.getSubmissionsCount(year) + (tool.equals("emf-model") ? 3 : 0);
                    assertEquals(Util.getTotalAmountOfUniqueTuples(n), jplagResult.getAllComparisons().size());

                    for (JPlagComparison tuple : jplagResult.getAllComparisons()) {
                        String firstFilename = FilenameUtils.removeExtension(tuple.firstSubmission().getName());
                        String secondFilename = FilenameUtils.removeExtension(tuple.secondSubmission().getName());
                        TupleType tupleType = TupleType.of(firstFilename, secondFilename);

                        if (tupleType != TupleType.UNRELATED) {
                            lines.add(createLineExperiment23(
                                    year, plagiarismType, firstFilename, secondFilename, tupleType,
                                    getToolDescriptionStrategy(tool, STRATEGY), TOKEN_LEN, tuple.similarity(), tuple.maximalSimilarity()
                            ));
                        }
                    }
                    setup();
                }
            }
            // Util.writeCSVFile(
            //         "/home/jonas/Desktop/statecharts-eval/eval/plots/input/",
            //         String.format("experiment3_%s", STRATEGY),
            //         lines            );
        }
    }
}
