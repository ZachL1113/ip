package nova.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class TaskList {
    private final List<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    public int size() {
        return tasks.size();
    }

    public Task get(int taskNumber) {
        return tasks.get(taskNumber - 1);
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task delete(int taskNumber) {
        return tasks.remove(taskNumber - 1);
    }

    public Task mark(int taskNumber) {
        Task task = get(taskNumber);
        task.markDone();
        return task;
    }

    public Task unmark(int taskNumber) {
        Task task = get(taskNumber);
        task.unmarkDone();
        return task;
    }

    public List<Task> asList() {
        return Collections.unmodifiableList(tasks);
    }

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
