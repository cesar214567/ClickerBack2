package clicker.back.antiguo;


import clicker.back.utils.entities.Locaciones;

import javax.persistence.*;
import javax.xml.transform.Result;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class Autos implements Cloneable {

    //@Column(name = "id_auto")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Id
    String id;

    //@Column
    String modelo;

    //@Column
    Long anoFabricacion;

    String  concesionarios;

    //@Column(length = 1000)
    String fotoPrincipal;

    //@Column(length = 1000)
    String documentacion;

    //@Column
    Float precioVenta;

    //@Column
    String moneda;

    //@ElementCollection
    //List<Locaciones> locaciones;
    Object locaciones;

    //@Column
    String tipoCarroceria;

    //@ElementCollection
    Object usoAuto;

    //@Column
    String marca;

    //@Column
    Boolean presentar;

    //@Column
    String version;

    //@Column
    Integer codVersion;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getFotoPrincipal() {
        return fotoPrincipal;
    }

    public void setFotoPrincipal(String fotoPrincipal) {
        this.fotoPrincipal = fotoPrincipal;
    }

    public String getDocumentacion() {
        return documentacion;
    }

    public void setDocumentacion(String documentacion) {
        this.documentacion = documentacion;
    }


    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getTipoCarroceria() {
        return tipoCarroceria;
    }

    public void setTipoCarroceria(String tipoCarroceria) {
        this.tipoCarroceria = tipoCarroceria;
    }



    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Boolean getPresentar() {
        return presentar;
    }

    public void setPresentar(Boolean presentar) {
        this.presentar = presentar;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getCodVersion() {
        return codVersion;
    }

    public void setCodVersion(Integer codVersion) {
        this.codVersion = codVersion;
    }

    public String getConcesionarios() {
        return concesionarios;
    }

    public void setConcesionarios(String concesionarios) {
        this.concesionarios = concesionarios;
    }

    public Long getAnoFabricacion() {
        return anoFabricacion;
    }

    public void setAnoFabricacion(Long anoFabricacion) {
        this.anoFabricacion = anoFabricacion;
    }

    public Float getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Float precioVenta) {
        this.precioVenta = precioVenta;
    }



    public Object getUsoAuto() {
        return usoAuto;
    }

    public void setUsoAuto(Object usoAuto) {
        this.usoAuto = usoAuto;
    }

    public Object getLocaciones() {
        return locaciones;
    }

    public void setLocaciones(Object locaciones) {
        this.locaciones = locaciones;
    }

    public Autos(ResultSet resultSet) throws SQLException {
        setId(resultSet.getString("id_auto"));
        setModelo(resultSet.getString("modelo"));
        setAnoFabricacion(resultSet.getLong("anofabricacion"));
        setConcesionarios(resultSet.getString("concesionario"));
        setFotoPrincipal(resultSet.getString("foto"));
        setDocumentacion(resultSet.getString("documentacion"));
        setPrecioVenta( resultSet.getFloat("precio"));
        setMoneda(resultSet.getString("moneda"));

        setLocaciones(resultSet.getArray("ciudadesdisp").getArray() );
        setTipoCarroceria(resultSet.getString("tipocarroceria"));
        setUsoAuto( resultSet.getArray("usoauto").getArray());
        setMarca(resultSet.getString("marca"));
        setPresentar(resultSet.getBoolean("presentar"));
        setVersion(resultSet.getString("version"));
        setCodVersion((int) resultSet.getShort("codversion"));
    }
}
