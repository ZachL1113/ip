package nova.task;

public abstract class Task {
    private final String description;
    private boolean isDone;

    protected Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markDone() {
        isDone = true;
    }

    public void unmarkDone() {
        isDone = false;
    }

    public boolean isDone() {
        return isDone;
    }

    protected String getDescription() {
        return description;
    }

    public abstract String toDataString();

    protected abstract String getTypeIcon();

    @Override
    public String toString() {
        return "[" + getTypeIcon() + "][" + (isDone ? "X" : " ") + "] " + description;
    }
}
