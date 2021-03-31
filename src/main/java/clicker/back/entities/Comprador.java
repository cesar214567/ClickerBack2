package clicker.back.entities;

import javax.persistence.*;

@Entity
public class Comprador {
    @Column(name = "id_comprador")
    @Id
    String correo;

    @Column
    String nombre;

    @Column
    String telefono;

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
