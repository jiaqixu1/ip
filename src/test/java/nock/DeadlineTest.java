package nock;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class DeadlineTest {

    @Test
    public void markDone_setsStatusCorrectly() {
        Deadline d = new Deadline("return book",
                LocalDate.of(2019, 12, 2));

        d.markDone();

        assertTrue(d.toString().contains("[X]"));
    }
}