package co.edu.uan.software.tasky.controllers;

import java.util.List;
import java.util.Optional;
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

import co.edu.uan.software.tasky.entities.TaskEntity;
import co.edu.uan.software.tasky.entities.Usuario;
import co.edu.uan.software.tasky.repositories.TaskRepository;
import co.edu.uan.software.tasky.repositories.UsuarioRepository;

/**
 * This is the Rest Controller for the Task Entity. It provides basic CRUD
 * functionality.
 * - Create -> POST
 * - Read -> GET
 * - Update -> PUT and PATCH
 * - Delete -> DELETE
 */
@RestController
public class TaskController {
    private final TaskRepository repo;
    private final UsuarioRepository usuariosRepo;

    TaskController(TaskRepository r, UsuarioRepository ur) {
        this.repo = r;
        this.usuariosRepo = ur;
    }

    /**
     * Creates a new task in the database
     * 
     * @param task The new task
     * @return Returns the task with an assigned PK
     */
    @PostMapping("/task")
    public ResponseEntity<TaskEntity> createTask(@RequestBody TaskEntity task) {
        return new ResponseEntity<>(this.repo.save(task), HttpStatus.CREATED);
    }

    /**
     * Creates a new task for an specific user
     * 
     * @param task The new task
     * @return Returns the task with an assigned PK
     */
    @PostMapping("/usuarios/{usuario_id}/tasks")
    public ResponseEntity<TaskEntity> createUserTask(
            @PathVariable(name = "usuario_id", required = true) UUID usuario_id, @RequestBody TaskEntity task) {
        Optional<Usuario> usuario = usuariosRepo.findById(usuario_id);
        if (!usuario.isPresent()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        task.setUsuario(usuario.get());
        return new ResponseEntity<>(this.repo.save(task), HttpStatus.CREATED);
    }

    /**
     * @return Returns all the tasks for a user.
     */
    @GetMapping("/usuarios/{usuario_id}/tasks")
    public ResponseEntity<List<TaskEntity>> findUserTasks(
            @PathVariable(name = "usuario_id", required = true) UUID usuario_id) {
        Optional<Usuario> usuario = usuariosRepo.findById(usuario_id);
        if (!usuario.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(usuario.get().getTasks(), HttpStatus.OK);
    }

    /**
     * @return Returns all the task in database.
     */
    @GetMapping("/task")
    public List<TaskEntity> findAllTask() {
        return this.repo.findAll();
    }

    /**
     * @param id The id of the taskList you are looking for.
     * @return Returns the taskList with the given Id or a Not Found if the taskList
     *         doesn't exist.
     */
    @GetMapping("/task/{id}")
    public ResponseEntity<TaskEntity> findTask(@PathVariable("id") UUID taskId) {
        return this.repo.findById(taskId)
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Updates an existing taskList in the database
     * 
     * @param taskList The taskList to be updated
     * @return Returns the taskList with all fields changed, or throws an exception
     *         if
     *         the entoty doesn't exist.
     */
    @PutMapping("/task/{id}")
    public ResponseEntity<TaskEntity> updateTask(@PathVariable("id") UUID taskId, @RequestBody TaskEntity task) {
        if (repo.existsById(taskId)) {
            task.setId(taskId);
            /*
             * TaskListController = new TaskListController();
             * 
             * Object idTaskList = taskList.findTaskList(task.getTaskListId());
             * 
             * if(task.getTaskListId() != null){
             * if(idTaskList == null){
             * return new ResponseEntity<TaskEntity>(task, HttpStatus.NOT_FOUND);
             * }
             * }
             * 
             * return new ResponseEntity<TaskEntity>(repo.save(task), HttpStatus.NOT_FOUND);
             */
            return new ResponseEntity<TaskEntity>(repo.save(task), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes an existing taskList in the database
     * 
     * @param taskList The taskList to be deleted
     */
    @DeleteMapping("/task/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable("id") UUID taskId) {
        try {
            repo.deleteById(taskId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
