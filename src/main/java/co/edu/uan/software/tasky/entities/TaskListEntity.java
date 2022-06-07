package co.edu.uan.software.tasky.entities;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "tasklist")
public class TaskListEntity {

    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uid", nullable = false)
    private UUID uid;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "user_id", nullable = false)
    private String user_id;
    @Column(name = "register_date")
    private Timestamp register_date;

    public TaskListEntity() {
    }

    public TaskListEntity(String name, String user_id) {
        this.name = name;
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "TaskList : " + name;
    }

    public UUID getId() {
        return this.uid;
    }

    public void setId(UUID uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    public Timestamp getRegisterDate() {
        return register_date;
    }

    public void setRegisterDate(Timestamp register_date) {
        this.register_date = register_date;
    }

}
