package clicker.back.antiguo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Documentos {
    @Id
    String documentname;

    @Column
    Integer documentsize;

    public String getDocumentname() {
        return documentname;
    }

    public void setDocumentname(String documentname) {
        this.documentname = documentname;
    }

    public Integer getDocumentsize() {
        return documentsize;
    }

    public void setDocumentsize(Integer documentsize) {
        this.documentsize = documentsize;
    }
}
