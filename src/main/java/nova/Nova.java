package nova;

import nova.command.Command;
import nova.exception.NovaException;
import nova.parser.Parser;
import nova.storage.Storage;
import nova.task.Task;
import nova.task.TaskList;
import nova.ui.Ui;

/**
 * Runs the Nova task-management chatbot.
 */
public class Nova {
    private final Parser parser;
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    /**
     * Creates a Nova instance backed by the specified data file.
     *
     * @param filePath Path of the file used to store tasks.
     */
    public Nova(String filePath) {
        parser = new Parser();
        storage = new Storage(filePath);
        ui = new Ui();

        TaskList loadedTasks;
        try {
            loadedTasks = new TaskList(storage.load());
        } catch (NovaException exception) {
            ui.showError(exception.getMessage());
            loadedTasks = new TaskList();
        }
        tasks = loadedTasks;
    }

    /**
     * Starts the command-processing loop.
     */
    public void run() {
        ui.showWelcome();
        while (ui.hasNextCommand()) {
            String input = ui.readCommand();
            try {
                Command command = parser.parseCommand(input);
                if (execute(command, input)) {
                    return;
                }
            } catch (NovaException exception) {
                ui.showError(exception.getMessage());
            }
        }
    }

    private boolean execute(Command command, String input) throws NovaException {
        switch (command) {
            case BYE:
                ui.showGoodbye();
                return true;
            case LIST:
                ui.showTaskList(tasks);
                break;
            case FIND:
                ui.showMatchingTasks(tasks.find(parser.parseFindKeyword(input)));
                break;
            case MARK:
                Task markedTask = tasks.mark(parser.parseTaskNumber(input, "mark", tasks.size()));
                storage.save(tasks.asList());
                ui.showMarked(markedTask);
                break;
            case UNMARK:
                Task unmarkedTask = tasks.unmark(parser.parseTaskNumber(input, "unmark", tasks.size()));
                storage.save(tasks.asList());
                ui.showUnmarked(unmarkedTask);
                break;
            case DELETE:
                Task removedTask = tasks.delete(parser.parseTaskNumber(input, "delete", tasks.size()));
                storage.save(tasks.asList());
                ui.showDeleted(removedTask, tasks.size());
                break;
            case TODO:
            case DEADLINE:
            case EVENT:
                Task task = parser.parseTask(input, command);
                tasks.add(task);
                storage.save(tasks.asList());
                ui.showAdded(task, tasks.size());
                break;
            default:
                throw new NovaException("I'm sorry, but I don't know what that means.");
        }
        return false;
    }

    /**
     * Starts Nova using the default data-file location.
     *
     * @param args Command-line arguments; not used.
     */
    public static void main(String[] args) {
        new Nova("data/nova.txt").run();
    }
}
