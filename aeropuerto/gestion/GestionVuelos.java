package aeropuerto.gestion;

import aeropuerto.elementos.Usuario;
import aeropuerto.elementos.Vuelo;
import aeropuerto.util.Time;
import baseDatos.FachadaBaseDatos;
import gui.FachadaGui;
import java.util.List;

public class GestionVuelos {

    FachadaGui fgui;
    FachadaBaseDatos fbd;

    public GestionVuelos(FachadaGui fgui, FachadaBaseDatos fbd) {
        this.fgui = fgui;
        this.fbd = fbd;
    }

    public Boolean insertarVuelo(Vuelo v) {
        return fbd.insertarVuelo(v);
    }

    public List<Vuelo> buscarVuelos(String numVuelo, String origen, String destino, Time fechaSalida, Time fechaLlegada) {
        return fbd.buscarVuelos(numVuelo, origen, destino, fechaSalida, fechaLlegada);
    }
    public List<Vuelo> obtenerVuelosUsuario(String dniUs){
        return fbd.obtenerVuelosUsuario(dniUs);
    }
}