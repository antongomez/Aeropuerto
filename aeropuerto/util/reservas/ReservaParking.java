package aeropuerto.util.reservas;

import aeropuerto.util.Time;
import java.sql.Timestamp;

public class ReservaParking extends Reserva {

    private Integer terminal;
    private Integer piso;
    private Integer numPlaza;

    /*Constructor reserva parking*/
    public ReservaParking(Timestamp inicio, Timestamp fin, String matricula, Integer terminal, Integer piso, Integer numPlaza) {
        super(inicio, fin, matricula);

        this.terminal = terminal;
        this.piso = piso;
        this.numPlaza = numPlaza;
    }

    public ReservaParking(Time inicio, Time fin, String matricula, Integer terminal, Integer piso, Integer numPlaza) {
        super(inicio, fin, matricula);

        this.terminal = terminal;
        this.piso = piso;
        this.numPlaza = numPlaza;
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

    public Integer getNumPlaza() {
        return numPlaza;
    }

    public void setNumPlaza(Integer numPlaza) {
        this.numPlaza = numPlaza;
    }

}
