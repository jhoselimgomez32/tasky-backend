package co.edu.uan.software.tasky.controllers;

import java.util.Date;
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

import co.edu.uan.software.tasky.entities.TaskListEntity;
import co.edu.uan.software.tasky.repositories.TaskListRepository;

/**
 * This is the Rest Controller for the TaskList Entity. It provides basic CRUD
 * functionality.
 * - Create -> POST
 * - Read -> GET
 * - Update -> PUT and PATCH
 * - Delete -> DELETE
 */
@RestController
public class TaskListController {
    private final TaskListRepository repo;

    TaskListController(TaskListRepository r) {
        this.repo = r;
    }

    /**
     * Creates a new taskList in the database
     * 
     * @param taskList The new taskList
     * @return Returns the taskList with an assigned PK
     */
    @PostMapping("/tasklist")
    public ResponseEntity<TaskListEntity> createTaskList(@RequestBody TaskListEntity taskList) {
        Date date = new Date(System.currentTimeMillis());
        String name = taskList.getName();
        String userId = taskList.getUserId();
        System.out.print("date "+date);
        taskList.setRegisterDate(date);

         if (name.length() > 64){
            return new ResponseEntity<>(taskList, HttpStatus.NOT_FOUND);
         }else if(name.isEmpty() && userId.isEmpty()){
            return new ResponseEntity<>(taskList, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(this.repo.save(taskList), HttpStatus.CREATED);
    }

    /**
     * @return Returns all the tasklist in database.
     */
    @GetMapping("/tasklist")
    public List<TaskListEntity> findAllTaskList() {
        return this.repo.findAll();
    }

    /**
     * @param id The id of the taskList you are looking for.
     * @return Returns the taskList with the given Id or a Not Found if the taskList
     *         doesn't exist.
     */
    @GetMapping("/tasklist/{id}")
    public ResponseEntity<TaskListEntity> findTaskList(@PathVariable("id") UUID taskListId) {
        return this.repo.findById(taskListId)
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Updates an existing taskList in the database
     * 
     * @param taskList The taskList to be updated
     * @return Returns the taskList with all fields changed, or throws an exception if
     *         the entoty doesn't exist.
     */
    @PutMapping("/tasklist/{id}")
    public ResponseEntity<TaskListEntity> updateTaskList(@PathVariable("id") UUID taskListId, @RequestBody TaskListEntity taskList) {
        if (repo.existsById(taskListId)) {
            taskList.setId(taskListId);
            return new ResponseEntity<TaskListEntity>(repo.save(taskList), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes an existing taskList in the database
     * 
     * @param taskList The taskList to be deleted
     */
    @DeleteMapping("/tasklist/{id}")
    public ResponseEntity<Object> deleteTaskList(@PathVariable("id") UUID taskListId) {
        try {
            repo.deleteById(taskListId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}
