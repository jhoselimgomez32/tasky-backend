package co.edu.uan.software.tasky.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.uan.software.tasky.entities.DummyEntity;

/**
 * This is the repository for @see co.edu.uan.software.tasky.entities.DummyEntity
 */
public interface DummyRepository extends JpaRepository<DummyEntity, UUID> {
    
}
