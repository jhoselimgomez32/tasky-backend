package co.edu.uan.software.tasky.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Dummy Entity, don't use it. It's an exmaple.
 */
@Entity(name = "dummy")
public class DummyEntity {

    private @Id @GeneratedValue Long id;
    private String dummyValue;

    /**
     * @return @Id @GeneratedValue Long return the id
     */
    public @Id @GeneratedValue Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
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
