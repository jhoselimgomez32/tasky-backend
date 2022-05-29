package co.edu.uan.software.tasky.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
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

    TagEntity tag1 = new TagEntity("tag1", "black");

    @BeforeEach
    public void init() {
        tag1 = repo.save(new TagEntity("tag1", "black"));
    }

    @Test
    public void createTag() throws Exception {
        String url = "http://localhost:" + port + "/tags";
        HttpEntity<TagEntity> request = new HttpEntity<>(new TagEntity("tag1", "black"));
        ResponseEntity<TagEntity> response = restTemplate.exchange(url, HttpMethod.POST, request, TagEntity.class);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getUid());
        assertNotNull(response.getBody().getName());
        assertNotNull(response.getBody().getColor());
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("tag1", response.getBody().getName());
        assertEquals("black", response.getBody().getColor());
    }

    @Test
    @Disabled
    public void createInvalidTag() throws Exception {
        String url = "http://localhost:" + port + "/tags";
        HttpEntity<TagEntity> request = new HttpEntity<>(new TagEntity());
        ResponseEntity<TagEntity> response = restTemplate.exchange(url, HttpMethod.POST, request, TagEntity.class);
        assertEquals(400, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    public void getTagsList() throws Exception {
        String url = "http://localhost:" + port + "/tags";
        ResponseEntity<List<TagEntity>> response = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<TagEntity>>() {
                });
        assertNotNull(response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void getTag() throws Exception {
        String url = "http://localhost:" + port + "/tags/" + tag1.getUid().toString();
        ResponseEntity<TagEntity> response = restTemplate.exchange(url, HttpMethod.GET, null, TagEntity.class);
        assertNotNull(response.getBody());
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("tag1", response.getBody().getName());
        assertEquals("black", response.getBody().getColor());
    }

    @Test
    public void getNonExistingTag() throws Exception {
        String url = "http://localhost:" + port + "/tags/8be63de2-a6d5-4a00-b441-102de43adbfa";
        ResponseEntity<TagEntity> response = restTemplate.exchange(url, HttpMethod.GET, null, TagEntity.class);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void updateTag() throws Exception {
        String url = "http://localhost:" + port + "/tags/" + tag1.getUid().toString();
        HttpEntity<TagEntity> request = new HttpEntity<>(new TagEntity("tag1", "black"));
        ResponseEntity<TagEntity> response = restTemplate.exchange(url, HttpMethod.PUT, request, TagEntity.class);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getUid());
        assertEquals("tag1", response.getBody().getName());
        assertEquals("black", response.getBody().getColor());
    }

    @Test
    public void updateNonExisting() throws Exception {
        String url = "http://localhost:" + port + "/tags/8be63de2-a6d5-4a00-b441-102de43adbfa";
        HttpEntity<TagEntity> request = new HttpEntity<>(new TagEntity("tag1", "black"));
        ResponseEntity<TagEntity> response = restTemplate.exchange(url, HttpMethod.PUT, request, TagEntity.class);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void deleteTag() throws Exception {
        String url = "http://localhost:" + port + "/tags/" + tag1.getUid().toString();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
        assertEquals(200, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void deleteNonExistingTag() throws Exception {
        String url = "http://localhost:" + port + "/tags/8be63de2-a6d5-4a00-b441-102de43adbfa";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }
}
