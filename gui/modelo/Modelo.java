package gui.modelo;

import aeropuerto.FachadaAplicacion;
import aeropuerto.Usuario;
import static gui.controlador.Controlador.loadWindow;
import gui.controlador.VErrorController;
import gui.controlador.VNotificacionController;

public class Modelo {

    private static Modelo mod = null; //Instancia do modelo
    private FachadaAplicacion fa; //Desde aqui llamaremos a la fachada

    private Modelo(FachadaAplicacion fa) {
        this.fa = fa;
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
    public void mostrarError(String mensaje){
      ((VErrorController)loadWindow(getClass().getResource("/gui/vista/vError.fxml"), "Error", null)).mostrarMensaje(mensaje);
    }
    public Usuario credencialesCorrectos(String id, String cont){
        return fa.credencialesCorrectos(id,cont);
    }
    public void mostrarNotificacion(String mensaje){
        ((VNotificacionController)loadWindow(getClass().getResource("/gui/vista/vNotificacion.fxml"), "Notificación", null)).mostrarMensaje(mensaje);
    }
    
}
