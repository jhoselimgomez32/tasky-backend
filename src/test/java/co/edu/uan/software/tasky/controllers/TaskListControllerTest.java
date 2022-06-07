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
import co.edu.uan.software.tasky.entities.TaskListEntity;
import co.edu.uan.software.tasky.repositories.TaskListRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TaskListControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private TaskListRepository repo;

    TaskListEntity taskList = new TaskListEntity("List1", "romina");

    @BeforeEach
    public void init() {
        taskList = repo.save(new TaskListEntity("List1", "romina"));
    }

    @Test
    public void createTaskList() throws Exception {
        String url = "http://localhost:" + port + "/tasklist";
        HttpEntity<TaskListEntity> request = new HttpEntity<>(new TaskListEntity("List1", "romina"));
        ResponseEntity<TaskListEntity> response = restTemplate.exchange(url, HttpMethod.POST, request, TaskListEntity.class);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertNotNull(response.getBody().getName());
        assertNotNull(response.getBody().getUserId());
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("List1", response.getBody().getName());
        assertEquals("romina", response.getBody().getUserId());
    }
}
