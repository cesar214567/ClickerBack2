package clicker.back.antiguo;

import javax.persistence.*;

@Entity
public class SedesConcesionarios {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long idSedeConcesionarios;

    @Column(length = 500)
    String direccion;

    @Column(length = 20)
    String telefonoContacto;

    @Column
    String correoContacto;

    @Column
    String nombreContacto;

    @JoinColumn(name = "concesionario")
    @ManyToOne
    Concesionarios concesionario;

    @Column(length = 500)
    String linkQr;

    public Long getIdSedeConcesionarios() {     
        return idSedeConcesionarios;
    }

    public void setIdSedeConcesionarios(Long idSedeConcesionarios) {
        this.idSedeConcesionarios = idSedeConcesionarios;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public String getCorreoContacto() {
        return correoContacto;
    }

    public void setCorreoContacto(String correoContacto) {
        this.correoContacto = correoContacto;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public Concesionarios getConcesionario() {
        return concesionario;
    }

    public void setConcesionario(Concesionarios concesionario) {
        this.concesionario = concesionario;
    }

    public String getLinkQr() {
        return linkQr;
    }

    public void setLinkQr(String linkQr) {
        this.linkQr = linkQr;
    }
}
