package core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Utility class for reading the text files containing puzzle inputs.
 * <p>
 * Provides methods to read all lines from a file and return them as a list of strings.
 * </p>
 */
public class InputReader {
    /**
     * Reads all lines from a text file specified by its file path.
     * <p>
     * If the file cannot be read due to an {@link IOException}, this method
     * wraps it in a {@link RuntimeException} and rethrows it.
     * </p>
     *
     * @param filePath the path to the text file to read
     * @return a {@link List} of {@link String} containing all lines in the file
     * @throws RuntimeException if an I/O error occurs while reading the file
     */
    public static List<String> readLines(String filePath) {
        try {
            return Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + filePath, e);
        }
    }
}