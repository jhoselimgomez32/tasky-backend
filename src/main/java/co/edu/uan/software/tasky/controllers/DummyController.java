package co.edu.uan.software.tasky.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uan.software.tasky.entities.DummyEntity;
import co.edu.uan.software.tasky.repositories.DummyRepository;

/**
 * This is the Rest Controller for the DUmmy Entity. It provides basic CRUD
 * functionality.
 * - Create -> POST
 * - Read -> GET
 * - Update -> PUT and PATCH
 * - Delete -> DELETE
 */
@RestController
public class DummyController {

    private final DummyRepository repo;

    DummyController(DummyRepository r) {
        this.repo = r;
    }

    /**
     * Creates a new dummy in the database
     * 
     * @param dummy The new dummy
     * @return Returns the dummy with an assigned PK
     */
    @PostMapping("/dummies")
    public ResponseEntity<DummyEntity> createDummy(@RequestBody DummyEntity dummy) {
        if(dummy.getDummyValue()==null || dummy.getDummyValue().isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(this.repo.save(dummy), HttpStatus.CREATED);
    }

    /**
     * @return Returns all the dummies in database.
     */
    @GetMapping("/dummies")
    public List<DummyEntity> findAllDummies() {
        return this.repo.findAll();
    }

    /**
     * @param id The id of the dummy you are looking for.
     * @return Returns the dummy with the given Id or a Not Found if the dummy
     *         doesn't exist.
     */
    @GetMapping("/dummies/{id}")
    public ResponseEntity<DummyEntity> findDummy(@PathVariable("id") UUID dummyId) {
        return this.repo.findById(dummyId)
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Updates an existing dummy in the database
     * 
     * @param dummy The dummy to be updated
     * @return Returns the dummy with all fields changed, or throws an exception if
     *         the entoty doesn't exist.
     */
    @PutMapping("/dummies/{id}")
    public ResponseEntity<DummyEntity> updateDummy(@PathVariable("id") UUID dummyId, @RequestBody DummyEntity dummy) {
        if (repo.existsById(dummyId)) {
            dummy.setId(dummyId);
            return new ResponseEntity<DummyEntity>(repo.save(dummy), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes an existing dummy in the database
     * 
     * @param dummy The dummy to be deleted
     */
    @DeleteMapping("/dummies/{id}")
    public ResponseEntity<Object> deleteDummy(@PathVariable("id") UUID dummyId) {
        try {
            repo.deleteById(dummyId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
