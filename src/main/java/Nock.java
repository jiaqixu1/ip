import java.util.ArrayList;
import java.util.Scanner;

public class Nock {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();

        System.out.println("Hello! I'm Nock");
        System.out.println("What can I do for you?");

        while (true) {
            String input = scanner.nextLine().trim();

            try {
                boolean shouldExit = handleInput(input, tasks);
                if (shouldExit) {
                    break;
                }
            } catch (NockException e) {
                System.out.println(e.getMessage());
            }
        }

        scanner.close();
    }

    private static boolean handleInput(String input, ArrayList<Task> tasks) throws NockException {
        if (input.equals("bye")) {
            System.out.println("Bye. Hope to see you again soon!");
            return true;
        }

        if (input.equals("list")) {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + "." + tasks.get(i));
            }
            return false;
        }

        if (input.startsWith("mark")) {
            int index = parseIndex(input, "mark", tasks.size());
            tasks.get(index).markDone();
            System.out.println("Nice! I've marked this task as done:");
            System.out.println("  " + tasks.get(index));
            return false;
        }

        if (input.startsWith("unmark")) {
            int index = parseIndex(input, "unmark", tasks.size());
            tasks.get(index).markUndone();
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println("  " + tasks.get(index));
            return false;
        }

        // ✅ Level 6: delete
        if (input.startsWith("delete")) {
            int index = parseIndex(input, "delete", tasks.size());
            Task removed = tasks.remove(index); // ArrayList shifts automatically
            System.out.println("Noted. I've removed this task:");
            System.out.println("  " + removed);
            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
            return false;
        }

        if (input.startsWith("todo")) {
            String description = input.substring(4).trim();
            if (description.isEmpty()) {
                throw new NockException("OOPS!!! The description of a todo cannot be empty.");
            }
            Task t = new Todo(description);
            tasks.add(t);
            printAdded(t, tasks.size());
            return false;
        }

        if (input.startsWith("deadline")) {
            String rest = input.substring(8).trim();
            if (rest.isEmpty()) {
                throw new NockException("OOPS!!! The description of a deadline cannot be empty.");
            }
            String[] parts = rest.split(" /by ", 2);
            if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                throw new NockException("OOPS!!! Use: deadline <description> /by <time>");
            }
            Task t = new Deadline(parts[0].trim(), parts[1].trim());
            tasks.add(t);
            printAdded(t, tasks.size());
            return false;
        }

        if (input.startsWith("event")) {
            String rest = input.substring(5).trim();
            if (rest.isEmpty()) {
                throw new NockException("OOPS!!! The description of an event cannot be empty.");
            }
            String[] p1 = rest.split(" /from ", 2);
            if (p1.length < 2 || p1[0].trim().isEmpty()) {
                throw new NockException("OOPS!!! Use: event <description> /from <start> /to <end>");
            }
            String[] p2 = p1[1].split(" /to ", 2);
            if (p2.length < 2 || p2[0].trim().isEmpty() || p2[1].trim().isEmpty()) {
                throw new NockException("OOPS!!! Use: event <description> /from <start> /to <end>");
            }
            Task t = new Event(p1[0].trim(), p2[0].trim(), p2[1].trim());
            tasks.add(t);
            printAdded(t, tasks.size());
            return false;
        }

        throw new NockException("OOPS!!! I'm sorry, but I don't know what that means :-(");
    }

    private static void printAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    private static int parseIndex(String input, String command, int taskCount) throws NockException {
        String numberPart = input.substring(command.length()).trim();
        if (numberPart.isEmpty()) {
            throw new NockException("OOPS!!! Please provide a task number. Example: " + command + " 2");
        }

        int oneBased;
        try {
            oneBased = Integer.parseInt(numberPart);
        } catch (NumberFormatException e) {
            throw new NockException("OOPS!!! Task number must be an integer. Example: " + command + " 2");
        }

        int index = oneBased - 1;
        if (index < 0 || index >= taskCount) {
            throw new NockException("OOPS!!! That task number is out of range.");
        }
        return index;
    }
}
