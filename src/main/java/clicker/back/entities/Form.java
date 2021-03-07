package clicker.back.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
public class Form {
    @Column(name = "id_form_remax")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    @JsonIgnoreProperties({"carrosPosteados","denuncias","interesadoReventas","solicitudesRetiros","form","password"})
    @JoinColumn(name = "id_usuario")
    @OneToOne
    Usuario usuario;

    @Column
    Boolean estado;

    //here goes the form actually


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
