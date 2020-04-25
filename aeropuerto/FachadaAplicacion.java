package aeropuerto;

import aeropuerto.elementos.Vuelo;
import aeropuerto.elementos.Usuario;
import aeropuerto.gestion.GestionReservas;
import aeropuerto.gestion.GestionVuelos;
import aeropuerto.gestion.GestionUsuarios;
import aeropuerto.util.EstadisticasUsuario;
import aeropuerto.util.Reserva;
import aeropuerto.util.Time;
import baseDatos.FachadaBaseDatos;
import gui.FachadaGui;
import java.util.List;
import javafx.application.Application;
import javafx.stage.Stage;

public class FachadaAplicacion extends Application {

    FachadaGui fgui;
    FachadaBaseDatos fbd;
    GestionUsuarios gu;
    GestionVuelos gv;
    GestionReservas gr;

    public FachadaAplicacion() {
        fgui = new gui.FachadaGui(this);
        fbd = new baseDatos.FachadaBaseDatos(this);
        gu = new GestionUsuarios(fgui, fbd);
        gv = new GestionVuelos(fgui, fbd);
        gr = new GestionReservas(fgui, fbd);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void iniciaInterfazUsuario(Stage stage) throws Exception {
        fgui.iniciarVista(stage);
    }

    @Override
    public void start(Stage stage) {
        FachadaAplicacion fa;
        fa = new FachadaAplicacion();
        try {
            fa.iniciaInterfazUsuario(stage);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());;
        }
    }

    //Usuarios
    public Boolean registrarUsuario(Usuario us, String clave) {//true si se registra y false si no
        return gu.registrarUsuario(us, clave);
    }
    public Boolean modificarContrasenha(String idUsuario, String clave){
        return gu.modificarContrasenha(idUsuario, clave);
    }

    public Usuario credencialesCorrectos(String id, String cont) {
        return gu.credencialesCorrectos(id, cont);
    }
    public Boolean modificarUsuario(Usuario us){
        return gu.modificarUsuario(us);
    }
    
    public boolean eliminarUsuario(String dni){
       return gu.eliminarUsuario(dni);
    }
    
    //en la interfaz falta poner si está o no cancelado y cuánto falta para su salida
    public List<Vuelo> obtenerVuelosUsuario(String dniUs){//no está probada
        return gv.obtenerVuelosUsuario(dniUs);
    }
    /*tipo: año/mes/estación
    tipo=anho => num= año concreto
    tipo=mes => num=1,...,12 dependiendo del mes concreto
    tipo= estacion => num=1,...,4 dependiendo de la estación concreta en este orden: primavera, verano, otoño, invierno*/
    public EstadisticasUsuario obtenerEstadisticasUsuario(String dniUs, String tipo, Integer num){
        return gu.obtenerEstadisticasUsuario(dniUs, tipo, num);
    }

    //Vuelos
    public Boolean insertarVuelo(Vuelo v) {
        return gv.insertarVuelo(v);
    }

    public List<Vuelo> buscarVuelos(String numVuelo, String origen, String destino, Time fechaSalida, Time fechaLlegada) {
        return gv.buscarVuelos(numVuelo, origen, destino, fechaSalida, fechaLlegada);
    }
    
    //Reservas
    /*Muestra las reservas de un usuario que aún no han sido vencidas*/
    public List<Reserva> obtenerReservasUsuario(String dniUs){
        return gr.obtenerReservasUsuario(dniUs);
    }
    

    //Erros
    public void mostrarError(String menseje) {
        fgui.mostrarError(menseje);
    }

    //Getters
    public FachadaGui getFgui() {
        return fgui;
    }
    
 
    
    

}
