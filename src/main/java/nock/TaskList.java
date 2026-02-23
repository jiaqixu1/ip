package nock;

import java.util.ArrayList;
/**
 * Represents a list of tasks.
 * Provides operations to add, delete, and retrieve tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Find the number of tasks
     * @return
     */
    public int size() {
        return tasks.size();
    }
    /**
     * Adds a task to the list.
     *
     * @param t the task to be added
     */
    public void add(Task t) {
        tasks.add(t);
    }

    /**
     * Get task index
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Delete tasks
     * @param index
     * @return
     */
    public Task remove(int index) {
        return tasks.remove(index);
    }
}
