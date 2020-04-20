package aeropuerto.elementos;

import aeropuerto.util.Time;
import java.sql.Timestamp;

public class Vuelo {

    private Integer numVuelo;
    private String origen;
    private String destino;
    private Time fechasalidaTeo;
    private Time fechasalidaReal;
    private Time fechallegadaTeo;
    private Time fechallegadaReal;
    private Float precioActual;
    private Integer puertaEmbarque;
    private Boolean cancelado;
    private Integer terminal;
    private Integer avion;

    private Float precioPremium;

    public Vuelo(Integer numVuelo, String origen, String destino,
            Time fechasalidaTeo, Time fechasalidaReal,
            Time fechallegadaTeo, Time fechallegadaReal,
            Float precioActual, Integer puertaEmbarque, Boolean cancelado,
            Integer terminal, Integer avion) {

        this.numVuelo = numVuelo;
        this.origen = origen;
        this.destino = destino;
        this.fechasalidaTeo = fechasalidaTeo;
        this.fechasalidaReal = fechasalidaReal;
        this.fechallegadaTeo = fechallegadaTeo;
        this.fechallegadaReal = fechallegadaReal;
        this.precioActual = (float) (Math.round(precioActual * 100d) / 100d);
        this.puertaEmbarque = puertaEmbarque;
        this.cancelado = cancelado;
        this.terminal = terminal;
        this.avion = avion;

        this.precioPremium = (float) (Math.round((precioActual * 1.2f) * 100d) / 100d);
    }

    public Vuelo(Integer numVuelo, String origen, String destino,
            Timestamp fechasalidaTeo, Timestamp fechasalidaReal,
            Timestamp fechallegadaTeo, Timestamp fechallegadaReal,
            Float precioActual, Integer puertaEmbarque, Boolean cancelado,
            Integer terminal, Integer avion) {

        this.numVuelo = numVuelo;
        this.origen = origen;
        this.destino = destino;
        this.fechasalidaTeo = new Time(fechasalidaTeo);
        this.fechasalidaReal = new Time(fechasalidaReal);
        this.fechallegadaTeo = new Time(fechallegadaTeo);
        this.fechallegadaReal = new Time(fechallegadaReal);
        this.precioActual = (float) (Math.round(precioActual * 100d) / 100d);
        this.puertaEmbarque = puertaEmbarque;
        this.cancelado = cancelado;
        this.terminal = terminal;
        this.avion = avion;

        this.precioPremium = (float) (Math.round((precioActual * 1.2f) * 100d) / 100d);
    }

    public Integer getNumVuelo() {
        return numVuelo;
    }

    public void setNumVuelo(Integer numVuelo) {
        this.numVuelo = numVuelo;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Time getFechasalidaTeo() {
        return fechasalidaTeo;
    }

    public void setFechasalidaTeo(Time fechasalidaTeo) {
        this.fechasalidaTeo = fechasalidaTeo;
    }

    public Time getFechasalidaReal() {
        return fechasalidaReal;
    }

    public void setFechasalidaReal(Time fechasalidaReal) {
        this.fechasalidaReal = fechasalidaReal;
    }

    public Time getFechallegadaTeo() {
        return fechallegadaTeo;
    }

    public void setFechallegadaTeo(Time fechallegadaTeo) {
        this.fechallegadaTeo = fechallegadaTeo;
    }

    public Time getFechallegadaReal() {
        return fechallegadaReal;
    }

    public void setFechallegadaReal(Time fechallegadaReal) {
        this.fechallegadaReal = fechallegadaReal;
    }

    public Float getPrecioActual() {
        return precioActual;
    }

    public void setPrecioActual(Float precioActual) {
        this.precioActual = precioActual;
        this.precioPremium = (float) (Math.round((precioActual * 1.2f) * 100d) / 100d);
    }

    public Integer getPuertaEmbarque() {
        return puertaEmbarque;
    }

    public void setPuertaEmbarque(Integer puertaEmbarque) {
        this.puertaEmbarque = puertaEmbarque;
    }

    public Boolean getCancelado() {
        return cancelado;
    }

    public void setCancelado(Boolean cancelado) {
        this.cancelado = cancelado;
    }

    public Integer getTerminal() {
        return terminal;
    }

    public void setTerminal(Integer terminal) {
        this.terminal = terminal;
    }

    public Integer getAvion() {
        return avion;
    }

    public void setAvion(Integer avion) {
        this.avion = avion;
    }

    public Float getPrecioPremium() {
        return precioPremium;
    }

}
