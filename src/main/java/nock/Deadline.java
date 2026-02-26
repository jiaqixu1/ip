package nock;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected final LocalDate by;

    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy");

    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    /**
     * get by string for storage
     * @return by string
     */
    public String getByForStorage() {
        return by.toString(); // yyyy-MM-dd
    }

    /**
     * Print the outcome
     * @return string outcome
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }
}

