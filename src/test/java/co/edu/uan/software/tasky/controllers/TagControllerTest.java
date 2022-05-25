package co.edu.uan.software.tasky.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import co.edu.uan.software.tasky.entities.TagEntity;
import co.edu.uan.software.tasky.repositories.TagRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TagControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private TagRepository repo;

    TagEntity tag1 = new TagEntity("john.doe", "black");
    TagEntity tag2 = new TagEntity("john.doe", "black");

    @BeforeEach
    public void init() {
        tag1 = repo.save(new TagEntity("john.doe", "black"));
        tag2 = repo.save(new TagEntity("john.doe", "black"));
    }

    @Test
    public void createTag() throws Exception {
        String url = "http://localhost:" + port + "/tags";
        HttpEntity<TagEntity> request = new HttpEntity<>(new TagEntity("john.doe", "black"));
        ResponseEntity<TagEntity> response = restTemplate.exchange(url, HttpMethod.POST, request, TagEntity.class);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getUid());
        assertNotNull(response.getBody().getName());
        assertNotNull(response.getBody().getColor());
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("john.doe", response.getBody().getName());
        assertEquals("black", response.getBody().getColor());
    }
}
