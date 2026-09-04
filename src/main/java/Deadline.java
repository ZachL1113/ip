import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Deadline extends Task {
    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH);

    private final LocalDate by;

    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    @Override
    protected String getTypeIcon() {
        return "D";
    }

    @Override
    public String toDataString() {
        return "D | " + (isDone() ? "1" : "0") + " | " + getDescription() + " | " + by;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }
}
