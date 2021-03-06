package clicker.back.utils.entities;

import javax.persistence.*;

@Entity
public class Locaciones {
    @Column(name = "locaciones_id")
    @Id
    String id;

    @Column
    String departamento;

    @Column
    String provincia;

    @Column
    String distrito;

    @Column
    Boolean enabled;

    public Locaciones(String id, String departamento, String provincia, String distrito, Boolean enabled) {
        this.id = id;
        this.departamento = departamento;
        this.provincia = provincia;
        this.distrito = distrito;
        this.enabled = enabled;
    }

    public Locaciones() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
