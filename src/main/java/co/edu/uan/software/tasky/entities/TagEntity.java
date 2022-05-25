package co.edu.uan.software.tasky.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Tag Entity, for task storage.
 */
@Entity(name = "tags")
public class TagEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uid;
    @Column(name = "name", nullable = false)
    public String name;
    @Column(name = "color")
    public String color;

    public TagEntity() {
    }

    public TagEntity(String name, String color) {
        this.name = name;
        this.color = color;
    }

    /**
     * @return return the uid
     */
    public UUID getUid() {
        return this.uid;
    }

    /**
     * @param uid to set
     */
    public void setUid(UUID uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Tag: { name: " + name + ", color: " + color + " }";
    }

    /**
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return String return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @param name the color to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
