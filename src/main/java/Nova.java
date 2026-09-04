import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Nova {
    private static final String HORIZONTAL_LINE = "____________________________________________________________";

    private enum Command {
        BYE, LIST, MARK, UNMARK, DELETE, TODO, DEADLINE, EVENT, UNKNOWN;

        private static Command from(String input) {
            String[] parts = input.trim().split("\\s+", 2);
            return switch (parts[0]) {
                case "bye" -> BYE;
                case "list" -> LIST;
                case "mark" -> MARK;
                case "unmark" -> UNMARK;
                case "delete" -> DELETE;
                case "todo" -> TODO;
                case "deadline" -> DEADLINE;
                case "event" -> EVENT;
                default -> UNKNOWN;
            };
        }
    }

    private static int parseTaskNumber(String input, String command, int taskCount) throws NovaException {
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

    private static String requireValue(String value, String errorMessage) throws NovaException {
        String trimmedValue = value.trim();
        if (trimmedValue.isEmpty()) {
            throw new NovaException(errorMessage);
        }
        return trimmedValue;
    }

    private static Task createTask(String input, Command command) throws NovaException {
        return switch (command) {
            case TODO -> {
                String description = requireValue(input.substring(4), "A todo needs a description.");
                yield new Todo(description);
            }
            case DEADLINE -> {
                String deadlineDetails = input.substring(8).trim();
                String[] deadlineParts = deadlineDetails.split("\\s+/by\\s+", 2);
                if (deadlineParts.length < 2) {
                    throw new NovaException("A deadline needs a /by date or time.");
                }
                String description = requireValue(deadlineParts[0],
                        "A deadline needs a description.");
                String by = requireValue(deadlineParts[1],
                        "A deadline needs a /by date or time.");
                yield new Deadline(description, by);
            }
            case EVENT -> {
                String eventDetails = input.substring(5).trim();
                String[] fromParts = eventDetails.split("\\s+/from\\s+", 2);
                if (fromParts.length < 2) {
                    throw new NovaException("An event needs /from and /to date or time values.");
                }
                String[] toParts = fromParts[1].split("\\s+/to\\s+", 2);
                if (toParts.length < 2) {
                    throw new NovaException("An event needs /from and /to date or time values.");
                }
                String description = requireValue(fromParts[0],
                        "An event needs a description.");
                String from = requireValue(toParts[0],
                        "An event needs a /from date or time.");
                String to = requireValue(toParts[1],
                        "An event needs a /to date or time.");
                yield new Event(description, from, to);
            }
            default -> throw new NovaException("I'm sorry, but I don't know what that means.");
        };
    }

    public static void main(String[] args) {
        System.out.println("Hello! I'm Nova.");
        System.out.println("What can I do for you?");

        List<Task> tasks = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();

            try {
                Command command = Command.from(input);
                switch (command) {
                    case BYE -> {
                        System.out.println("Bye! See you next time.");
                        return;
                    }
                    case LIST -> {
                        System.out.println(HORIZONTAL_LINE);
                        System.out.println("Here are the tasks in your list:");
                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.println((i + 1) + ". " + tasks.get(i));
                        }
                        System.out.println(HORIZONTAL_LINE);
                    }
                    case MARK -> {
                        int taskNumber = parseTaskNumber(input, "mark", tasks.size());
                        Task task = tasks.get(taskNumber - 1);
                        task.markDone();
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println("  " + task);
                    }
                    case UNMARK -> {
                        int taskNumber = parseTaskNumber(input, "unmark", tasks.size());
                        Task task = tasks.get(taskNumber - 1);
                        task.unmarkDone();
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println("  " + task);
                    }
                    case DELETE -> {
                        int taskNumber = parseTaskNumber(input, "delete", tasks.size());
                        Task removedTask = tasks.remove(taskNumber - 1);
                        System.out.println("Noted. I've removed this task:");
                        System.out.println("  " + removedTask);
                        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    }
                    case TODO, DEADLINE, EVENT -> {
                        Task task = createTask(input, command);
                        tasks.add(task);
                        System.out.println(HORIZONTAL_LINE);
                        System.out.println("Got it. I've added this task:");
                        System.out.println("  " + task);
                        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                        System.out.println(HORIZONTAL_LINE);
                    }
                    default -> throw new NovaException("I'm sorry, but I don't know what that means.");
                }
            } catch (NovaException exception) {
                System.out.println("OOPS!!! " + exception.getMessage());
            }
        }
    }
}
