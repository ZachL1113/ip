import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Nova {
    private static final String HORIZONTAL_LINE = "____________________________________________________________";

    public static void main(String[] args) {
        System.out.println("Hello! I'm Nova.");
        System.out.println("What can I do for you?");

        List<Task> tasks = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();

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
            } else if (input.startsWith("mark ")) {
                int taskNumber = Integer.parseInt(input.substring(5));
                Task task = tasks.get(taskNumber - 1);
                task.markDone();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + task);
            } else if (input.startsWith("unmark ")) {
                int taskNumber = Integer.parseInt(input.substring(7));
                Task task = tasks.get(taskNumber - 1);
                task.unmarkDone();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + task);
            } else if (input.startsWith("todo ")) {
                Task task = new Todo(input.substring(5));
                tasks.add(task);
                System.out.println(HORIZONTAL_LINE);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + task);
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                System.out.println(HORIZONTAL_LINE);
            } else if (input.startsWith("deadline ")) {
                int byIndex = input.indexOf(" /by ");
                String description = input.substring(9, byIndex);
                String by = input.substring(byIndex + 5);
                Task task = new Deadline(description, by);
                tasks.add(task);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + task);
            } else if (input.startsWith("event ")) {
                int fromIndex = input.indexOf(" /from ");
                int toIndex = input.indexOf(" /to ");
                String description = input.substring(6, fromIndex);
                String from = input.substring(fromIndex + 7, toIndex);
                String to = input.substring(toIndex + 5);
                Task task = new Event(description, from, to);
                tasks.add(task);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + task);
            }
        }
    }
}
