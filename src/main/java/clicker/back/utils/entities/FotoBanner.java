package clicker.back.utils.entities;

import javax.persistence.*;

@Entity
public class FotoBanner {
    @Column(name = "foto_banner_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    @Column(length = 1000)
    String foto;

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
}
