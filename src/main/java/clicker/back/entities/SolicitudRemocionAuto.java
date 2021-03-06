package clicker.back.entities;

import javax.persistence.*;

@Entity
public class SolicitudRemocionAuto {
    @Column(name = "id_solicitud_remocion_auto")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    @JoinColumn(name="id_auto_semi_nuevo")
    @OneToOne
    AutoSemiNuevo autoSemiNuevo;

    @Column(length = 1000)
    String descripcion;

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
