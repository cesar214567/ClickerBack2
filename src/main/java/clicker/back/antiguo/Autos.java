package clicker.back.antiguo;


import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Autos implements Cloneable {

    @Column(name = "id_auto")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    @Column
    String modelo;

    @Column
    Date anofabricacion;

    //TODO
    //Concesionarios concesionarios;

    @Column(length = 1000)
    String foto;

    @Column(length = 1000)
    String documentacion;

    @Column
    Long precio;

    @Column
    String moneda;

    @ElementCollection
    List<String> ciudadesDisponibles;

    @Column
    String tipoCarroceria;

    @ElementCollection
    List<String> usoAuto;

    @Column
    String marca;

    @Column
    Boolean presentar;

    @Column
    String version;

    @Column
    Integer codVersion;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Date getAnofabricacion() {
        return anofabricacion;
    }

    public void setAnofabricacion(Date anofabricacion) {
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

    public List<String> getCiudadesDisponibles() {
        return ciudadesDisponibles;
    }

    public void setCiudadesDisponibles(List<String> ciudadesDisponibles) {
        this.ciudadesDisponibles = ciudadesDisponibles;
    }

    public String getTipoCarroceria() {
        return tipoCarroceria;
    }

    public void setTipoCarroceria(String tipoCarroceria) {
        this.tipoCarroceria = tipoCarroceria;
    }

    public List<String> getUsoAuto() {
        return usoAuto;
    }

    public void setUsoAuto(List<String> usoAuto) {
        this.usoAuto = usoAuto;
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
}
