package co.edu.uan.software.tasky.entities;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

/**
 * Dummy Entity, don't use it. It's an exmaple.
 */
@Entity(name = "dummy")
public class DummyEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;
    private String dummyValue;

    public DummyEntity() {}

    public DummyEntity(String v) {
        this.dummyValue = v;
    }

    @Override
    public String toString() {
        return "Dummy " + id + ":" + dummyValue;
    }

    /**
     * @return @Id @GeneratedValue Long return the id
     */
    public @Id @GeneratedValue UUID getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * @return String return the dummyValue
     */
    public String getDummyValue() {
        return dummyValue;
    }

    /**
     * @param dummyValue the dummyValue to set
     */
    public void setDummyValue(String dummyValue) {
        this.dummyValue = dummyValue;
    }

}
