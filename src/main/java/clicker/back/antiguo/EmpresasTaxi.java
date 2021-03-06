package clicker.back.antiguo;

import javax.persistence.*;

@Entity
public class EmpresasTaxi {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long idEmptaxi;

    @Column(unique = true)
    String empresa;

    public Long getIdEmptaxi() {
        return idEmptaxi;
    }

    public void setIdEmptaxi(Long idEmptaxi) {
        this.idEmptaxi = idEmptaxi;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }
}
