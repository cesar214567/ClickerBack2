package clicker.back.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
public class InteresadoCompra {
    @Column(name = "id_interesados_compra")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    @JsonIgnoreProperties({"denuncias","usuario","solicitudRemocionAuto"})
    @JoinColumn(name = "id_auto_semi_nuevo")
    @ManyToOne
    AutoSemiNuevo autoSemiNuevo;

    @Column
    String nombres;

    @Column(length = 25)
    String numTelefono;

    @Column
    String correo;

    @Column
    String descripcion;

    @Column
    String dni;

    @Column
    String apellidos;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AutoSemiNuevo getAutoSemiNuevo() {
        return autoSemiNuevo;
    }

    public void setAutoSemiNuevo(AutoSemiNuevo autoSemiNuevo) {
        this.autoSemiNuevo = autoSemiNuevo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getNumTelefono() {
        return numTelefono;
    }

    public void setNumTelefono(String numTelefono) {
        this.numTelefono = numTelefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }


}

