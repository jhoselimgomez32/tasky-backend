package co.edu.uan.software.tasky.entities;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name = "tasks")
public class TaskEntity {
    // Be sure you are using this annotation ONLY in the variable definition
    // and not in getters/setters
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String descripcion;
    private UUID taskListId;
    @ManyToOne
    private Usuario usuario;

    public TaskEntity() {
    }

    public UUID getTaskListId() {
        return taskListId;
    }

    public void setTaskListId(UUID taskListId) {
        this.taskListId = taskListId;
    }

    public TaskEntity(String descripValue, Usuario userValue) {
        this.setDescripcion(descripValue);
        this.setUsuario(userValue);
    }

    @Override
    public String toString() {
        return "Task: { id: " + id + ", descripción: " + descripcion + " }";
    }

    public UUID getId() {
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

    /**
     * @return Usuario return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
