package aeropuerto.elementos;

public class Aerolinea {

    String nombre;
    String pais;
    Float precioBaseMaleta;
    Float pesoBaseMaleta;

    public Aerolinea(String nombre, Float precioBaseMaleta, Float pesoBaseMaleta) {
        this.nombre = nombre;
        this.precioBaseMaleta = (float) (Math.round(precioBaseMaleta * 100d) / 100d);
        this.pesoBaseMaleta = (float) (Math.round(pesoBaseMaleta * 100d) / 100d);
    }

    public Aerolinea(String nombre, String pais, Float precioBaseMaleta, Float pesoBaseMaleta) {
        this.nombre = nombre;
        this.pais = pais;
        this.precioBaseMaleta = (float) (Math.round(precioBaseMaleta * 100d) / 100d);
        this.pesoBaseMaleta = (float) (Math.round(pesoBaseMaleta * 100d) / 100d);
    }

    public Aerolinea(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPais() {
        return pais;
    }

    public Float getPrecioBaseMaleta() {
        return precioBaseMaleta;
    }

    public Float getPesoBaseMaleta() {
        return pesoBaseMaleta;
    }

}
