package clicker.back.controllers.beans;

public class FiltrosBean {
    String marca;

    String modelo;

    String tipoCarroceria;

    String tipoCarro;

    public FiltrosBean(String marca, String modelo, String tipoCarroceria, String tipoCarro) {
        this.marca = marca;
        this.modelo = modelo;
        this.tipoCarroceria = tipoCarroceria;
        this.tipoCarro = tipoCarro;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTipoCarroceria() {
        return tipoCarroceria;
    }

    public void setTipoCarroceria(String tipoCarroceria) {
        this.tipoCarroceria = tipoCarroceria;
    }

    public String getTipoCarro() {
        return tipoCarro;
    }

    public void setTipoCarro(String tipoCarro) {
        this.tipoCarro = tipoCarro;
    }
}
