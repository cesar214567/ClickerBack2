package clicker.back.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
public class VentaSemiNuevo {
    @Column(name = "id_venta_semi_nuevo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    @JoinColumn(name = "id_auto_semi_nuevo")
    @OneToOne(cascade = CascadeType.ALL)
    AutoSemiNuevo autoSemiNuevo;

    @JsonIgnoreProperties({"carrosPosteados","denuncias","interesadoReventas","solicitudesRetiros"})
    @JoinColumn(name = "id_vendedor")
    @ManyToOne
    Usuario vendedor;

    @Column
    Date fecha;

    @Column
    String ciudadCompra;

    @Column
    String foto;

    @Column
    Float comisionGeneral;

    @Column
    Float precioFinalVenta;

    @Column
    Float gananciaUsuario;

    @Column
    Float gananciaVendedor;

    @Column
    Float gananciaEmpresa;

    public Float getComisionGeneral() {
        return comisionGeneral;
    }

    public void setComisionGeneral(Float comisionGeneral) {
        this.comisionGeneral = comisionGeneral;
    }

    public Float getPrecioFinalVenta() {
        return precioFinalVenta;
    }

    public void setPrecioFinalVenta(Float precioFinalVenta) {
        this.precioFinalVenta = precioFinalVenta;
    }

    public Float getGananciaUsuario() {
        return gananciaUsuario;
    }

    public void setGananciaUsuario(Float gananciaUsuario) {
        this.gananciaUsuario = gananciaUsuario;
    }

    public Float getGananciaVendedor() {
        return gananciaVendedor;
    }

    public void setGananciaVendedor(Float gananciaVendedor) {
        this.gananciaVendedor = gananciaVendedor;
    }

    public Float getGananciaEmpresa() {
        return gananciaEmpresa;
    }

    public void setGananciaEmpresa(Float gananciaEmpresa) {
        this.gananciaEmpresa = gananciaEmpresa;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

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

    public Usuario getVendedor() {
        return vendedor;
    }

    public void setVendedor(Usuario vendedor) {
        this.vendedor = vendedor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCiudadCompra() {
        return ciudadCompra;
    }

    public void setCiudadCompra(String ciudadCompra) {
        this.ciudadCompra = ciudadCompra;
    }

}
