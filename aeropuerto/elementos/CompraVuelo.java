package aeropuerto.elementos;

public class CompraVuelo {

    String numVuelo;
    Boolean acompanante;
    Integer numMaletas;
    Boolean premium;
    Integer asiento;
    Float precio;

    public CompraVuelo(String numVuelo) {
        this.numVuelo = numVuelo;
        acompanante = false;
        numMaletas = 0;
        premium = false;
    }

    public void setAcompanante(Boolean acompanante) {
        this.acompanante = acompanante;
    }

    public void setNumMaletas(Integer numMaletas) {
        this.numMaletas = numMaletas;
    }

    public void setPremium(Boolean premium) {
        this.premium = premium;
    }

    public Boolean getAcompanante() {
        return acompanante;
    }

    public Integer getNumMaletas() {
        return numMaletas;
    }

    public Boolean getPremium() {
        return premium;
    }

    public Integer getAsiento() {
        return asiento;
    }

    public void setAsiento(Integer asiento) {
        this.asiento = asiento;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public String getNumVuelo() {
        return numVuelo;
    }

    public void setNumVuelo(String numVuelo) {
        this.numVuelo = numVuelo;
    }

}
