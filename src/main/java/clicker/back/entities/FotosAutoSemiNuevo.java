package clicker.back.entities;

import javax.persistence.*;

@Entity
public class FotosAutoSemiNuevo {
    @Column(name = "fotos_carro_semi_nuevo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    @Column(length = 1000)
    String foto;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

}
