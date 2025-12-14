package core;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for logging execution times of problems to a file.
 * <p>
 * This class writes timing information to a log file located at "logs/executionTime.log".
 * Each log entry includes the problem identifier, mode, formatted execution time, and timestamp.
 * </p>
 */
public class TimeLogger {

    /** Path to the log file. */
    private static final Path LOG_PATH = Paths.get("logs", "executionTime.log");

    /** Date-time formatter used for timestamps in the log. */
    private static final DateTimeFormatter DT_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Logs the execution time of a problem to the log file.
     * <p>
     * The log entry contains the problem ID, mode, execution time formatted in
     * seconds, milliseconds, microseconds, and nanoseconds, and the current timestamp.
     * </p>
     *
     * @param problemId identifier of the problem
     * @param mode      mode in which the problem was executed (e.g., "Silver" or "Gold")
     * @param nanos     execution time in nanoseconds
     */
    public static void log(String problemId, String mode, long nanos) {
        try {
            Files.createDirectories(LOG_PATH.getParent());
            String line = formatLine(problemId, mode, nanos);
            Files.writeString(LOG_PATH, line + System.lineSeparator(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Error writing log: " + e.getMessage());
        }
    }

    /**
     * Formats a single log line for a given problem and execution time.
     *
     * @param problemId identifier of the problem
     * @param mode      mode in which the problem was executed
     * @param nanos     execution time in nanoseconds
     * @return a formatted string representing a single log entry
     */
    private static String formatLine(String problemId, String mode, long nanos) {
        long seconds = nanos / 1_000_000_000;
        nanos %= 1_000_000_000;

        long ms = nanos / 1_000_000;
        nanos %= 1_000_000;

        long us = nanos / 1_000;
        long ns = nanos % 1_000;

        String execTime = String.format("%d s : %d ms : %d µs : %d ns",
                seconds, ms, us, ns);

        String dateTime = LocalDateTime.now().format(DT_FORMAT);

        return String.format("%s-%s, %s, %s", problemId, mode, execTime, dateTime);
    }
}
