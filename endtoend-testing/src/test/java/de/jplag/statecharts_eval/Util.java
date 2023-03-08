package de.jplag.statecharts_eval;

import de.jplag.JPlag;
import de.jplag.JPlagResult;
import de.jplag.Language;
import de.jplag.cli.LanguageLoader;
import de.jplag.exceptions.ExitException;
import de.jplag.options.JPlagOptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.fail;

public class Util {

    protected static final String BASE_SUBMISSION_DIR = "/home/jonas/Desktop/statecharts-eval/eval/src/test/resources/obfuscated/2020_assignments/SCXML";
    public static final int ORIGINAL_SUBMISSIONS_COUNT_2020 = 21;
    public static final int ORIGINAL_SUBMISSIONS_COUNT_2021 = 17;


    public static JPlagResult runJPlag(String lang, String submissionFolder, int minTokenMatch) throws ExitException {
        Language language = LanguageLoader.getLanguage(lang).orElseThrow();
        File submissionDir = Path.of(BASE_SUBMISSION_DIR).resolve(submissionFolder).toFile();
        JPlagOptions jplagOptions = new JPlagOptions(language, Set.of(submissionDir), Set.of())
                .withMinimumTokenMatch(minTokenMatch);
        return new JPlag(jplagOptions).run();
    }

    public static void writeCSVFile(String path, String fileName, List<List<String>> results) {
        File file = new File(path + File.separator + fileName + ".csv");
        System.out.println(file.getAbsolutePath());
        try (PrintWriter writer = new PrintWriter(file)) {
            StringBuilder builder = new StringBuilder();
            for (List<String> line : results) {
                builder.append(String.join(";", line));
                builder.append(System.lineSeparator());
            }
            writer.write(builder.toString());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    // Calculates n choose 2
    public static int getTotalAmountOfUniqueTuples(int n) {
        BigInteger ret = BigInteger.ONE;
        int r = 2;
        for (int k = 0; k < r; k++) {
            ret = ret.multiply(BigInteger.valueOf(n - k)).divide(BigInteger.valueOf(k + 1));
        }
        return ret.intValue();
    }
}


