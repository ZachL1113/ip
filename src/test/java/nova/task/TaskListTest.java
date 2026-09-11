package nova.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class TaskListTest {
    @Test
    public void sortByDescription_mixedCaseDescriptions_sortsAlphabeticallyIgnoringCase() {
        TaskList tasks = new TaskList(List.of(
                new Todo("write report"),
                new Todo("Buy milk"),
                new Todo("attend meeting")));

        tasks.sortByDescription();

        assertEquals("[T][ ] attend meeting", tasks.get(1).toString());
        assertEquals("[T][ ] Buy milk", tasks.get(2).toString());
        assertEquals("[T][ ] write report", tasks.get(3).toString());
    }
}
