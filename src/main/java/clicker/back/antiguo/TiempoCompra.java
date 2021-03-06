package clicker.back.antiguo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TiempoCompra {
    @Id
    Long idTiempocompra;

    @Column
    String tiempo;

    public Long getIdTiempocompra() {
        return idTiempocompra;
    }

    public void setIdTiempocompra(Long idTiempocompra) {
        this.idTiempocompra = idTiempocompra;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }
}
