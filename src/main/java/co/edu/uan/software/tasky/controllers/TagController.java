package co.edu.uan.software.tasky.controllers;

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

import java.util.List;
import java.util.Random;
import java.util.UUID;

import co.edu.uan.software.tasky.entities.TagEntity;
import co.edu.uan.software.tasky.repositories.TagRepository;

@RestController
public class TagController {

    private final TagRepository repository;

    TagController(TagRepository r) {
        this.repository = r;
    }

    /**
     * Creates a new tag in the database
     * 
     * @param tag The new tag
     * @return Returns the tag with an assigned PK
     */
    @PostMapping("/tags")
    public ResponseEntity<TagEntity> createTag(@RequestBody TagEntity tag) {

        System.out.println(tag.color);
        if (tag.color == null || tag.color == "") {
            // create object of Random class
            Random obj = new Random();
            int rand_num = obj.nextInt(0xffffff + 1);

            // format it as hexadecimal string and print
            String colorCode = String.format("#%06x", rand_num);
            System.out.println(colorCode);
            tag.color = colorCode;
        }
        
        if (tag.name == null || tag.name == "") {
            return new ResponseEntity<>(tag, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(repository.save(tag), HttpStatus.CREATED);
        }
    }

    /**
     * @return Returns all the tags in database.
     */
    @GetMapping("/tags")
    public List<TagEntity> findAllTags() {
        return repository.findAll();
    }

    /**
     * @param id The id of the tag you are looking for.
     * @return Returns the tag with the given Id or a Not Found if the tag
     *         doesn't exist.
     */
    @GetMapping("/tags/{id}")
    public ResponseEntity<TagEntity> findTag(@PathVariable("id") UUID tagId) {
        return this.repository.findById(tagId)
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Updates an existing tag in the database
     * 
     * @param tag The tag to be updated
     * @return Returns the tag with all fields changed, or throws an exception if
     *         the entoty doesn't exist.
     */
    @PutMapping("/tags/{id}")
    public ResponseEntity<TagEntity> updateTag(@PathVariable("id") UUID tagId, @RequestBody TagEntity tag) {
        if (repository.existsById(tagId)) {
            tag.setId(tagId);
            return new ResponseEntity<TagEntity>(repository.save(tag), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes an existing tag in the database
     * 
     * @param tag The tag to be deleted
     */
    @DeleteMapping("/tags/{id}")
    public ResponseEntity<Object> deleteTag(@PathVariable("id") UUID tagId) {
        try {
            repository.deleteById(tagId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
