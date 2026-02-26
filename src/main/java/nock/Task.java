package nock;

public class Task {
    protected final String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Mark the task as done
     */
    public void markDone() {
        this.isDone = true;
    }

    /**
     * Mark the task as undone
     */
    public void markUndone() {
        this.isDone = false;
    }

    /**
     * Get whether item is done or not
     * @return done or undone status
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Get Description
     * @return description
     */
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
