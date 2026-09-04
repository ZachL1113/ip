import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Event extends Task {
    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH);

    private final LocalDate from;
    private final LocalDate to;

    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    protected String getTypeIcon() {
        return "E";
    }

    @Override
    public String toDataString() {
        return "E | " + (isDone() ? "1" : "0") + " | " + getDescription()
                + " | " + from + " | " + to;
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + from.format(OUTPUT_FORMAT)
                + " to: " + to.format(OUTPUT_FORMAT) + ")";
    }
}
