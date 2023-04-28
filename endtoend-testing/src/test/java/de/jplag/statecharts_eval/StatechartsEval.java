package de.jplag.statecharts_eval;

import static de.jplag.statecharts_eval.Util.BASE_SUBMISSION_DIR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.*;

import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.*;

import de.jplag.JPlagComparison;
import de.jplag.JPlagResult;
import de.jplag.exceptions.ExitException;

class StatechartsEval {

    // Tools for experiment 1: "yakindu", "scxml", "emf-model"
    private static final List<String> TOOLS = List.of("scxml", "create");
    // private static final List<String> TOOLS = List.of("emf-model");

    // Attacks for experiment 1 (MMT vs. avg. similarity)
    private static final String[] PLAGIARISM_TYPES = new String[] {"insert10", "delete5"};

    // private static final String[] PLAGIARISM_TYPES = new String[]{ "move10", "rename10" };
    // private static final String[] PLAGIARISM_TYPES = new String[]{"insert10", "delete5" };

    private static final List<String> LINES_HEADER = List.of("year", "plag_type", "first", "second", "tuple_type", "tool", "min_token_length",
            "avg_similarity", "max_similarity");

    private List<String> createLine(int year, String plagType, String first, String second, TupleType tupleType, String tool, int minTokenLength,
            double similarity, double maxSimilarity) {
        return new ArrayList<>(List.of(Integer.toString(year), plagType, first, second, tupleType.toString(), tool, Integer.toString(minTokenLength),
                Double.toString(similarity), Double.toString(maxSimilarity)));
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
    public static void deleteViewFiles() {
        for (String subFolder : PLAGIARISM_TYPES) {
            deleteViewFiles(BASE_SUBMISSION_DIR + "/" + subFolder);
            deleteViewFiles(BASE_SUBMISSION_DIR + "/SCXML/" + subFolder);
            deleteViewFiles(BASE_SUBMISSION_DIR + "/EMF_MODEL/" + subFolder);
        }
    }

    /**
     * This experiment collects the average similarity values for the "create", "scxml" and "emf-model"
     * language modules. There might be errors when running this experiment for all tools at once.
     * In that case, try running it for "scxml" + "create" and "emf-model" separately and then merge the output
     * files into a single file.
     * The active sorting strategy needs to be set to the handcrafted token extraction for both the SCXML
     * and Yakindu language modules. The relevant methods for this are the createYakinduVisitor
     * method in YakinduParserAdapter as well as the constructor in ScxmlParserAdapter.
     */
    @Test
    public void experiment1() throws ExitException {

        List<List<String>> lines = new ArrayList<>();
        lines.add(LINES_HEADER);

        for (int year : new int[] {2020, 2021}) {
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
                        // The number of submissions for the EMF Model language module is 3 more
                        // since it also contains the 3 .ecore files.
                        int n = 2 * Util.getSubmissionsCount(year) + (tool.equals("emf-model") ? 3 : 0);

                        // The 2020 dataset for rename10 contains 5 fewer submissions
                        if (year == 2020 && tool.equals("scxml") && plagiarismType.equals("rename10")) {
                            n = 2 * (Util.getSubmissionsCount(year) - 5);
                        }

                        String message = String.format("%s, %s (%d)", tool, plagiarismType, year);
                        assertEquals(Util.getTotalAmountOfUniqueTuples(n), jplagResult.getAllComparisons().size(), message);

                        for (JPlagComparison tuple : jplagResult.getAllComparisons()) {
                            String firstFilename = FilenameUtils.removeExtension(tuple.firstSubmission().getName());
                            String secondFilename = FilenameUtils.removeExtension(tuple.secondSubmission().getName());
                            TupleType tupleType = TupleType.of(firstFilename, secondFilename);

                            if (tupleType != TupleType.UNRELATED) {
                                lines.add(createLine(year, plagiarismType, firstFilename, secondFilename, tupleType, tool, token_len,
                                        tuple.similarity(), tuple.maximalSimilarity()));
                            }
                        }
                        deleteViewFiles();
                    }
                }
            }
            // First run (with TOOLS set to "scxml", "yakindu")
            Util.writeCSVFile("target", "experiment1", lines);

            // Second run (with TOOLS set to "emf-model")
            // Util.writeCSVFile("target", "experiment1_emf", lines);
        }
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
        lines.add(LINES_HEADER);

        final boolean SORTING = true;
        for (int year : new int[] {2020, 2021}) {
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
                            lines.add(createLine(year, plagiarismType, firstFilename, secondFilename, tupleType,
                                    getToolDescriptionSorting(tool, SORTING), token_len, tuple.similarity(), tuple.maximalSimilarity()));
                        }
                    }
                    deleteViewFiles();
                }
            }
            Util.writeCSVFile("/home/jonas/Desktop/statecharts-eval/eval/plots/input/",
                    // String.format("experiment2_handcrafted_%ssorting", SORTING ? "" : "no_"),
                    // "experiment2_handcrafted_no_sorting",
                    // "experiment2_handcrafted_simple_sorting",
                    "experiment2_handcrafted_recursive_sorting", lines);
        }
    }

    @Test
    public void experiment3() throws ExitException {
        List<List<String>> lines = new ArrayList<>();
        lines.add(LINES_HEADER);

        final String STRATEGY = "simple";
        for (int year : new int[] {2020, 2021}) {
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
                    if (year == 2020 && tool.equals("scxml") && plagiarismType.equals("rename10")) {
                        n = 2 * (Util.getSubmissionsCount(year) - 5);
                    }

                    assertEquals(Util.getTotalAmountOfUniqueTuples(n), jplagResult.getAllComparisons().size());

                    for (JPlagComparison tuple : jplagResult.getAllComparisons()) {
                        String firstFilename = FilenameUtils.removeExtension(tuple.firstSubmission().getName());
                        String secondFilename = FilenameUtils.removeExtension(tuple.secondSubmission().getName());
                        TupleType tupleType = TupleType.of(firstFilename, secondFilename);

                        if (tupleType != TupleType.UNRELATED) {
                            lines.add(createLine(year, plagiarismType, firstFilename, secondFilename, tupleType,
                                    getToolDescriptionStrategy(tool, STRATEGY), TOKEN_LEN, tuple.similarity(), tuple.maximalSimilarity()));
                        }
                    }
                    deleteViewFiles();
                }
            }
            Util.writeCSVFile("/home/jonas/Desktop/statecharts-eval/eval/plots/input/", String.format("experiment3_%s_partb", STRATEGY), lines);
        }
    }
}
