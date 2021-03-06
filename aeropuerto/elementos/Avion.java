package aeropuerto.elementos;

public class Avion {

    private String codigo;
    private Integer asientosNormales;
    private Integer asientosPremium;

    public Avion(String codigo, Integer asientosNormales, Integer asientosPremium) {
        this.codigo = codigo;
        this.asientosNormales = asientosNormales;
        this.asientosPremium = asientosPremium;
    }

    public Avion(String codigo) {
        this.codigo = codigo;
    }

    public Integer getAsientosNormales() {
        return asientosNormales;
    }

    public void setAsientosNormales(Integer asientosNormales) {
        this.asientosNormales = asientosNormales;
    }

    public Integer getAsientosPremium() {
        return asientosPremium;
    }

    public void setAsientosPremium(Integer asientosPremium) {
        this.asientosPremium = asientosPremium;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

}
