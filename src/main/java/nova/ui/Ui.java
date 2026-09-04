package nova.ui;

import java.util.Scanner;

import nova.task.Task;
import nova.task.TaskList;

/**
 * Handles console input and output for Nova.
 */
public class Ui {
    private static final String HORIZONTAL_LINE =
            "____________________________________________________________";

    private final Scanner scanner;

    /**
     * Creates a console user interface.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Returns whether another command is available from standard input.
     *
     * @return True if another command can be read.
     */
    public boolean hasNextCommand() {
        return scanner.hasNextLine();
    }

    /**
     * Returns the next command from standard input.
     *
     * @return Next command.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Shows the welcome message.
     */
    public void showWelcome() {
        System.out.println("Hello! I'm Nova.");
        System.out.println("What can I do for you?");
    }

    /**
     * Shows the goodbye message.
     */
    public void showGoodbye() {
        System.out.println("Bye! See you next time.");
    }

    /**
     * Shows a user-facing error message.
     *
     * @param message Explanation of the error.
     */
    public void showError(String message) {
        System.out.println("OOPS!!! " + message);
    }

    /**
     * Shows all tasks in the list.
     *
     * @param tasks Tasks to show.
     */
    public void showTaskList(TaskList tasks) {
        showLine();
        System.out.println("Here are the tasks in your list:");
        for (int i = 1; i <= tasks.size(); i++) {
            System.out.println(i + ". " + tasks.get(i));
        }
        showLine();
    }

    /**
     * Shows tasks that match a search keyword.
     *
     * @param tasks Matching tasks.
     */
    public void showMatchingTasks(TaskList tasks) {
        showLine();
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 1; i <= tasks.size(); i++) {
            System.out.println(i + ". " + tasks.get(i));
        }
        showLine();
    }

    /**
     * Shows a task that was marked as completed.
     *
     * @param task Marked task.
     */
    public void showMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
    }

    /**
     * Shows a task that was marked as incomplete.
     *
     * @param task Unmarked task.
     */
    public void showUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
    }

    /**
     * Shows a deleted task and the remaining task count.
     *
     * @param task Deleted task.
     * @param taskCount Number of remaining tasks.
     */
    public void showDeleted(Task task, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Shows an added task and the updated task count.
     *
     * @param task Added task.
     * @param taskCount Updated task count.
     */
    public void showAdded(Task task, int taskCount) {
        showLine();
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        showLine();
    }

    private void showLine() {
        System.out.println(HORIZONTAL_LINE);
    }
}
