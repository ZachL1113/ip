package nova.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Manages the tasks tracked by Nova.
 */
public class TaskList {
    private final List<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        tasks = new ArrayList<>();
    }

    /**
     * Creates a task list containing copies of the supplied task references.
     *
     * @param tasks Initial tasks.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Returns the number of tasks.
     *
     * @return Task count.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the task at the specified one-based position.
     *
     * @param taskNumber One-based task number.
     * @return Selected task.
     */
    public Task get(int taskNumber) {
        return tasks.get(taskNumber - 1);
    }

    /**
     * Adds a task to the list.
     *
     * @param task Task to add.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes and returns the task at the specified position.
     *
     * @param taskNumber One-based task number.
     * @return Deleted task.
     */
    public Task delete(int taskNumber) {
        return tasks.remove(taskNumber - 1);
    }

    /**
     * Marks and returns the task at the specified position.
     *
     * @param taskNumber One-based task number.
     * @return Marked task.
     */
    public Task mark(int taskNumber) {
        Task task = get(taskNumber);
        task.markDone();
        return task;
    }

    /**
     * Unmarks and returns the task at the specified position.
     *
     * @param taskNumber One-based task number.
     * @return Unmarked task.
     */
    public Task unmark(int taskNumber) {
        Task task = get(taskNumber);
        task.unmarkDone();
        return task;
    }

    /**
     * Returns an unmodifiable view of the tasks.
     *
     * @return Unmodifiable task list.
     */
    public List<Task> asList() {
        return Collections.unmodifiableList(tasks);
    }

    /**
     * Returns tasks whose descriptions contain the keyword, ignoring case.
     *
     * @param keyword Keyword to find.
     * @return Matching tasks.
     */
    public TaskList find(String keyword) {
        String normalizedKeyword = keyword.toLowerCase(Locale.ROOT);
        List<Task> matches = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase(Locale.ROOT).contains(normalizedKeyword)) {
                matches.add(task);
            }
        }
        return new TaskList(matches);
    }
}
