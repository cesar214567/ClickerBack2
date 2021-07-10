package clicker.back.entities;

import clicker.back.utils.entities.Accesorio;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class AutoSemiNuevo implements Cloneable {
    @Column(name = "id_auto_semi_nuevo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Id
    Long id;

    @JsonIgnoreProperties({"carrosPosteados","denuncias","interesadoReventas","solicitudesRetiros","formRemax","solicitudesRetiro","password","historialBalance","form"})
    @JoinColumn(name = "id_usuario")
    @ManyToOne
    Usuario usuario;

    @Column(length = 35)
    String placa;

    @Column(length = 35)
    String serie;

    @Column
    String vin;

    @Column
    String correoDueno;

    @Column
    String nombreDueno;

    @Column(length = 20)
    String telefonoDueno;

    @Column(length = 100)
    String marca;

    @Column(length = 100)
    String modelo;

    @Column
    Integer anoFabricacion;

    @Column(length = 100)
    String tipoCambios;

    @Column(length = 100)
    String tipoCombustible;

    @Column(length = 100)
    String tipoCarroceria;

    @Column
    Float cilindrada;

    @Column
    Long kilometraje;

    @Column
    Integer numeroPuertas;

    @Column(length = 50)
    String tipoTraccion;

    @Column(length = 50)
    String color;

    @Column(length = 100)
    Integer numeroCilindros;

    @Column
    Float precioVenta;

    @Column
    String locacion;

    @Column
    Boolean comprado;

    @Column
    Boolean validado;

    @Column
    Boolean enabled;

    @Column
    Boolean revisado;

    @Column
    Date fechaPublicacion;

    @Column(length = 1000)
    String video;

    @ManyToMany
    List<Accesorio> accesorios;

    @Column(length = 1000)
    String fotoPrincipal;

    @Column(length = 1000)
    String descripcion;

    @Column
    String version;

    @Column
    String mantenimiento;

    @Column
    Boolean unicoDueno;

    @Column
    Integer choques;

    @Column
    Boolean fallaMecanica;

    @Column
    Boolean llaves;

    @Column
    Boolean fumado;

    @ElementCollection
    List<String> fotos;

    @OneToOne(cascade = CascadeType.ALL)
    SolicitudRemocionAuto solicitudRemocionAuto;

    @JsonIgnoreProperties({"autoSemiNuevo"})
    @OneToMany(cascade = CascadeType.ALL)
    List<Denuncia> denuncias;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMantenimiento() {
        return mantenimiento;
    }

    public void setMantenimiento(String mantenimiento) {
        this.mantenimiento = mantenimiento;
    }

    public Boolean getUnicoDueno() {
        return unicoDueno;
    }

    public void setUnicoDueno(Boolean unicoDueno) {
        this.unicoDueno = unicoDueno;
    }

    public Boolean getRevisado() {
        return revisado;
    }

    public void setRevisado(Boolean revisado) {
        this.revisado = revisado;
    }

    @JsonIgnore
    public String getNombredeauto(){
        return marca+" "+modelo+" de tipo "+tipoCarroceria;
    }

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

    public Float getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Float precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Long getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(Long kilometraje) {
        this.kilometraje = kilometraje;
    }


    public Boolean getComprado() {
        return comprado;
    }

    public void setComprado(Boolean comprado) {
        this.comprado = comprado;
    }

    public Boolean getValidado() {
        return validado;
    }

    public void setValidado(Boolean validado) {
        this.validado = validado;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public SolicitudRemocionAuto getSolicitudRemocionAuto() {
        return solicitudRemocionAuto;
    }

    public void setSolicitudRemocionAuto(SolicitudRemocionAuto solicitudRemocionAuto) {
        this.solicitudRemocionAuto = solicitudRemocionAuto;
    }


    public List<Denuncia> getDenuncias() {
        return denuncias;
    }

    public void setDenuncias(List<Denuncia> denuncias) {
        this.denuncias = denuncias;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getCorreoDueno() {
        return correoDueno;
    }

    public void setCorreoDueno(String correoDueno) {
        this.correoDueno = correoDueno;
    }

    public String getNombreDueno() {
        return nombreDueno;
    }

    public void setNombreDueno(String nombreDueno) {
        this.nombreDueno = nombreDueno;
    }

    public String getTelefonoDueno() {
        return telefonoDueno;
    }

    public void setTelefonoDueno(String telefonoDueno) {
        this.telefonoDueno = telefonoDueno;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getAnoFabricacion() {
        return anoFabricacion;
    }

    public void setAnoFabricacion(Integer anoFabricacion) {
        this.anoFabricacion = anoFabricacion;
    }

    public String getTipoCambios() {
        return tipoCambios;
    }

    public void setTipoCambios(String tipoCambios) {
        this.tipoCambios = tipoCambios;
    }

    public String getTipoCombustible() {
        return tipoCombustible;
    }

    public void setTipoCombustible(String tipoCombustible) {
        this.tipoCombustible = tipoCombustible;
    }

    public String getTipoCarroceria() {
        return tipoCarroceria;
    }

    public void setTipoCarroceria(String tipoCarroceria) {
        this.tipoCarroceria = tipoCarroceria;
    }

    public Float getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(Float cilindrada) {
        this.cilindrada = cilindrada;
    }

    public Integer getNumeroPuertas() {
        return numeroPuertas;
    }

    public void setNumeroPuertas(Integer numeroPuertas) {
        this.numeroPuertas = numeroPuertas;
    }

    public String getTipoTraccion() {
        return tipoTraccion;
    }

    public void setTipoTraccion(String tipoTraccion) {
        this.tipoTraccion = tipoTraccion;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getNumeroCilindros() {
        return numeroCilindros;
    }

    public void setNumeroCilindros(Integer numeroCilindros) {
        this.numeroCilindros = numeroCilindros;
    }

    public List<Accesorio> getAccesorios() {
        return accesorios;
    }

    public void setAccesorios(List<Accesorio> accesorios) {
        this.accesorios = accesorios;
    }

    public String getFotoPrincipal() {
        return fotoPrincipal;
    }

    public void setFotoPrincipal(String fotoPrincipal) {
        this.fotoPrincipal = fotoPrincipal;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }



    public Integer getChoques() {
        return choques;
    }

    public void setChoques(Integer choques) {
        this.choques = choques;
    }

    public Boolean getFallaMecanica() {
        return fallaMecanica;
    }

    public void setFallaMecanica(Boolean fallaMecanica) {
        this.fallaMecanica = fallaMecanica;
    }

    public Boolean getLlaves() {
        return llaves;
    }

    public void setLlaves(Boolean llaves) {
        this.llaves = llaves;
    }

    public Boolean getFumado() {
        return fumado;
    }

    public void setFumado(Boolean fumado) {
        this.fumado = fumado;
    }

    public String getLocacion() {
        return locacion;
    }

    public void setLocacion(String locacion) {
        this.locacion = locacion;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }



    public void info(AutoSemiNuevo autoSemiNuevo){


        if(this.correoDueno!=null)autoSemiNuevo.setCorreoDueno(this.correoDueno);
        if(this.nombreDueno!=null)autoSemiNuevo.setNombreDueno(this.nombreDueno);
        if(this.telefonoDueno!=null)autoSemiNuevo.setTelefonoDueno(this.telefonoDueno);
        if(this.anoFabricacion!=null)autoSemiNuevo.setAnoFabricacion(this.anoFabricacion);
        if(this.tipoCambios!=null)autoSemiNuevo.setTipoCambios(this.tipoCambios);
        if(this.tipoCombustible!=null)autoSemiNuevo.setTipoCombustible(this.tipoCombustible);
        if(this.tipoCarroceria!=null)autoSemiNuevo.setTipoCarroceria(this.tipoCarroceria);
        if(this.cilindrada!=null)autoSemiNuevo.setCilindrada(this.cilindrada);
        if(this.kilometraje!=null)autoSemiNuevo.setKilometraje(this.kilometraje);
        if(this.numeroPuertas!=null)autoSemiNuevo.setNumeroPuertas(this.numeroPuertas);
        if(this.tipoTraccion!=null)autoSemiNuevo.setTipoTraccion(this.tipoTraccion);
        if(this.color!=null)autoSemiNuevo.setColor(this.color);
        if(this.numeroCilindros!=null)autoSemiNuevo.setNumeroCilindros(this.numeroCilindros);
        if(this.precioVenta!=null)autoSemiNuevo.setPrecioVenta(this.precioVenta);
        if(this.fechaPublicacion!=null)autoSemiNuevo.setFechaPublicacion(this.fechaPublicacion);
        if(this.video!=null)autoSemiNuevo.setVideo(this.video);
        if(this.accesorios!=null)autoSemiNuevo.setAccesorios(this.accesorios);
        if(this.descripcion!=null)autoSemiNuevo.setDescripcion(this.descripcion);
        if(this.version!=null)autoSemiNuevo.setVersion(this.version);
        if(this.mantenimiento!=null)autoSemiNuevo.setMantenimiento(this.mantenimiento);
        if(this.unicoDueno!=null)autoSemiNuevo.setUnicoDueno(this.unicoDueno);
        if(this.choques!=null)autoSemiNuevo.setChoques(this.choques);
        if(this.fallaMecanica!=null)autoSemiNuevo.setFallaMecanica(this.fallaMecanica);
        if(this.llaves!=null)autoSemiNuevo.setLlaves(this.llaves);
        if(this.fumado!=null)autoSemiNuevo.setFumado(this.fumado);
        if(this.vin!=null)autoSemiNuevo.setVin(this.vin);


    }
    public void reverseInfo(AutoSemiNuevo autoSemiNuevo){
        this.setComprado(autoSemiNuevo.getComprado());
        this.setValidado(autoSemiNuevo.getValidado());
        this.setRevisado(autoSemiNuevo.getRevisado());
        this.setUsuario(autoSemiNuevo.getUsuario());
        this.setSolicitudRemocionAuto(autoSemiNuevo.getSolicitudRemocionAuto());
        this.setDenuncias(autoSemiNuevo.getDenuncias());
        this.setFotoPrincipal(autoSemiNuevo.getFotoPrincipal());
        this.setFotos(autoSemiNuevo.getFotos());
        this.setId(autoSemiNuevo.getId());
        this.setPlaca(autoSemiNuevo.getPlaca());
        this.setMarca(autoSemiNuevo.getMarca());
        this.setModelo(autoSemiNuevo.getModelo());
        this.setSerie(autoSemiNuevo.getSerie());

    }


}
