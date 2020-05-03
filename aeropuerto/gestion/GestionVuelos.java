package aeropuerto.gestion;

import aeropuerto.elementos.Aerolinea;
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
    static final float EXTRA_MALETA_KG=5;

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

    public List<Vuelo> obtenerVuelosUsuario(String dniUs) {
        return fbd.obtenerVuelosUsuario(dniUs);
    }

    public void obtenerDatosAvionVuelo(Vuelo v) {

        fbd.obtenerDatosAvionVuelo(v);
    }

    public void obtenerAsientos(Vuelo vuelo) {
        fbd.obtenerAsientos(vuelo);
    }

    public Boolean comprarBilletes(ObservableList<Usuario> usuarios) {
        return fbd.comprarBilletes(usuarios);
    }

    public Boolean plazoDevolucion(String vuelo) {
        return fbd.plazoDevolucion(vuelo);
    }

    public Boolean vueloRealizado(String vuelo) {
        return fbd.vueloRealizado(vuelo);
    }

    public Boolean devolverBillete(String vuelo, String dni) {
        return fbd.devolverBillete(vuelo, dni);
    }

    public List<Vuelo> verSalidas() {
        return fbd.verSalidas();
    }

    public List<Vuelo> verLlegadas() {
        return fbd.verLlegadas();
    }

    public EstadisticasAerolinea obtenerEstAerolineas(String aer) {
        return fbd.obtenerEstAerolineas(aer);
    }

    public List<String> obtenerAerolineasConVuelos() {
        return fbd.obtenerAerolineasConVuelos();
    }

    public Boolean pasarControlBillete(String dni, String vuelo) {
        return fbd.pasarControlBillete(dni, vuelo);
    }

    public Boolean salirControlBillete(String dni, String vuelo) {
        return fbd.salirControlBillete(dni, vuelo);
    }

    public List<String> obtenerAnhosViajados(String dni) {
        return fbd.obtenerAnhosViajados(dni);
    }
    /*Los tres primeros argumentos son de entrada y el último de salida.
    La función devuelve si fue posible facturar la maleta*/
    public Boolean facturarMaleta(String dni, String vuelo, Float peso,Float precioExtra){
       
        Float precio=(float)0;
        if(fbd.facturarMaleta(dni, vuelo, peso)==false){
            return false;
        }
        else{
            Aerolinea aer=fbd.obtenerDatosAerolinea(vuelo);
            /*Si después de facturar, el número de maletas disponibles es negativo*/
            if(fbd.numeroMaletasDisponibles(dni, vuelo)<0){
                precio+=aer.getPrecioBaseMaleta();
            }
            if(peso>aer.getPesoBaseMaleta()){
                precio+=EXTRA_MALETA_KG*(peso-aer.getPesoBaseMaleta());
            }
            precioExtra=precio;
            return true;
        }
        
        
            
    }
    
}
