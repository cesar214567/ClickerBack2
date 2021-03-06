package clicker.back.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
public class SolicitudesRetiro {
    @Column(name = "id_solicitudes_retiro")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    @JsonIgnoreProperties({"carrosPosteados","denuncias","interesadoReventas","solicitudesRetiros","formRemax"})
    @JoinColumn(name = "id_usuario")
    @ManyToOne
    Usuario usuario;

    @JoinColumn(name = "id_user")
    @ManyToOne
    Users admin;

    @Column
    Long monto;

    @Column
    Long transferencia;

    @Column
    Boolean aceptado;

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

    public Users getAdmin() {
        return admin;
    }

    public void setAdmin(Users admin) {
        this.admin = admin;
    }

    public Long getMonto() {
        return monto;
    }

    public void setMonto(Long monto) {
        this.monto = monto;
    }

    public Long getTransferencia() {
        return transferencia;
    }

    public void setTransferencia(Long transferencia) {
        this.transferencia = transferencia;
    }

    public Boolean getAceptado() {
        return aceptado;
    }

    public void setAceptado(Boolean aceptado) {
        this.aceptado = aceptado;
    }
}
