package co.edu.uan.software.tasky.entities;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "TASKLIST")
public class TaskListEntity {
    private @Id @GeneratedValue(strategy = GenerationType.AUTO) UUID id;
    private String name;
    private String userId;
    private Date registerDate;

    public TaskListEntity() {}

    public TaskListEntity(String nameValue) {
        this.name = nameValue;
    }

    @Override
    public String toString() {
        return "TaskList " + id + ":" + name;
    }

    public @Id @GeneratedValue UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }
        
}
