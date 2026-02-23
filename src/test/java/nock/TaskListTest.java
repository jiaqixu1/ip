package nock;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class TaskListTest {

    @Test
    public void addTask_taskListSizeIncreases() {
        TaskList list = new TaskList(new ArrayList<>());
        Todo todo = new Todo("read book");

        list.add(todo);

        assertEquals(1, list.size());
    }


}