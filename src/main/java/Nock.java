import java.util.Scanner;

public class Nock {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Task[] tasks = new Task[100];
        int taskCount = 0;

        System.out.println("Hello! I'm Nock");
        System.out.println("What can I do for you?");

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            }

            if (input.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + "." + tasks[i]);
                }
                continue;
            }

            if (input.startsWith("mark ")) {
                int index = Integer.parseInt(input.substring(5).trim()) - 1;
                if (index >= 0 && index < taskCount) {
                    tasks[index].markDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + tasks[index]);
                }
                continue;
            }

            if (input.startsWith("unmark ")) {
                int index = Integer.parseInt(input.substring(7).trim()) - 1;
                if (index >= 0 && index < taskCount) {
                    tasks[index].markUndone();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + tasks[index]);
                }
                continue;
            }

            if (input.startsWith("todo ")) {
                String description = input.substring(5).trim();
                tasks[taskCount] = new Todo(description);
                taskCount++;
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[taskCount - 1]);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                continue;
            }

            if (input.startsWith("deadline ")) {
                String rest = input.substring(9).trim(); // after "deadline "
                String[] parts = rest.split(" /by ", 2);
                String description = parts[0].trim();
                String by = (parts.length == 2) ? parts[1].trim() : "";
                tasks[taskCount] = new Deadline(description, by);
                taskCount++;
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[taskCount - 1]);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                continue;
            }

            if (input.startsWith("event ")) {
                String rest = input.substring(6).trim(); // after "event "
                String[] firstSplit = rest.split(" /from ", 2);
                String description = firstSplit[0].trim();

                String from = "";
                String to = "";

                if (firstSplit.length == 2) {
                    String[] secondSplit = firstSplit[1].split(" /to ", 2);
                    from = secondSplit[0].trim();
                    if (secondSplit.length == 2) {
                        to = secondSplit[1].trim();
                    }
                }

                tasks[taskCount] = new Event(description, from, to);
                taskCount++;
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[taskCount - 1]);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                continue;
            }

            // If you want: ignore unknown commands for now, or treat as todo.
            System.out.println("Please use: todo / deadline / event / list / mark / unmark / bye");
        }

        scanner.close();
    }
}
