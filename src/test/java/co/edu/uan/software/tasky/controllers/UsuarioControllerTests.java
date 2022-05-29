package co.edu.uan.software.tasky.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import co.edu.uan.software.tasky.entities.Usuario;
import co.edu.uan.software.tasky.repositories.UsuarioRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UsuarioControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UsuarioRepository repo;

    Usuario usuario1 = new Usuario("john.doe", "strongpassword");
    Usuario usuario2 = new Usuario("john.doe", "strongpassword");

    @BeforeEach
    public void init() {
        repo.deleteAll();
        usuario1 = repo.save(new Usuario("john.doe", "strongpassword"));
        usuario2 = repo.save(new Usuario("john.doe", "strongpassword"));
    }

    @ParameterizedTest
    @CsvSource({"john.doe,strongpassword"})
    public void createUsuario(String username, String password) throws Exception {
        String url = "http://localhost:" + port + "/usuarios";
        HttpEntity<Usuario> request = new HttpEntity<>(new Usuario(username, password));
        ResponseEntity<Usuario> response = restTemplate.exchange(url, HttpMethod.POST, request, Usuario.class);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getUid());
        assertNotNull(response.getBody().getFechaCreacion());
        assertNotNull(response.getBody().getContrasena());
        assertNotNull(response.getBody().getNombreUsuario());
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("john.doe", response.getBody().getNombreUsuario());
        // password is hashed with MD5
        assertEquals("F93FC10472A31BB3061AA0B45E228C5A", response.getBody().getContrasena());
    }

    @ParameterizedTest
    @CsvSource({
        "john.doe,", // password null
        "john.doe,''", // password empty
        ",strongpassword", // username null
        "'',strongpassword", // username empty
        "'',", // username empty and password null
        ",''", // username null and password empty
        "'',''", // both  empty
        "," // both null
    })
    public void createInvalidUsuario(String username, String password) throws Exception {
        String url = "http://localhost:" + port + "/usuarios";
        HttpEntity<Usuario> request = new HttpEntity<>(new Usuario(username, password));
        ResponseEntity<Usuario> response = restTemplate.exchange(url, HttpMethod.POST, request, Usuario.class);
        assertEquals(400, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void getUsuarioList() throws Exception {
        String url = "http://localhost:" + port + "/usuarios";
        ResponseEntity<List<Usuario>> response = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Usuario>>() {
                });
        assertNotNull(response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void authenticate() throws Exception {
        String url = "http://localhost:" + port + "/auth";
        HttpEntity<Usuario> request = new HttpEntity<>(new Usuario("john.doe", "strongpassword"));
        ResponseEntity<Usuario> response = restTemplate.exchange(url, HttpMethod.POST, request, Usuario.class);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getUid());
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("john.doe", response.getBody().getNombreUsuario());
        assertNull(response.getBody().getContrasena());
        System.out.println(response.getBody());
    }

    @ParameterizedTest
    @CsvSource({
        "john.doe,", // password null
        "john.doe,''", // password empty
        ",strongpassword", // username null
        "'',strongpassword", // username empty
        "'',", // username empty and password null
        ",''", // username null and password empty
        "'',''", // both  empty
        "," // both null
    })
    public void invalidAuthenticate(String username, String password) throws Exception {
        String url = "http://localhost:" + port + "/auth";
        HttpEntity<Usuario> request = new HttpEntity<>(new Usuario(username, password));
        ResponseEntity<Usuario> response = restTemplate.exchange(url, HttpMethod.POST, request, Usuario.class);
        assertNull(response.getBody());
        assertEquals(400, response.getStatusCodeValue());
    }

    @ParameterizedTest
    @CsvSource({
        "nonexistinguser,password", // invalid user
        "john.doe,invalidpassword", // invalid password
        "nonexistinguser,invalidpassword", // both invalid
    })
    public void failedAuthenticate(String username, String password) throws Exception {
        String url = "http://localhost:" + port + "/auth";
        HttpEntity<Usuario> request = new HttpEntity<>(new Usuario(username, password));
        ResponseEntity<Usuario> response = restTemplate.exchange(url, HttpMethod.POST, request, Usuario.class);
        assertNull(response.getBody());
        assertEquals(401, response.getStatusCodeValue());
    }

    @Test
    public void getUsuario() throws Exception {
        String url = "http://localhost:" + port + "/usuarios/" + usuario1.getUid().toString();
        ResponseEntity<Usuario> response = restTemplate.exchange(url, HttpMethod.GET, null, Usuario.class);
        assertNotNull(response.getBody());
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("john.doe", response.getBody().getNombreUsuario());
    }

    @Test
    public void getNonExistingUsuario() throws Exception {
        String url = "http://localhost:" + port + "/usuarios/8be63de2-a6d5-4a00-b441-102de43adbfa";
        ResponseEntity<Usuario> response = restTemplate.exchange(url, HttpMethod.GET, null, Usuario.class);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void updateUsuario() throws Exception {
        String url = "http://localhost:" + port + "/usuarios/" + usuario1.getUid().toString();
        HttpEntity<Usuario> request = new HttpEntity<>(new Usuario("bart.simpson", "verystrongpass"));
        ResponseEntity<Usuario> response = restTemplate.exchange(url, HttpMethod.PUT, request, Usuario.class);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getUid());
        assertEquals("bart.simpson", response.getBody().getNombreUsuario());
    }

    @Test
    public void updateNonExisting() throws Exception {
        String url = "http://localhost:" + port + "/usuarios/8be63de2-a6d5-4a00-b441-102de43adbfa";
        HttpEntity<Usuario> request = new HttpEntity<>(new Usuario("bart.simpson", "verystrongpass"));
        ResponseEntity<Usuario> response = restTemplate.exchange(url, HttpMethod.PUT, request, Usuario.class);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void deleteUsuario() throws Exception {
        String url = "http://localhost:" + port + "/usuarios/" + usuario2.getUid().toString();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
        assertEquals(200, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void deleteNonExistingUsuario() throws Exception {
        String url = "http://localhost:" + port + "/usuarios/8be63de2-a6d5-4a00-b441-102de43adbfa";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

}