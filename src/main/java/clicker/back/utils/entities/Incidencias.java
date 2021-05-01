package clicker.back.utils.entities;

import clicker.back.entities.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Incidencias {
    @Column(name = "id_incidencias")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    @Column
    String descripcion;

    @Column
    String asunto;

    @Column
    String  tipo;

    @JsonIgnoreProperties({"fbId","imagenPerfil","numTelefono","password","tipoLicencia"
            ,"tiempoLicencia","trabajoAplicativo","tipoDocumento","numDocumento"
            ,"enabled","validated","rol","balance","cantidadCarrosAno","carrosPosteados"
            ,"denuncias","interesadoReventas","solicitudesRetiros","form"
            ,"secret","numeroDenuncias"})
    @JoinColumn(name ="id_usuario")
    @ManyToOne
    Usuario usuario;

    @Column
    String foto;

    @Column
    Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
