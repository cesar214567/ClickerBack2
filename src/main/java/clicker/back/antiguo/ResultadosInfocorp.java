package clicker.back.antiguo;

import clicker.back.entities.Usuario;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
public class ResultadosInfocorp {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long idConsulta;

    @JoinColumn(name = "id_user")
    @OneToOne
    Usuario user;

    @Column
    Date fechaConsulta;

    @Column
    Time horaConsulta;

    @Column(length = 20)
    String documento;

    @Column(length = 100,name="RazonSocial")
    String razonSocial;

    @Column(length = 10,name ="FechaNacimiento")
    String fechaNacimiento;

    @Column(length = 10,name ="FechaProceso")
    String fechaProceso;

    @Column(length = 24,name ="Semaforos")
    String semaforos;

    @Column(length = 7,name ="Nota")
    String nota;

    @Column(length = 2,name ="NroBancos")
    String nroBancos;

    @Column(length = 20,name ="DeudaTotal")
    String deudaTotal;

    @Column(length = 20,name ="VencidoBanco")
    String vencidoBanco;

    @Column(length = 100,name ="Calificativo")
    String calificativo;

    @Column(length = 2,name ="SemaActual")
    String semaActual;

    @Column(length = 2,name ="SemaPrevio")
    String semaPrevio;

    @Column(length = 2,name ="SemaPeorMejor")
    String semaPeorMejor;

    @Column(length = 20,name ="Documento2")
    String documento2;

    @Column(length = 2,name ="EstDomic")
    String estDomic;

    @Column(length = 2,name ="CondDomic")
    String condDomic;

    @Column(length = 20,name ="DeudaTributaria")
    String deudaTributaria;

    @Column(length = 20,name ="DeudaLaboral")
    String deudaLaboral;

    @Column(length = 20,name ="DeudaImpaga")
    String deudaImpaga;

    @Column(length = 20,name ="DeudaProtestos")
    String deudaProtestos;

    @Column(length = 20,name ="deudaSBS")
    String deudaSBS;

    @Column(length = 2,name ="TarCtas")
    String tarCtas;

    @Column(length = 2,name ="RepNeg")
    String repNeg;

    @Column(length = 100,name ="TipoActv")
    String tipoActv;

    @Column(length = 10,name ="FechIniActv")
    String fechIniActv;

    @Column(length = 400,name ="DireccionFiscal")
    String direccionFiscal;

    @Column(length = 3,name ="CodigoWS")
    String codigoWS;

    @Column(name = "Resultado")
    Boolean resultado;

    @Column(name = "Procesado")
    Boolean procesado;

    public Long getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(Long idConsulta) {
        this.idConsulta = idConsulta;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public Date getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(Date fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    public Time getHoraConsulta() {
        return horaConsulta;
    }

    public void setHoraConsulta(Time horaConsulta) {
        this.horaConsulta = horaConsulta;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getFechaProceso() {
        return fechaProceso;
    }

    public void setFechaProceso(String fechaProceso) {
        this.fechaProceso = fechaProceso;
    }

    public String getSemaforos() {
        return semaforos;
    }

    public void setSemaforos(String semaforos) {
        this.semaforos = semaforos;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getNroBancos() {
        return nroBancos;
    }

    public void setNroBancos(String nroBancos) {
        this.nroBancos = nroBancos;
    }

    public String getDeudaTotal() {
        return deudaTotal;
    }

    public void setDeudaTotal(String deudaTotal) {
        this.deudaTotal = deudaTotal;
    }

    public String getVencidoBanco() {
        return vencidoBanco;
    }

    public void setVencidoBanco(String vencidoBanco) {
        this.vencidoBanco = vencidoBanco;
    }

    public String getCalificativo() {
        return calificativo;
    }

    public void setCalificativo(String calificativo) {
        this.calificativo = calificativo;
    }

    public String getSemaActual() {
        return semaActual;
    }

    public void setSemaActual(String semaActual) {
        this.semaActual = semaActual;
    }

    public String getSemaPrevio() {
        return semaPrevio;
    }

    public void setSemaPrevio(String semaPrevio) {
        this.semaPrevio = semaPrevio;
    }

    public String getSemaPeorMejor() {
        return semaPeorMejor;
    }

    public void setSemaPeorMejor(String semaPeorMejor) {
        this.semaPeorMejor = semaPeorMejor;
    }

    public String getDocumento2() {
        return documento2;
    }

    public void setDocumento2(String documento2) {
        this.documento2 = documento2;
    }

    public String getEstDomic() {
        return estDomic;
    }

    public void setEstDomic(String estDomic) {
        this.estDomic = estDomic;
    }

    public String getCondDomic() {
        return condDomic;
    }

    public void setCondDomic(String condDomic) {
        this.condDomic = condDomic;
    }

    public String getDeudaTributaria() {
        return deudaTributaria;
    }

    public void setDeudaTributaria(String deudaTributaria) {
        this.deudaTributaria = deudaTributaria;
    }

    public String getDeudaLaboral() {
        return deudaLaboral;
    }

    public void setDeudaLaboral(String deudaLaboral) {
        this.deudaLaboral = deudaLaboral;
    }

    public String getDeudaImpaga() {
        return deudaImpaga;
    }

    public void setDeudaImpaga(String deudaImpaga) {
        this.deudaImpaga = deudaImpaga;
    }

    public String getDeudaProtestos() {
        return deudaProtestos;
    }

    public void setDeudaProtestos(String deudaProtestos) {
        this.deudaProtestos = deudaProtestos;
    }

    public String getDeudaSBS() {
        return deudaSBS;
    }

    public void setDeudaSBS(String deudaSBS) {
        this.deudaSBS = deudaSBS;
    }

    public String getTarCtas() {
        return tarCtas;
    }

    public void setTarCtas(String tarCtas) {
        this.tarCtas = tarCtas;
    }

    public String getRepNeg() {
        return repNeg;
    }

    public void setRepNeg(String repNeg) {
        this.repNeg = repNeg;
    }

    public String getTipoActv() {
        return tipoActv;
    }

    public void setTipoActv(String tipoActv) {
        this.tipoActv = tipoActv;
    }

    public String getFechIniActv() {
        return fechIniActv;
    }

    public void setFechIniActv(String fechIniActv) {
        this.fechIniActv = fechIniActv;
    }

    public String getDireccionFiscal() {
        return direccionFiscal;
    }

    public void setDireccionFiscal(String direccionFiscal) {
        this.direccionFiscal = direccionFiscal;
    }

    public String getCodigoWS() {
        return codigoWS;
    }

    public void setCodigoWS(String codigoWS) {
        this.codigoWS = codigoWS;
    }

    public Boolean getResultado() {
        return resultado;
    }

    public void setResultado(Boolean resultado) {
        this.resultado = resultado;
    }

    public Boolean getProcesado() {
        return procesado;
    }

    public void setProcesado(Boolean procesado) {
        this.procesado = procesado;
    }
}
