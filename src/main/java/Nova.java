import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Nova {
    private static final String HORIZONTAL_LINE = "____________________________________________________________";

    public static void main(String[] args) {
        System.out.println("Hello! I'm Nova.");
        System.out.println("What can I do for you?");

        List<String> tasks = new ArrayList<>();
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
            } else {
                tasks.add(input);
                System.out.println(HORIZONTAL_LINE);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + input);
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                System.out.println(HORIZONTAL_LINE);
            }
        }
    }
}
