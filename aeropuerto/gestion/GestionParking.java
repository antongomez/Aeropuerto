package aeropuerto.gestion;

import aeropuerto.elementos.Parking;
import aeropuerto.util.PorcentajeDisponibilidad;
import aeropuerto.util.Time;
import baseDatos.FachadaBaseDatos;
import gui.FachadaGui;
import java.util.ArrayList;
import java.util.List;

public class GestionParking {

    private FachadaGui fgui;
    private FachadaBaseDatos fbd;

    public GestionParking(FachadaGui fgui, FachadaBaseDatos fbd) {
        this.fgui = fgui;
        this.fbd = fbd;
    }

    public List<Integer> buscarTerminais() {
        return fbd.buscarTerminais();
    }

    public Parking buscarParking(Integer terminal, Time inicio, Time fin) {
        return fbd.buscarParking(terminal, inicio, fin);
    }

    public PorcentajeDisponibilidad obterPrazasRestantesParkingTerminal(Integer numTerminal, Time inicio, Time fin) {
        return fbd.obterPrazasRestantesParkingTerminal(numTerminal, inicio, fin);
    }

    public Integer obterPrazaLibre(Integer numTerminal, Integer piso, Time inicio, Time fin) {
        Integer numPrazas = fbd.obterNumPlazasParking(numTerminal, piso);
        List<Integer> prazasOc = fbd.obterPlazasOcupadas(numTerminal, piso, inicio, fin);

        //Enchemos o array de plazas libres
        for (int i = 1; i <= numPrazas; i++) {
            if (estaLibrePraza(i, prazasOc)) {
                return i; //Devolvese a primeira libre
            }
        }

        //Dara un erro
        return 0;
    }

    private Boolean estaLibrePraza(int praza, List<Integer> prazasOc) {
        for (int i = 0; i < prazasOc.size(); i++) {
            if (prazasOc.get(i).equals(praza)) {
                return false;
            }
        }
        return true;
    }
}
