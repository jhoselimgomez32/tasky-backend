package co.edu.uan.software.tasky.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import co.edu.uan.software.tasky.repositories.TagRepository;

@SpringBootTest()
public class TagEntityTest {
    @Autowired
    private TagRepository repo;

    @Test
    public void unsavedToString() {
        TagEntity tag = new TagEntity("tag1", "black");
        String expected = "Tag: { name: tag1, color: black }";
        assertEquals(expected, tag.toString());
    }

    @Test
    public void savedToString() {
        TagEntity tag = new TagEntity("tag1", "black");
        repo.save(tag);
        String expected = "Tag: { name: tag1, color: black }";
        assertEquals(expected, tag.toString());
    }
}
