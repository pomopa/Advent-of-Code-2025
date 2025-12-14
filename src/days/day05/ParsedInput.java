package days.day05;

import java.util.List;

/**
 * Helper class to store parsed input for the Cafeteria problem.
 * <p>
 * Contains two lists: one for ranges of values and one for individual values.
 * Used internally by the {@link Cafeteria} solver to separate ranges from single numbers.
 * </p>
 */
public class ParsedInput {

    /** List of ranges, each represented as a long array [start, end]. */
    List<long[]> ranges;

    /** List of individual values not included in ranges. */
    List<Long> values;

    /**
     * Constructs a ParsedInput object with the given ranges and values.
     *
     * @param ranges list of ranges, each as a long array [start, end]
     * @param values list of individual values
     */
    ParsedInput(List<long[]> ranges, List<Long> values) {
        this.ranges = ranges;
        this.values = values;
    }
}
