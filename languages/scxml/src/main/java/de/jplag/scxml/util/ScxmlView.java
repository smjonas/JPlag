package de.jplag.scxml.util;

import de.jplag.scxml.ScxmlToken;
import de.jplag.scxml.ScxmlTokenType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ScxmlView {

    private final File file;
    private final StringBuilder builder = new StringBuilder();
    private final Logger logger;
    private int line;

    public ScxmlView(File file) {
        this.file = file;
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.line = 1;
    }

    public void writeToFile(String suffix) {
        File viewFile = new File(file + suffix);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(viewFile))) {
            if (!viewFile.createNewFile()) {
                logger.warn("Overwriting statechart view file: {}", viewFile);
            }
            writer.append(builder.toString());
        } catch (IOException exception) {
            logger.error("Could not write statechart view file!", exception);
        }
    }

    public ScxmlToken enhanceToken(ScxmlToken token, int depth) {
        String prefix = "  ".repeat(depth);
        ScxmlTokenType type = (ScxmlTokenType) token.getType();
        String content = type.isEndToken() ? "}" : token.getStatechartElement().toString();
        builder.append(prefix).append(content).append("\n");
        return new ScxmlToken(token.getType(), token.getFile(), line++, prefix.length() + 1, content.length(), token.getStatechartElement());
    }
}
