package nock;

import java.util.Scanner;
import java.util.List;


public class Ui {
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Next few methods are for GUI
     * @return String
     */
    public String formatWelcome() {
        return "Hello! I'm Nock\nWhat can I do for you?";
    }

    /**
     * Show goodbye message
     * @return goodbye message
     */
    public String formatGoodbye() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Show help message
     * @return help command
     */
    public String formatHelp() {
        return "Nock Help\n"
                + "Commands:\n"
                + "  list\n"
                + "  todo <description>\n"
                + "  deadline <description> /by <yyyy-mm-dd>\n"
                + "  event <description> /from <start> /to <end>\n"
                + "  mark <index>\n"
                + "  unmark <index>\n"
                + "  delete <index>\n"
                + "  find <keyword>\n"
                + "  help\n"
                + "  bye";
    }

    /**
     * Show tasks
     * @param tasks
     * @return tasks
     */
    public String formatTasks(List<Task> tasks) {
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:");
        if (tasks.isEmpty()) {
            sb.append("\n(no tasks)");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                sb.append("\n").append(i + 1).append(". ").append(tasks.get(i));
            }
        }
        return sb.toString();
    }

    /**
     * Show add message
     * @param task
     * @param taskCount
     * @return add message
     */
    public String formatAdded(Task task, int taskCount) {
        return "Got it. I've added this task:\n"
                + "  " + task + "\n"
                + "Now you have " + taskCount + " tasks in the list.";
    }

    /**
     * Show mark message
     * @param task
     * @return mark message
     */
    public String formatMarked(Task task) {
        return "Nice! I've marked this task as done:\n"
                + "  " + task;
    }

    /**
     * Unmark item message
     * @param task
     * @return unmark message
     */
    public String formatUnmarked(Task task) {
        return "OK, I've marked this task as not done yet:\n"
                + "  " + task;
    }

    /**
     * Delete message
     * @param task
     * @param taskCount
     * @return delete message
     */
    public String formatDeleted(Task task, int taskCount) {
        return "Noted. I've removed this task:\n"
                + "  " + task + "\n"
                + "Now you have " + taskCount + " tasks in the list.";
    }

    /**
     * Find results
     * @param matches
     * @return results found
     */
    public String formatFindResults(List<Task> matches) {
        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:");
        if (matches.isEmpty()) {
            sb.append("\n(no matches)");
        } else {
            for (int i = 0; i < matches.size(); i++) {
                sb.append("\n").append(i + 1).append(". ").append(matches.get(i));
            }
        }
        return sb.toString();
    }
}

