package de.jplag.statecharts_eval;

import de.jplag.JPlagComparison;
import org.apache.commons.io.FilenameUtils;

public enum TupleType {

    PLAG_ORIGINAL("Plag-Original"),
    PLAG_PLAG("Plag-Plag"),
    ORIGINAL_ORIGINAL("Original-Original"),
    UNRELATED("unrelated");

    private final String text;

    TupleType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public static TupleType of(String firstFilename, String secondFilename) {
        if ((firstFilename + "_obfuscated").equals(secondFilename) || firstFilename.equals(secondFilename + "_obfuscated")) {
            return PLAG_ORIGINAL;
        } else if (firstFilename.contains("obfuscated") && secondFilename.contains("obfuscated")) {
            return PLAG_PLAG;
        } else if (!firstFilename.contains("obfuscated") && !secondFilename.contains("obfuscated")) {
            return ORIGINAL_ORIGINAL;
        }
        return UNRELATED;
    }
}