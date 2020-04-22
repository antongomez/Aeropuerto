package gui.modelo;

import aeropuerto.FachadaAplicacion;
import aeropuerto.elementos.Usuario;
import aeropuerto.elementos.Vuelo;
import aeropuerto.util.Time;
import gui.FachadaGui;
import static gui.controlador.Controlador.loadWindow;
import gui.controlador.VErrorController;
import gui.controlador.VNotificacionController;
import gui.controlador.vConfirmacionControlador;
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
    
    public void mostrarConfirmacion(String mensaje){
        ((vConfirmacionControlador) loadWindow(getClass().getResource("/gui/vista/vConfirmacion.fxml"), "Confirmación", null)).mostrarMensaje(mensaje);
    }

    //Vuelos
    public Boolean insertarVuelo(Vuelo v) {
        return fa.insertarVuelo(v);
    }

    public List<Vuelo> buscarVuelos(String numVuelo, String origen, String destino, Time fechaSalida, Time fechaLlegada) {
        return fa.buscarVuelos(numVuelo, origen, destino, fechaSalida, fechaLlegada);
    }
    
    public List<Vuelo> obtenerVuelosUsuario(String dniUs){
        return fa.obtenerVuelosUsuario(dniUs);
    }
    
    //Función cambiar datos
    public boolean modificarUsuario(Usuario usuario){
        return fa.modificarUsuario(usuario);
    }
    
    
   public boolean eliminarUsuario(String dni){
       return fa.eliminarUsuario(dni);
   }

}
