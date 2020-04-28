package aeropuerto.elementos;

public class Parking {

    private Integer terminal;
    private Integer piso;
    private Integer numPrazas;

    public Parking(Integer terminal, Integer piso, Integer numPrazas) {
        this.terminal = terminal;
        this.piso = piso;
        this.numPrazas = numPrazas;
    }

    public Integer getTerminal() {
        return terminal;
    }

    public void setTerminal(Integer terminal) {
        this.terminal = terminal;
    }

    public Integer getPiso() {
        return piso;
    }

    public void setPiso(Integer piso) {
        this.piso = piso;
    }

    public Integer getNumPrazas() {
        return numPrazas;
    }

    public void setNumPrazas(Integer numPrazas) {
        this.numPrazas = numPrazas;
    }

}
