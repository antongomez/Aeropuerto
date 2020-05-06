package aeropuerto.elementos;

public class Tienda {

    private String nombre;
    private String tipoVentas;
    private Integer terminal;

    public Tienda(String nombre, String tipoVentas, Integer terminal) {
        this.nombre = nombre;
        this.tipoVentas = tipoVentas;
        this.terminal = terminal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoVentas() {
        return tipoVentas;
    }

    public void setTipoVentas(String tipoVentas) {
        this.tipoVentas = tipoVentas;
    }

    public Integer getTerminal() {
        return terminal;
    }

    public void setTerminal(Integer terminal) {
        this.terminal = terminal;
    }

}
