import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final Path filePath;

    public Storage(String filePath) {
        this.filePath = Path.of(filePath);
    }

    public List<Task> load() throws NovaException {
        List<Task> tasks = new ArrayList<>();
        if (!Files.exists(filePath)) {
            return tasks;
        }

        try {
            for (String line : Files.readAllLines(filePath)) {
                tasks.add(parseTask(line));
            }
            return tasks;
        } catch (IOException | IllegalArgumentException exception) {
            throw new NovaException("I couldn't load the saved tasks.");
        }
    }

    public void save(List<Task> tasks) throws NovaException {
        try {
            Path parent = filePath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            List<String> lines = new ArrayList<>();
            for (Task task : tasks) {
                lines.add(task.toDataString());
            }
            Files.write(filePath, lines);
        } catch (IOException exception) {
            throw new NovaException("I couldn't save the tasks.");
        }
    }

    private Task parseTask(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid saved task");
        }

        Task task = switch (parts[0]) {
            case "T" -> new Todo(parts[2]);
            case "D" -> new Deadline(parts[2], parts[3]);
            case "E" -> new Event(parts[2], parts[3], parts[4]);
            default -> throw new IllegalArgumentException("Unknown task type");
        };
        if (parts[1].equals("1")) {
            task.markDone();
        }
        return task;
    }
}
