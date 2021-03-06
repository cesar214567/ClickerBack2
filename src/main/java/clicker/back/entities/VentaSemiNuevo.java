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
    String linkQr;

    @Column
    Boolean pagado;

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

    public String getLinkQr() {
        return linkQr;
    }

    public void setLinkQr(String linkQr) {
        this.linkQr = linkQr;
    }

    public Boolean getPagado() {
        return pagado;
    }

    public void setPagado(Boolean pagado) {
        this.pagado = pagado;
    }
}
