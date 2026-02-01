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
                    System.out.println((i + 1) + "." + tasks[i].toDisplayString());
                }
                continue;
            }

            if (input.startsWith("mark ")) {
                int index = parseTaskNumber(input.substring(5)) - 1;
                if (isValidIndex(index, taskCount)) {
                    tasks[index].markDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + tasks[index].toDisplayString());
                }
                continue;
            }

            if (input.startsWith("unmark ")) {
                int index = parseTaskNumber(input.substring(7)) - 1;
                if (isValidIndex(index, taskCount)) {
                    tasks[index].markUndone();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + tasks[index].toDisplayString());
                }
                continue;
            }

            // Otherwise: treat input as a new task
            tasks[taskCount] = new Task(input);
            taskCount++;
            System.out.println("added: " + input);
        }

        scanner.close();
    }

    private static int parseTaskNumber(String s) {
        return Integer.parseInt(s.trim());
    }

    private static boolean isValidIndex(int index, int taskCount) {
        return index >= 0 && index < taskCount;
    }
}
