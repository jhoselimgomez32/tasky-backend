package co.edu.uan.software.tasky.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import co.edu.uan.software.tasky.repositories.TaskListRepository;

@SpringBootTest()
public class TaskListEntityTest {
    @Autowired
    private TaskListRepository repo;

    @Test
    public void unsavedToString() {
        TaskListEntity tasklist = new TaskListEntity("john.doe", "userid");
        String expected = "TaskList : john.doe";
        assertEquals(expected, tasklist.toString());
    }

    @Test
    public void savedToString() {
        TaskListEntity tasklist = new TaskListEntity("john.doe", "userid");
        repo.save(tasklist);
        String expected = "TaskList : john.doe";
        assertEquals(expected, tasklist.toString());
    }
}
