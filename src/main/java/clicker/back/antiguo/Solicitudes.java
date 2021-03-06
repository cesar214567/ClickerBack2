package clicker.back.antiguo;


import clicker.back.entities.Usuario;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
public class Solicitudes {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long solicitudesId;

    @Column
    String tipouso;

    @Column
    String tipoauto;

    @Column
    Boolean credito;

    @Column
    String entidadcrediticia;

    //@JoinColumn(name = "id_auto")
    //@ManyToOne
    //Auto auto;

    @JoinColumn(name = "id_user")
    @ManyToOne
    Usuario usuario;


    @Column
    Date fecha;

    @Column
    String canalinput;

    @Column
    String ciudadcompra;

    @Column
    Time hora;

    @Column
    String tiempocompra;

    public Long getSolicitudesId() {
        return solicitudesId;
    }

    public void setSolicitudesId(Long solicitudesId) {
        this.solicitudesId = solicitudesId;
    }

    public String getTipouso() {
        return tipouso;
    }

    public void setTipouso(String tipouso) {
        this.tipouso = tipouso;
    }

    public String getTipoauto() {
        return tipoauto;
    }

    public void setTipoauto(String tipoauto) {
        this.tipoauto = tipoauto;
    }

    public Boolean getCredito() {
        return credito;
    }

    public void setCredito(Boolean credito) {
        this.credito = credito;
    }

    public String getEntidadcrediticia() {
        return entidadcrediticia;
    }

    public void setEntidadcrediticia(String entidadcrediticia) {
        this.entidadcrediticia = entidadcrediticia;
    }

    /*public Auto getAuto() {
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }*/

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCanalinput() {
        return canalinput;
    }

    public void setCanalinput(String canalinput) {
        this.canalinput = canalinput;
    }

    public String getCiudadcompra() {
        return ciudadcompra;
    }

    public void setCiudadcompra(String ciudadcompra) {
        this.ciudadcompra = ciudadcompra;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public String getTiempocompra() {
        return tiempocompra;
    }

    public void setTiempocompra(String tiempocompra) {
        this.tiempocompra = tiempocompra;
    }
}

