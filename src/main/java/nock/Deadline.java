package nock;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that must be completed before a specific date.
 */
public class Deadline extends Task {
    protected final LocalDate by;

    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy");

    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    /**
     * Get the storage ingo
     * @return
     */
    public String getByForStorage() {
        return by.toString(); // yyyy-MM-dd
    }

    /**
     * Print the string
     * @return
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }
}

