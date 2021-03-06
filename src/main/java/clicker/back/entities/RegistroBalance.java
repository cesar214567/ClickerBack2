package clicker.back.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class RegistroBalance {
    @Column(name = "registro_balance_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    @Column
    Date fecha;

    @Column
    Long ganancia;


    @JoinColumn(name = "correo")
    @ManyToOne
    Usuario usuario;

    @Column
    String descripcion;

    public RegistroBalance(Date fecha, Long ganancia, Usuario usuario, String descripcion) {
        this.fecha = fecha;
        this.ganancia = ganancia;
        this.usuario = usuario;
        this.descripcion = descripcion;
    }

    public RegistroBalance() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Long getGanancia() {
        return ganancia;
    }

    public void setGanancia(Long ganancia) {
        this.ganancia = ganancia;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
