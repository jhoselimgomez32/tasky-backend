package co.edu.uan.software.tasky.controllers;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uan.software.tasky.entities.Usuario;
import co.edu.uan.software.tasky.repositories.UsuarioRepository;

@RestController
public class UsuarioController {

    private final UsuarioRepository repo;

    UsuarioController(UsuarioRepository r) {
        this.repo = r;
    }

    /**
     * Creates a new user in the database
     * 
     * @param dummy The new user
     * @return Returns the user with an assigned PK
     */
    @PostMapping("/usuarios")
    public ResponseEntity<Usuario> createUser(@RequestBody Usuario usuario) throws Exception {
        if(usuario.getNombreUsuario()==null || usuario.getContrasena()==null ||
           usuario.getNombreUsuario().isEmpty() || usuario.getContrasena().isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        String md5Hex = DigestUtils
                .md5DigestAsHex(usuario.getContrasena().getBytes()).toUpperCase();
        usuario.setContrasena(md5Hex);
        Date date = new Date();  
        usuario.setFechaCreacion(new Timestamp(date.getTime()));

        return new ResponseEntity<>(this.repo.save(usuario), HttpStatus.CREATED);
    }

    /**
     * @return Returns all the users in database.
     */
    @GetMapping("/usuarios")
    public List<Usuario> findAllusuarios() {
        return this.repo.findAll();
    }

    /**
     * @param id The id of the usuario you are looking for.
     * @return Returns the usuario with the given Id or a Not Found if the usuario
     *         doesn't exist.
     */
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> findUsuario(@PathVariable("id") UUID uid) {
        return this.repo.findById(uid)
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/auth")
    public ResponseEntity<Usuario> authenticate(@RequestBody Usuario usuario) {
        if(usuario.getNombreUsuario()==null || usuario.getContrasena()==null ||
           usuario.getNombreUsuario().isEmpty() || usuario.getContrasena().isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        List<Usuario> usuarios = repo.findByNombreUsuario(usuario.getNombreUsuario());
        // Check if a user exists
        if(usuarios.isEmpty()) 
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        Usuario result = usuarios.get(0);
        // Check if a password is different
        if (!result.getContrasena().equals(usuario.getContrasena()))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        result.setContrasena(null);
        return new ResponseEntity<Usuario>(result, HttpStatus.OK);
    }

    /**
     * Updates an existing usuario in the database
     * 
     * @param usuario The usuario to be updated
     * @return Returns the usuario with all fields changed, or throws an exception
     *         if
     *         the entity doesn't exist.
     */
    @PutMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable("id") UUID uid, @RequestBody Usuario usuario) {
        if (repo.existsById(uid)) {
            usuario.setUid(uid);
            return new ResponseEntity<Usuario>(repo.save(usuario), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes an existing usuario in the database
     * 
     * @param dummy The usuario to be deleted
     */
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Object> deleteUsuario(@PathVariable("id") UUID uid) {
        try {
            repo.deleteById(uid);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
