package clicker.back.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.core.annotation.Order;

import javax.persistence.*;
import java.util.List;

@Entity
public class Usuario {

    @Column
    String fbId;

    @Column
    String nombre;

    @Column(length = 1000)
    String imagenPerfil;

    @Column
    Long numTelefono;

    @Id
    @Column(unique = true,name = "id_usuario")
    String correo;

    @Column
    String password;

    @Column(length = 50)
    String tipoLicencia;

    @Column(length = 100)
    String tiempoLicencia;

    @Column
    Boolean trabajoAplicativo;

    @Column
    String tipoDocumento;

    @Column(length = 25)
    String numDocumento;

    @Column
    Boolean enabled;

    @Column
    Boolean validated;

    @Column
    String rol;

    @Column
    Long balance;

    @Column
    Integer cantidadCarrosAno;

    @JsonIgnoreProperties("usuario")
    @OneToMany(cascade = CascadeType.ALL)
    List<AutoSemiNuevo> carrosPosteados;

    @JsonIgnoreProperties("usuario")
    @OneToMany(cascade = CascadeType.ALL)
    List<Denuncia> denuncias;

    @JsonIgnoreProperties("usuario")
    @OneToMany(cascade = CascadeType.ALL)
    List<InteresadoReventa> interesadoReventas;

    @JsonIgnoreProperties({"usuario","admin"})
    @OneToMany(cascade = CascadeType.ALL)
    List<SolicitudesRetiro> solicitudesRetiros;

    @JsonIgnoreProperties("usuario")
    @OneToOne(cascade = CascadeType.ALL)
    Form form;

    @JsonIgnoreProperties({"usuario","admin"})
    @OneToMany(cascade = CascadeType.ALL)
    List<SolicitudesRetiro> solicitudesRetiro;

    @OrderBy("fecha")
    @JsonIgnoreProperties({"usuario"})
    @OneToMany(cascade = CascadeType.ALL)
    List<RegistroBalance> historialBalance;

    Long numeroDenuncias;

    public List<RegistroBalance> getHistorialBalance() {
        return historialBalance;
    }

    public void setHistorialBalance(List<RegistroBalance> historialBalance) {
        this.historialBalance = historialBalance;
    }

    public Boolean getValidated() {
        return validated;
    }

    public void setValidated(Boolean validated) {
        this.validated = validated;
    }

    public Long getNumeroDenuncias() {
        return numeroDenuncias;
    }

    public void setNumeroDenuncias(Long numeroDenuncias) {
        this.numeroDenuncias = numeroDenuncias;
    }

    public List<InteresadoReventa> getInteresadoReventas() {
        return interesadoReventas;
    }

    public void setInteresadoReventas(List<InteresadoReventa> interesadoReventas) {
        this.interesadoReventas = interesadoReventas;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



    public String getImagenPerfil() {
        return imagenPerfil;
    }

    public void setImagenPerfil(String imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }

    public Long getNumTelefono() {
        return numTelefono;
    }

    public void setNumTelefono(Long numTelefono) {
        this.numTelefono = numTelefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTipoLicencia() {
        return tipoLicencia;
    }

    public void setTipoLicencia(String tipoLicencia) {
        this.tipoLicencia = tipoLicencia;
    }

    public String getTiempoLicencia() {
        return tiempoLicencia;
    }

    public void setTiempoLicencia(String tiempoLicencia) {
        this.tiempoLicencia = tiempoLicencia;
    }

    public Boolean getTrabajoAplicativo() {
        return trabajoAplicativo;
    }

    public void setTrabajoAplicativo(Boolean trabajoAplicativo) {
        this.trabajoAplicativo = trabajoAplicativo;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumDocumento() {
        return numDocumento;
    }

    public void setNumDocumento(String numDocumento) {
        this.numDocumento = numDocumento;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Integer getCantidadCarrosAno() {
        return cantidadCarrosAno;
    }

    public void setCantidadCarrosAno(Integer cantidadCarrosAno) {
        this.cantidadCarrosAno = cantidadCarrosAno;
    }

    public List<AutoSemiNuevo> getCarrosPosteados() {
        return carrosPosteados;
    }

    public void setCarrosPosteados(List<AutoSemiNuevo> carrosPosteados) {
        this.carrosPosteados = carrosPosteados;
    }

    public List<Denuncia> getDenuncias() {
        return denuncias;
    }

    public void setDenuncias(List<Denuncia> denuncias) {
        this.denuncias = denuncias;
    }

    public List<SolicitudesRetiro> getSolicitudesRetiros() {
        return solicitudesRetiros;
    }

    public void setSolicitudesRetiros(List<SolicitudesRetiro> solicitudesRetiros) {
        this.solicitudesRetiros = solicitudesRetiros;
    }

    public void setSolicitudesRetiro(List<SolicitudesRetiro> solicitudesRetiro) {
        this.solicitudesRetiro = solicitudesRetiro;
    }

    public List<SolicitudesRetiro> getSolicitudesRetiro() {
        return solicitudesRetiro;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }
}
