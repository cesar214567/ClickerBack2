package clicker.back.antiguo;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
public class Registrodecambio {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    @Column
    String operacion;

    @Column
    Long usuarioSolicitando;

    @Column
    Date fecha;

    @Column
    Time time;

    @Column
    String tablaAfectada;

    @Column
    String idAfectado;

    @Column
    String ipUsuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public Long getUsuarioSolicitando() {
        return usuarioSolicitando;
    }

    public void setUsuarioSolicitando(Long usuarioSolicitando) {
        this.usuarioSolicitando = usuarioSolicitando;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getTablaAfectada() {
        return tablaAfectada;
    }

    public void setTablaAfectada(String tablaAfectada) {
        this.tablaAfectada = tablaAfectada;
    }

    public String getIdAfectado() {
        return idAfectado;
    }

    public void setIdAfectado(String idAfectado) {
        this.idAfectado = idAfectado;
    }

    public String getIpUsuario() {
        return ipUsuario;
    }

    public void setIpUsuario(String ipUsuario) {
        this.ipUsuario = ipUsuario;
    }
}
