package aeropuerto.gestion;

import aeropuerto.elementos.Usuario;
import aeropuerto.elementos.Vuelo;
import aeropuerto.util.EstadisticasAerolinea;
import aeropuerto.util.Time;
import baseDatos.FachadaBaseDatos;
import gui.FachadaGui;
import java.util.List;
import javafx.collections.ObservableList;

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
    public void obtenerDatosAvionVuelo(Vuelo v){
        
        fbd.obtenerDatosAvionVuelo(v);
    }
    
    public void obtenerAsientos(Vuelo vuelo){
        fbd.obtenerAsientos(vuelo);
    }
    
    public Boolean comprarBilletes(ObservableList<Usuario> usuarios){
        return fbd.comprarBilletes(usuarios);
    }
    public List<Vuelo> verSalidas(){
        return fbd.verSalidas();
    }
    public List<Vuelo> verLlegadas(){
        return fbd.verLlegadas();
    }
    public EstadisticasAerolinea obtenerEstAerolineas(String aer){
        return fbd.obtenerEstAerolineas(aer);
    }
    public List<String> obtenerAerolineas(){
        return fbd.obtenerAerolineas();
    }
}
