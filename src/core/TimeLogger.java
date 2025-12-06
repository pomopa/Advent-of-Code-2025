package core;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeLogger {

    private static final Path LOG_PATH = Paths.get("logs", "executionTime.log");
    private static final DateTimeFormatter DT_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
