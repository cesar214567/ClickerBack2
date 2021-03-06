package clicker.back.antiguo;

import javax.persistence.*;

@Entity
public class OpcionesPrincipalesVenta {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long idOpciones;

    @Column(unique = true)
    String titulo;

    @Column
    Boolean link;

    @Column(length = 1000)
    String transmitido;

    @Column(length = 1000)
    String urlImagen;

    @Column
    Boolean activar;

    @Column(length = 30)
    String textoBoton;

    @Column
    String subtitulo;

    public Long getIdOpciones() {
        return idOpciones;
    }

    public void setIdOpciones(Long idOpciones) {
        this.idOpciones = idOpciones;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Boolean getLink() {
        return link;
    }

    public void setLink(Boolean link) {
        this.link = link;
    }

    public String getTransmitido() {
        return transmitido;
    }

    public void setTransmitido(String transmitido) {
        this.transmitido = transmitido;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public Boolean getActivar() {
        return activar;
    }

    public void setActivar(Boolean activar) {
        this.activar = activar;
    }

    public String getTextoBoton() {
        return textoBoton;
    }

    public void setTextoBoton(String textoBoton) {
        this.textoBoton = textoBoton;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }
}
