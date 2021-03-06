package clicker.back.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
@Entity
public class InteresadoReventa {

    @Column(name = "id_interesados_reventa")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    @JsonIgnoreProperties({"denuncias","usuario","solicitudRemocionAuto"})
    @JoinColumn(name = "id_auto_semi_nuevo")
    @ManyToOne
    AutoSemiNuevo autoSemiNuevo;

    @JsonIgnoreProperties({"carrosPosteados","denuncias","interesadoReventas","solicitudesRetiros","formRemax"})
    @JoinColumn(name = "id_usuario")
    @ManyToOne
    Usuario usuario;

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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
