package com.metelski.prefixtree.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * Util for file processing
 */
@Slf4j
public class FileHelper {

    private FileHelper() {
    }

    /**
     * Reads file word by word and applying function to every read word.
     *
     * @param filePath        file name.
     * @param processFunction function for applying.
     */
    public static void processTextFileByWords(final String filePath, final Consumer<String> processFunction) {
        StringBuilder builder = new StringBuilder();

        try (FileReader fileReader = new FileReader(getFileByPath(filePath))) {
            while (fileReader.ready()) {
                int codePoint = fileReader.read();
                if (Character.isLetter(codePoint)) {
                    builder.append((char) codePoint);
                } else if (builder.length() > 0) {
                    processFunction.accept(builder.toString());
                    builder.delete(0, builder.length());
                }
            }
        } catch (IOException e) {
            log.error("Can't read this file:{} or the file is missing ", filePath, e);
        }
    }

    /**
     * Returns string representation of the path to file.
     *
     * @param filePath path to a file.
     */
    private static File getFileByPath(final String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            return file;
        } else {
            throw new IllegalArgumentException("File should exist and not be a directory.");
        }
    }
}
