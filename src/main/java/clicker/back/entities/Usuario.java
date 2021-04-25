package clicker.back.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.core.annotation.Order;

import javax.persistence.*;
import java.util.List;

@Entity
public class Usuario {
    @Column(name = "id_usuario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    @Column
    String fbId;

    @Column
    String nombre;

    @Column(length = 1000)
    String imagenPerfil;

    @Column
    Long numTelefono;

    @Column(unique = true)
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
    Float balance;

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

    String secret;

    Long numeroDenuncias;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public void updateInfo(Usuario usuario){
        if(usuario.getFbId()!=null)this.fbId=usuario.getFbId();
        if(usuario.getNombre()!=null)this.nombre=usuario.getNombre();
        if(usuario.getNumTelefono()!=null)this.numTelefono=usuario.getNumTelefono();
        if(usuario.getPassword()!=null)this.password=usuario.getPassword();
        if(usuario.getTipoLicencia()!=null)this.tipoLicencia=usuario.getTipoLicencia();
        if(usuario.getTiempoLicencia()!=null)this.tiempoLicencia=usuario.getTiempoLicencia();
        if(usuario.getTrabajoAplicativo()!=null)this.trabajoAplicativo=usuario.getTrabajoAplicativo();

    }
}
