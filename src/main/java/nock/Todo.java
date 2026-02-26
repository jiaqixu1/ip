package nock;

public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    /**
     * Print the todo string
     * @return todo string
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

