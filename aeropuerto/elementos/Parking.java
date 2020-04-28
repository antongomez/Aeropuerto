package aeropuerto.elementos;

import java.util.ArrayList;
import java.util.Objects;

public class Parking {

    private Integer terminal;
    private Integer piso;
    private Integer numPrazas;
    private ArrayList<Integer> prazasLibres;

    public Parking(Integer terminal, Integer piso, Integer numPrazas, ArrayList<Integer> prazasLibres) {
        this.terminal = terminal;
        this.piso = piso;
        this.numPrazas = numPrazas;
        this.prazasLibres = prazasLibres;
    }

    public Parking(Integer terminal, Integer piso, Integer numPrazas) {
        this.terminal = terminal;
        this.piso = piso;
        this.numPrazas = numPrazas;
        this.prazasLibres = new ArrayList<>();
    }

    public Boolean comprobarPlazaLibre(int numPlaza) {
        if (prazasLibres == null) {
            return true;
        }
        for (int i = 0; i < prazasLibres.size(); i++) {
            if (prazasLibres.get(i) == numPlaza) {
                return true;
            }
        }
        return false;
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

    public ArrayList<Integer> getPrazasLibres() {
        return prazasLibres;
    }

    public void setPrazasLibres(ArrayList<Integer> prazasLibres) {
        this.prazasLibres = prazasLibres;
    }

    public Boolean anhadirPlazaLibre(Integer numPlaza) {
        if (!comprobarPlazaLibre(numPlaza)) {
            prazasLibres.add(numPlaza);
            return true;
        }
        return false;
    }

    public Boolean quitarPlazaLibre(Integer numPlaza) {
        for (int i = 0; i < prazasLibres.size(); i++) {
            if (prazasLibres.get(i).equals(numPlaza)) {
                prazasLibres.remove(i);
                return true;
            }
        }
        return false;
    }

}
