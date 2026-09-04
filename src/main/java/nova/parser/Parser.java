package nova.parser;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import nova.command.Command;
import nova.exception.NovaException;
import nova.task.Deadline;
import nova.task.Event;
import nova.task.Task;
import nova.task.Todo;

/**
 * Parses user input into commands and task data.
 */
public class Parser {
    /**
     * Returns the command type represented by the input.
     *
     * @param input Full user input.
     * @return Parsed command type.
     */
    public Command parseCommand(String input) {
        String[] parts = input.trim().split("\\s+", 2);
        return switch (parts[0]) {
            case "bye" -> Command.BYE;
            case "list" -> Command.LIST;
            case "mark" -> Command.MARK;
            case "unmark" -> Command.UNMARK;
            case "delete" -> Command.DELETE;
            case "todo" -> Command.TODO;
            case "deadline" -> Command.DEADLINE;
            case "event" -> Command.EVENT;
            case "find" -> Command.FIND;
            default -> Command.UNKNOWN;
        };
    }

    /**
     * Returns the keyword supplied to a find command.
     *
     * @param input Full user input.
     * @return Search keyword.
     * @throws NovaException If the keyword is missing.
     */
    public String parseFindKeyword(String input) throws NovaException {
        return requireValue(input.substring(4), "A find command needs a keyword.");
    }

    /**
     * Returns a validated one-based task number.
     *
     * @param input Full user input.
     * @param command Command word preceding the number.
     * @param taskCount Number of available tasks.
     * @return Validated task number.
     * @throws NovaException If the number is missing, invalid, or out of range.
     */
    public int parseTaskNumber(String input, String command, int taskCount) throws NovaException {
        String numberText = input.substring(command.length()).trim();
        int taskNumber;
        try {
            taskNumber = Integer.parseInt(numberText);
        } catch (NumberFormatException exception) {
            throw new NovaException("Please provide a valid task number.");
        }
        if (taskNumber < 1 || taskNumber > taskCount) {
            throw new NovaException("That task number does not exist.");
        }
        return taskNumber;
    }

    /**
     * Returns a task parsed from an add command.
     *
     * @param input Full user input.
     * @param command Type of task to create.
     * @return Parsed task.
     * @throws NovaException If required task details are missing or invalid.
     */
    public Task parseTask(String input, Command command) throws NovaException {
        return switch (command) {
            case TODO -> new Todo(requireValue(input.substring(4), "A todo needs a description."));
            case DEADLINE -> parseDeadline(input);
            case EVENT -> parseEvent(input);
            default -> throw new NovaException("I'm sorry, but I don't know what that means.");
        };
    }

    private Task parseDeadline(String input) throws NovaException {
        String[] parts = input.substring(8).trim().split("\\s+/by\\s+", 2);
        if (parts.length < 2) {
            throw new NovaException("A deadline needs a /by date or time.");
        }
        String description = requireValue(parts[0], "A deadline needs a description.");
        String by = requireValue(parts[1], "A deadline needs a /by date or time.");
        return new Deadline(description, parseDate(by));
    }

    private Task parseEvent(String input) throws NovaException {
        String[] fromParts = input.substring(5).trim().split("\\s+/from\\s+", 2);
        if (fromParts.length < 2) {
            throw new NovaException("An event needs /from and /to date or time values.");
        }
        String[] toParts = fromParts[1].split("\\s+/to\\s+", 2);
        if (toParts.length < 2) {
            throw new NovaException("An event needs /from and /to date or time values.");
        }
        String description = requireValue(fromParts[0], "An event needs a description.");
        String from = requireValue(toParts[0], "An event needs a /from date or time.");
        String to = requireValue(toParts[1], "An event needs a /to date or time.");
        return new Event(description, parseDate(from), parseDate(to));
    }

    private LocalDate parseDate(String value) throws NovaException {
        try {
            return LocalDate.parse(value);
        } catch (DateTimeParseException exception) {
            throw new NovaException("Please use the date format yyyy-MM-dd.");
        }
    }

    private String requireValue(String value, String errorMessage) throws NovaException {
        String trimmedValue = value.trim();
        if (trimmedValue.isEmpty()) {
            throw new NovaException(errorMessage);
        }
        return trimmedValue;
    }
}
