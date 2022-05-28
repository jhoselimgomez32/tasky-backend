package co.edu.uan.software.tasky.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uid;
    @Column(name = "nombre_usuario", nullable = false)
    private String nombreUsuario;
    @Column(nullable = false)
    private String contrasena;
    private String nombres;
    private String apellidos;
    @Column(name = "correo_electronico")
    private String correoElectronico;
    @Column(name = "fecha_creacion")
    private Timestamp fechaCreacion;
    @OneToMany(mappedBy = "usuario") // this means usuario is a variable in the Task class
    @JsonIgnore // this is important to avoid an infinite recursion in the relation
    private List<TaskEntity> tasks;

    public Usuario() {

    }

    public Usuario(String usuario, String contrasena) {
        this.nombreUsuario = usuario;
        this.contrasena = contrasena;
        this.tasks = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Usuario: {" + nombreUsuario + "}";
    }

    /**
     * @return return the uid
     */
    public UUID getUid() {
        return uid;
    }

    /**
     * @param uid to set
     */
    public void setUid(UUID puid) {
        this.uid = puid;
    }

    /**
     * @return String return the nombreUsuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * @param nombreUsuario the nombreUsuario to set
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * @return String return the contrasena
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * @param contrasena the contrasena to set
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * @return String return the nombres
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * @param nombres the nombres to set
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * @return String return the apellidos
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * @param apellidos the apellidos to set
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * @return String return the correoElectronico
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * @param correoElectronico the correoElectronico to set
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /**
     * @return Timestamp return the fechaCreacion
     */
    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @return List<TaskEntity> return the tasks
     */
    public List<TaskEntity> getTasks() {
        return tasks;
    }

    /**
     * @param tasks the tasks to set
     */
    public void setTasks(List<TaskEntity> tasks) {
        this.tasks = tasks;
    }

    /**
     * @param task the tasks to add
     */
    public void addTask(TaskEntity task) {
        this.tasks.add(task);
    }

}
