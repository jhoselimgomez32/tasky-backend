package co.edu.uan.software.tasky.entities;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "TAGS")
public class TagEntity {
    
    private @Id @GeneratedValue(strategy = GenerationType.AUTO) UUID id;
    public String name;
    public String color;

    public TagEntity() {}

    public TagEntity(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Tag: { name: " + name + ", color: " + color + " }";
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setName(String name) {
        this.name = name;
    }
}
