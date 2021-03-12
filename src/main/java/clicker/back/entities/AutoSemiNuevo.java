package clicker.back.entities;

import clicker.back.utils.entities.Locaciones;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
    Integer cilindrada;

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
    Long precioVenta;

    @ManyToOne
    Locaciones locaciones;

    @Column
    Boolean comprado;

    @Column
    Boolean validado;

    @Column
    Boolean enabled;

    @Column
    Integer comisionUsuario;

    @Column
    Integer comisionVendedor;

    @Column
    Integer comisionEmpresa;

    @Column
    Date fechaPublicacion;

    @Column(length = 1000)
    String video;

    @ElementCollection
    List<String> accesorios;

    @Column(length = 1000)
    String fotoPrincipal;

    @Column
    String descripcion;

    @OneToMany(cascade = CascadeType.ALL)
    List<FotosAutoSemiNuevo> fotos;

    @OneToOne(cascade = CascadeType.ALL)
    SolicitudRemocionAuto solicitudRemocionAuto;

    @JsonIgnoreProperties({"autoSemiNuevo"})
    @OneToMany(cascade = CascadeType.ALL)
    List<Denuncia> denuncias;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

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

    public Long getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Long precioVenta) {
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

    public Integer getComisionUsuario() {
        return comisionUsuario;
    }

    public void setComisionUsuario(Integer comisionUsuario) {
        this.comisionUsuario = comisionUsuario;
    }

    public Integer getComisionVendedor() {
        return comisionVendedor;
    }

    public void setComisionVendedor(Integer comisionVendedor) {
        this.comisionVendedor = comisionVendedor;
    }

    public Integer getComisionEmpresa() {
        return comisionEmpresa;
    }

    public void setComisionEmpresa(Integer comisionEmpresa) {
        this.comisionEmpresa = comisionEmpresa;
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

    public Integer getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(Integer cilindrada) {
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

    public List<String> getAccesorios() {
        return accesorios;
    }

    public void setAccesorios(List<String> accesorios) {
        this.accesorios = accesorios;
    }

    public String getFotoPrincipal() {
        return fotoPrincipal;
    }

    public void setFotoPrincipal(String fotoPrincipal) {
        this.fotoPrincipal = fotoPrincipal;
    }

    public List<FotosAutoSemiNuevo> getFotos() {
        return fotos;
    }

    public void setFotos(List<FotosAutoSemiNuevo> fotos) {
        this.fotos = fotos;
    }

    public Locaciones getLocaciones() {
        return locaciones;
    }

    public void setLocaciones(Locaciones locaciones) {
        this.locaciones = locaciones;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
