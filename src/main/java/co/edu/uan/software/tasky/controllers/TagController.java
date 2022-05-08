package co.edu.uan.software.tasky.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import co.edu.uan.software.tasky.entities.TagEntity;
import co.edu.uan.software.tasky.repositories.TagRepository;

@RestController
public class TagController {

    private final TagRepository repo;

    TagController(TagRepository r) {
        this.repo = r;
    }

    /**
     * Creates a new tag in the database
     * 
     * @param tag The new tag
     * @return Returns the tag with an assigned PK
     */
    @PostMapping("/tag")
    public ResponseEntity<TagEntity> createTag(@RequestBody TagEntity tag) {
        return new ResponseEntity<>(this.repo.save(tag), HttpStatus.CREATED);
    }

    /**
     * @return Returns all the dummies in database.
     */
    @GetMapping("/tags")
    public List<TagEntity> findAllTags() {
        return this.repo.findAll();
    }
}
