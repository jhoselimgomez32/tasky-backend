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

import co.edu.uan.software.tasky.entities.DummyEntity;
import co.edu.uan.software.tasky.repositories.DummyRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DummyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private DummyRepository repo;

    @BeforeEach
    public void init() {
        repo.save(new DummyEntity("Singleton"));
        repo.save(new DummyEntity("Singleton"));
    }

    @Test
    public void createDummy() throws Exception {
        String url = "http://localhost:"+port+"/dummies";
        HttpEntity<DummyEntity> request = new HttpEntity<>(new DummyEntity("Lorem Ipsum"));
        ResponseEntity<DummyEntity> response = restTemplate.exchange(url, HttpMethod.POST, request, DummyEntity.class);
        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals(request.getBody().getDummyValue(), response.getBody().getDummyValue());
    }

    @Test
    @Disabled
    public void createInvalidDummy() throws Exception {
        String url = "http://localhost:"+port+"/dummies";
        HttpEntity<DummyEntity> request = new HttpEntity<>(new DummyEntity());
        ResponseEntity<DummyEntity> response = restTemplate.exchange(url, HttpMethod.POST, request, DummyEntity.class);
        assertEquals(400, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    public void getDummyList() throws Exception {
        String url = "http://localhost:"+port+"/dummies";
        ResponseEntity<List<DummyEntity>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<DummyEntity>>(){});
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    public void getDummy() throws Exception {
        String url = "http://localhost:"+port+"/dummies/1";
        ResponseEntity<DummyEntity> response = restTemplate.exchange(url, HttpMethod.GET, null, DummyEntity.class);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    public void getNonExistingDummy() throws Exception {
        String url = "http://localhost:"+port+"/dummies/100";
        ResponseEntity<DummyEntity> response = restTemplate.exchange(url, HttpMethod.GET, null, DummyEntity.class);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void updateDummy() throws Exception {
        String url = "http://localhost:"+port+"/dummies/1";
        HttpEntity<DummyEntity> request = new HttpEntity<>(new DummyEntity("Lorem Ipsum"));
        ResponseEntity<DummyEntity> response = restTemplate.exchange(url, HttpMethod.PUT, request, DummyEntity.class);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals(request.getBody().getDummyValue(), response.getBody().getDummyValue());
    }

    @Test
    public void deleteDummy() throws Exception {
        String url = "http://localhost:"+port+"/dummies/2";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
        assertEquals(200, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void deleteNonExistingDummy() throws Exception {
        String url = "http://localhost:"+port+"/dummies/100";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }


}