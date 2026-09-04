package nova.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import nova.exception.NovaException;
import nova.task.Deadline;
import nova.task.Event;
import nova.task.Task;
import nova.task.Todo;

public class StorageTest {
    @TempDir
    private Path temporaryDirectory;

    @Test
    public void saveAndLoad_multipleTasks_preservesTasks() throws NovaException {
        Storage storage = new Storage(temporaryDirectory.resolve("tasks.txt").toString());
        Task todo = new Todo("read book");
        Task deadline = new Deadline("submit report", LocalDate.parse("2026-09-05"));
        Task event = new Event("demo", LocalDate.parse("2026-09-06"),
                LocalDate.parse("2026-09-07"));
        deadline.markDone();

        storage.save(List.of(todo, deadline, event));
        List<Task> loadedTasks = storage.load();

        assertEquals(3, loadedTasks.size());
        assertEquals(todo.toDataString(), loadedTasks.get(0).toDataString());
        assertEquals(deadline.toDataString(), loadedTasks.get(1).toDataString());
        assertEquals(event.toDataString(), loadedTasks.get(2).toDataString());
    }

    @Test
    public void load_missingFile_returnsEmptyList() throws NovaException {
        Storage storage = new Storage(temporaryDirectory.resolve("missing.txt").toString());

        assertTrue(storage.load().isEmpty());
    }
}
