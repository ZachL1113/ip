import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Nova {
    private static final String HORIZONTAL_LINE = "____________________________________________________________";

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

    private static Task createTask(String input) throws NovaException {
        if (input.equals("todo") || input.startsWith("todo ")) {
            String description = requireValue(input.substring(4), "A todo needs a description.");
            return new Todo(description);
        }
        if (input.equals("deadline") || input.startsWith("deadline ")) {
            String details = input.substring(8).trim();
            int byIndex = details.indexOf("/by");
            if (byIndex < 0) {
                throw new NovaException("A deadline needs a /by date or time.");
            }
            String description = requireValue(details.substring(0, byIndex),
                    "A deadline needs a description.");
            String by = requireValue(details.substring(byIndex + 3),
                    "A deadline needs a /by date or time.");
            return new Deadline(description, by);
        }
        if (input.equals("event") || input.startsWith("event ")) {
            String details = input.substring(5).trim();
            int fromIndex = details.indexOf("/from");
            int toIndex = details.indexOf("/to");
            if (fromIndex < 0 || toIndex < 0 || toIndex <= fromIndex) {
                throw new NovaException("An event needs /from and /to date or time values.");
            }
            String description = requireValue(details.substring(0, fromIndex),
                    "An event needs a description.");
            String from = requireValue(details.substring(fromIndex + 5, toIndex),
                    "An event needs a /from date or time.");
            String to = requireValue(details.substring(toIndex + 3),
                    "An event needs a /to date or time.");
            return new Event(description, from, to);
        }
        throw new NovaException("I'm sorry, but I don't know what that means.");
    }

    public static void main(String[] args) {
        System.out.println("Hello! I'm Nova.");
        System.out.println("What can I do for you?");

        List<Task> tasks = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();

            try {
                if (input.equals("bye")) {
                    System.out.println("Bye! See you next time.");
                    break;
                } else if (input.equals("list")) {
                    System.out.println(HORIZONTAL_LINE);
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i));
                    }
                    System.out.println(HORIZONTAL_LINE);
                } else if (input.equals("mark") || input.startsWith("mark ")) {
                    int taskNumber = parseTaskNumber(input, "mark", tasks.size());
                    Task task = tasks.get(taskNumber - 1);
                    task.markDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + task);
                } else if (input.equals("unmark") || input.startsWith("unmark ")) {
                    int taskNumber = parseTaskNumber(input, "unmark", tasks.size());
                    Task task = tasks.get(taskNumber - 1);
                    task.unmarkDone();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + task);
                } else {
                    Task task = createTask(input);
                    tasks.add(task);
                    System.out.println(HORIZONTAL_LINE);
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + task);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    System.out.println(HORIZONTAL_LINE);
                }
            } catch (NovaException exception) {
                System.out.println("OOPS!!! " + exception.getMessage());
            }
        }
    }
}
