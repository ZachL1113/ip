package nova.task;

public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    @Override
    protected String getTypeIcon() {
        return "T";
    }

    @Override
    public String toDataString() {
        return "T | " + (isDone() ? "1" : "0") + " | " + getDescription();
    }
}
