package nock;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Returns the internal list of tasks.
     *
     * @return The {@code ArrayList} containing all tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The total number of tasks.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Adds a task to the list.
     *
     * @param t The {@code Task} to be added.
     */
    public void add(Task t) {
        tasks.add(t);
    }

    /**
     * Retrieves the task at the specified index.
     *
     * @param index The index of the task (0-based).
     * @return The {@code Task} at the given index.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Removes and returns the task at the specified index.
     *
     * @param index The index of the task to remove (0-based).
     * @return The removed {@code Task}.
     */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    /**
     * Finds all tasks whose descriptions contain the given keyword.
     * <p>
     * The search is case-insensitive.
     *
     * @param keyword The keyword to search for.
     * @return A {@code List} of tasks that match the keyword.
     */
    public List<Task> findTasks(String keyword) {
        String key = keyword.toLowerCase();
        List<Task> matches = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getDescription().toLowerCase().contains(key)) {
                matches.add(t);
            }
        }
        return matches;
    }
}
