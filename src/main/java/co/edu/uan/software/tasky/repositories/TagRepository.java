package co.edu.uan.software.tasky.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.uan.software.tasky.entities.TagEntity;

public interface TagRepository extends JpaRepository<TagEntity, UUID> {
    
}
