package co.edu.uan.software.tasky.entities;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "TASK")
public class TaskEntity {
    private @Id @GeneratedValue(strategy = GenerationType.AUTO) UUID id;
    private String descripcion;
    private UUID userId;
    private UUID taskListId;

    public TaskEntity() {}

    public UUID getTaskListId() {
        return taskListId;
    }

    public void setTaskListId(UUID taskListId) {
        this.taskListId = taskListId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public TaskEntity(String descripValue, UUID userIdValue) {
        this.setDescripcion(descripValue);
        this.setUserId(userIdValue);
    }

    @Override
    public String toString() {
        return "Task: { id: " + id + ", descripción: " +descripcion+ " }";
    }

    public @Id @GeneratedValue UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
