package co.edu.uan.software.tasky.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import co.edu.uan.software.tasky.repositories.UsuarioRepository;

@SpringBootTest()
public class UsuarioTests {

    @Autowired
    private UsuarioRepository repo;

    @Test
    public void unsavedToString() {
        Usuario usuario = new Usuario("john.doe","strongpassword");
        String expected = "Usuario: {john.doe}";
        assertEquals(expected, usuario.toString());
    }

    @Test
    public void savedToString() {
        Usuario usuario = new Usuario("john.doe","strongpassword");
        repo.save(usuario);
        String expected = "Usuario: {john.doe}";
        assertEquals(expected, usuario.toString());
    }
}
