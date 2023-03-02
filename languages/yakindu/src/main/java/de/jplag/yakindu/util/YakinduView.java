package de.jplag.yakindu.util;

import de.jplag.yakindu.YakinduToken;
import de.jplag.yakindu.YakinduTokenType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.slf4j.LoggerFactory.*;

public class YakinduView {

    private final File file;
    private final StringBuilder builder = new StringBuilder();
    private final Logger logger;
    private int line;

    public YakinduView(File file) {
        this.file = file;
        this.logger = getLogger(this.getClass());
        this.line = 1;
    }

    public void writeToFile(String suffix) {
        File viewFile = new File(file + suffix);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(viewFile));) {
            if (!viewFile.createNewFile()) {
                logger.warn("Overwriting Yakindu view file: {}", viewFile);
            }
            writer.append(builder.toString());
        } catch (IOException exception) {
            logger.error("Could not write Yakindu view file!", exception);
        }
    }

    public YakinduToken enhanceToken(YakinduToken token, int depth) {
        String prefix = "  ".repeat(depth);
        YakinduTokenType type = (YakinduTokenType) token.getType();
        String content = type.isEndToken() ? "}" : token + (type.isLeaf() ? "" : " {");
        builder.append(prefix).append(content).append("\n");
        return new YakinduToken(token.getType(), token.getFile(), line++, prefix.length() + 1, content.length(), token.getEObject());
    }
}
