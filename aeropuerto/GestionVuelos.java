package aeropuerto;

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

    public List<Vuelo> buscarVuelos(Integer numVuelo, String origen, String destino) {
        return fbd.buscarVuelos(numVuelo, origen, destino);
    }
}
