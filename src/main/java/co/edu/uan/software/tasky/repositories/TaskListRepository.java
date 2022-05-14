package co.edu.uan.software.tasky.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import co.edu.uan.software.tasky.entities.TaskListEntity;

public interface TaskListRepository extends JpaRepository<TaskListEntity, UUID>{
    List<TaskListEntity> findByUserId(String userId);
}
