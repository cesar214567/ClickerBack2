package clicker.back.utils.entities;

import javax.persistence.*;

@Entity
public class Newsletter {
    @Column(name = "newsletter_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    @Column(unique = true)
    String correo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
