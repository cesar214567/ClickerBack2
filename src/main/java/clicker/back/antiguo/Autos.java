package clicker.back.antiguo;


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
    Long anofabricacion;

    String  concesionarios;

    //@Column(length = 1000)
    String foto;

    //@Column(length = 1000)
    String documentacion;

    //@Column
    Long precio;

    //@Column
    String moneda;

    //@ElementCollection
    Object ciudadesDisponibles;

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

    public Long getAnofabricacion() {
        return anofabricacion;
    }

    public void setAnofabricacion(Long anofabricacion) {
        this.anofabricacion = anofabricacion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDocumentacion() {
        return documentacion;
    }

    public void setDocumentacion(String documentacion) {
        this.documentacion = documentacion;
    }

    public Long getPrecio() {
        return precio;
    }

    public void setPrecio(Long precio) {
        this.precio = precio;
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

    public Object getCiudadesDisponibles() {

        return ciudadesDisponibles;
    }

    public void setCiudadesDisponibles(Object ciudadesDisponibles) {
        this.ciudadesDisponibles = ciudadesDisponibles;
    }

    public Object getUsoAuto() {
        return usoAuto;
    }

    public void setUsoAuto(Object usoAuto) {
        this.usoAuto = usoAuto;
    }

    public Autos(ResultSet resultSet) throws SQLException {
        setId(resultSet.getString("id_auto"));
        setModelo(resultSet.getString("modelo"));
        setAnofabricacion(resultSet.getLong("anofabricacion"));
        setConcesionarios(resultSet.getString("concesionario"));
        setFoto(resultSet.getString("foto"));
        setDocumentacion(resultSet.getString("documentacion"));
        setPrecio((long) resultSet.getInt("precio"));
        setMoneda(resultSet.getString("moneda"));

        setCiudadesDisponibles(resultSet.getArray("ciudadesdisp").getArray() );
        setTipoCarroceria(resultSet.getString("tipocarroceria"));
        setUsoAuto( resultSet.getArray("usoauto").getArray());
        setMarca(resultSet.getString("marca"));
        setPresentar(resultSet.getBoolean("presentar"));
        setVersion(resultSet.getString("version"));
        setCodVersion((int) resultSet.getShort("codversion"));
    }
}
