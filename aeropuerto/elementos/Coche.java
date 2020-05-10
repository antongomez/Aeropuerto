package aeropuerto.elementos;

public class Coche {

    private String matricula;
    private String modelo;
    private Integer caballos;
    private Integer nPrazas;
    private Integer nPuertas;
    private Float precioDia;
    private String tipoCombustible;
    private Boolean retirado;

    public Coche(String matricula, String modelo, Integer caballos, Integer nPrazas, Integer nPuertas, Float precioDia, String tipoCombustible, Boolean retirado) {
        this.matricula = matricula;
        this.modelo = modelo;
        this.caballos = caballos;
        this.nPrazas = nPrazas;
        this.nPuertas = nPuertas;
        this.precioDia = precioDia;
        this.tipoCombustible = tipoCombustible;
        this.retirado = retirado;
    }

    public Coche(String matricula, String modelo, Float precioDia) {
        this.matricula = matricula;
        this.modelo = modelo;
        this.precioDia = precioDia;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getCaballos() {
        return caballos;
    }

    public void setCaballos(Integer caballos) {
        this.caballos = caballos;
    }

    public Integer getNPrazas() {
        return nPrazas;
    }

    public void setNPrazas(Integer nPrazas) {
        this.nPrazas = nPrazas;
    }

    public Integer getNPuertas() {
        return nPuertas;
    }

    public void setNPuertas(Integer nPuertas) {
        this.nPuertas = nPuertas;
    }

    public Float getPrecioDia() {
        return precioDia;
    }

    public void setPrecioDia(Float precioDia) {
        this.precioDia = precioDia;
    }

    public String getTipoCombustible() {
        return tipoCombustible;
    }

    public void setTipoCombustible(String tipoCombustible) {
        this.tipoCombustible = tipoCombustible;
    }

    public Boolean getRetirado() {
        return retirado;
    }

    public void setRetirado(Boolean retirado) {
        this.retirado = retirado;
    }

}
