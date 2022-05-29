package co.edu.uan.software.tasky.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.uan.software.tasky.entities.Usuario;

/**
 * This is the repository for @see co.edu.uan.software.tasky.entities.DummyEntity
 */
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    public List<Usuario> findByNombreUsuario(String u);
}
