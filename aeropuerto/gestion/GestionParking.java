package aeropuerto.gestion;

import aeropuerto.elementos.Parking;
import aeropuerto.util.PorcentajeDisponibilidad;
import aeropuerto.util.Time;
import baseDatos.FachadaBaseDatos;
import gui.FachadaGui;
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
    
    public PorcentajeDisponibilidad obterPrazasRestantesParkingTerminal(Integer numTerminal, Time inicio, Time fin){
        return fbd.obterPrazasRestantesParkingTerminal(numTerminal, inicio, fin);
    }
}
