package gui.modelo;

import aeropuerto.FachadaAplicacion;
import aeropuerto.elementos.Usuario;
import aeropuerto.elementos.Vuelo;
import gui.FachadaGui;
import static gui.controlador.Controlador.loadWindow;
import gui.controlador.VErrorController;
import gui.controlador.VNotificacionController;
import java.util.List;

public class Modelo {

    private static Modelo mod = null; //Instancia do modelo
    private final FachadaAplicacion fa; //Desde aqui llamaremos a la fachada
    private final FachadaGui fgui;

    private Modelo(FachadaAplicacion fa) {
        this.fa = fa;
        this.fgui = fa.getFgui();
    }

    //Desta forma, creamos unha vez a clase modelo e podemola ir obtendo a traves das clases
    public static Modelo newModelo(FachadaAplicacion fa) {
        if (mod == null) {
            mod = new Modelo(fa);
        }

        return mod;
    }

    public static Modelo getInstanceModelo() {
        return mod;
    }

    public Boolean registrarUsuario(Usuario us) {
        return fa.registrarUsuario(us);
    }

    public void mostrarError(String mensaje) {
        ((VErrorController) loadWindow(getClass().
                getResource("/gui/vista/vError.fxml"), "Error", null)).mostrarMensaje(mensaje);
    }

    public Usuario credencialesCorrectos(String id, String cont) {
        return fa.credencialesCorrectos(id, cont);
    }

    public void mostrarNotificacion(String mensaje) {
        ((VNotificacionController) loadWindow(getClass().
                getResource("/gui/vista/vNotificacion.fxml"), "Notificación", null)).mostrarMensaje(mensaje);
    }

    //Vuelos
    public Boolean insertarVuelo(Vuelo v) {
        return fa.insertarVuelo(v);
    }

    public List<Vuelo> buscarVuelos(String numVuelo, String origen, String destino) {
        return fa.buscarVuelos(numVuelo, origen, destino);
    }

}
