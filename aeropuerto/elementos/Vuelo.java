package aeropuerto.elementos;

import aeropuerto.util.Time;
import java.sql.Timestamp;
import java.util.HashMap;

public class Vuelo {

    private String numVuelo;
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
    private Avion avion;
    private Aerolinea aerolinea;
    private Integer plazasNormal;
    private Integer plazasPremium;
    private HashMap<Integer,Boolean> asientosNormalesDisponibles;
    private HashMap<Integer,Boolean> asientosPremiumDisponibles;
    private String retraso;//Es un string que viene de un interval en java (hh:mm:ss)

    public Vuelo(String numVuelo, String origen, String destino,
            Time fechasalidaTeo, Time fechasalidaReal,
            Time fechallegadaTeo, Time fechallegadaReal,
            Float precioActual, Integer puertaEmbarque, Boolean cancelado,
            Integer terminal, String codigoAvion) {

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
        this.avion = new Avion(codigoAvion);
        this.asientosNormalesDisponibles=new HashMap<>();
        this.asientosPremiumDisponibles=new HashMap<>();

    }

    public Vuelo(String numVuelo, String origen, String destino,
            Timestamp fechasalidaTeo, Timestamp fechasalidaReal,
            Timestamp fechallegadaTeo, Timestamp fechallegadaReal,
            Float precioActual, Integer puertaEmbarque, Boolean cancelado,
            Integer terminal, String codigoAvion) {

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
        this.avion = new Avion(codigoAvion);
        this.asientosNormalesDisponibles=new HashMap<>();
        this.asientosPremiumDisponibles=new HashMap<>();

    }

    public Aerolinea getAerolinea() {
        return aerolinea;
    }

    public Integer getPlazasNormal() {
        return plazasNormal;
    }

    public Integer getPlazasPremium() {
        return plazasPremium;
    }

    public String getNumVuelo() {
        return numVuelo;
    }

    public void setNumVuelo(String numVuelo) {
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

    public void setAerolinea(String nombre, Float precioMaleta, Float pesoMaleta) {
        this.aerolinea = new Aerolinea(nombre, precioMaleta, pesoMaleta);
    }
    public void setAerolinea(String nombre) {
        this.aerolinea = new Aerolinea(nombre);
    }
    public void setRetraso(String retraso){
        this.retraso=retraso;
    }
    public String getRetraso(){
        return retraso;
    }

    public void setPlazasNormal(Integer plazasNormal) {
        this.plazasNormal = plazasNormal;
    }

    public void setPlazasPremium(Integer plazasPremium) {
        this.plazasPremium = plazasPremium;
    }

    public Avion getAvion() {
        return avion;
    }

    public void setAvion(Avion avion) {
        this.avion = avion;
    }

    public Float getPrecioPremium() {
        return ((float) (Math.round((precioActual * 1.2f) * 100d) / 100d));
    }

    public HashMap<Integer, Boolean> getAsientosNormalesDisponibles() {
        return asientosNormalesDisponibles;
    }

    public void setAsientosNormalesDisponibles(HashMap<Integer, Boolean> asientosNormalesDisponibles) {
        this.asientosNormalesDisponibles = asientosNormalesDisponibles;
    }

    public HashMap<Integer, Boolean> getAsientosPremiumDisponibles() {
        return asientosPremiumDisponibles;
    }

    public void setAsientosPremiumDisponibles(HashMap<Integer, Boolean> asientosPremiumDisponibles) {
        this.asientosPremiumDisponibles = asientosPremiumDisponibles;
    }
    
    
}
