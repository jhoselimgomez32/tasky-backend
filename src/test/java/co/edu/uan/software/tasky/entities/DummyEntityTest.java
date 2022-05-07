package co.edu.uan.software.tasky.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import co.edu.uan.software.tasky.repositories.DummyRepository;

@SpringBootTest()
public class DummyEntityTest {

    @Autowired
    private DummyRepository repo;

    @Test
    public void unsavedToString() {
        DummyEntity de = new DummyEntity("Lorem Ipsum");
        String expected = "Dummy null:Lorem Ipsum";
        assertEquals(expected, de.toString());
    }

    @Test
    public void savedToString() {
        DummyEntity de = new DummyEntity("Lorem Ipsum");
        repo.save(de);
        String expected = "Dummy "+de.getId().toString()+":Lorem Ipsum";
        assertEquals(expected, de.toString());
    }
}
