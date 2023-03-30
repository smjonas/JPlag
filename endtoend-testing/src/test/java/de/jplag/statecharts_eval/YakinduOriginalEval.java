package de.jplag.statecharts_eval;

import de.jplag.JPlagComparison;
import de.jplag.JPlagResult;
import de.jplag.exceptions.ExitException;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;

import java.util.*;

class YakinduOriginalEval {

    private static final List<String> TOOLS = List.of("yakindu", "scxml");

    private static final List<String> LINES_HEADER = List.of(
            "first", "second", "language", "extraction_strategy", "min_token_length", "avg_similarity", "max_similarity"
    );
    private static final List<String> LINES_HEADER_2 = List.of(
            "year", "language", "min_token_length", "duration", "number_of_files"
    );

    private List<String> createLine(
            int year, String first, String second, String language, String extractionStrategy,
            int minTokenLength, double similarity, double maxSimilarity
    ) {
        return new ArrayList<>(List.of(
                Integer.toString(year), first, second, language, extractionStrategy,
                Integer.toString(minTokenLength), Double.toString(similarity), Double.toString(maxSimilarity)
        ));
    }

    private List<String> createLine2(
            int year, String language, int minTokenLength, long duration, int numberOfFiles
    ) {
        return new ArrayList<>(List.of(
                Integer.toString(year), language, Integer.toString(minTokenLength), Long.toString(duration),
                Integer.toString(numberOfFiles)
        ));
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
            default:
                throw new IllegalStateException("unknown tool " + tool);
        }
        return result + (strategy.equals("simple") ? " (simple)" : " (handcrafted)");
    }

    // For plotting the similarity against the submissions count.
    @Test
    public void evalSimilarityDistribution() throws ExitException {

        List<List<String>> lines = new ArrayList<>();
        lines.add(LINES_HEADER);

        final String EXTRACTION_STRATEGY = "simple";
        final int MIN_TOKEN_MATCH = 10;

        for (int year : new int[]{ 2020, 2021}) {
            for (String tool : TOOLS) {
                JPlagResult jplagResult = Util.runJPlagOriginal(year, tool, MIN_TOKEN_MATCH);
                assert jplagResult.getAllComparisons().size() == Util.getTotalAmountOfUniqueTuples(Util.getSubmissionsCount(year));

                for (JPlagComparison tuple : jplagResult.getAllComparisons()) {
                    String firstFilename = FilenameUtils.removeExtension(tuple.firstSubmission().getName());
                    String secondFilename = FilenameUtils.removeExtension(tuple.secondSubmission().getName());

                    lines.add(createLine(
                            year, firstFilename, secondFilename, getToolDescriptionStrategy(tool, EXTRACTION_STRATEGY),
                            EXTRACTION_STRATEGY, MIN_TOKEN_MATCH, tuple.similarity(), tuple.maximalSimilarity()
                    ));
                }
            }
        }
        Util.writeCSVFile("target", "similarity_dist", lines);
    }

    @Test
    public void evalBenchmark() throws ExitException {
        final int year = 2020;
        final int MIN_TOKEN_MATCH = 10;
        final int inputFilesCount = 2;

        List<List<String>> lines = new ArrayList<>();
        lines.add(LINES_HEADER_2);

        for (String tool : TOOLS) {
            long totalDuration = 0;
            for (int i = 0; i < 10; i++) {
                JPlagResult jplagResult = Util.runJPlagOriginal(year, tool, MIN_TOKEN_MATCH);
                // assert jplagResult.getAllComparisons().size() == Util.getTotalAmountOfUniqueTuples(Util.getSubmissionsCount(year));
                totalDuration += jplagResult.getDuration();
                System.out.println(jplagResult.getDuration());
            }
            long avgDuration = totalDuration / 10;
            lines.add(createLine2(
                    year, tool.equals("scxml") ? "SCXML" : "Create", MIN_TOKEN_MATCH, avgDuration, inputFilesCount
            ));
        }

        Util.writeCSVFile("/home/jonas/Desktop/statecharts-eval/eval/plots/input/","benchmark", lines);
        System.out.println(lines);
        System.out.println("lines");
    }
}
