package aeropuerto;

import aeropuerto.elementos.Vuelo;
import aeropuerto.elementos.Usuario;
import aeropuerto.gestion.GestionVuelos;
import aeropuerto.gestion.GestionUsuarios;
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

    public FachadaAplicacion() {
        fgui = new gui.FachadaGui(this);
        fbd = new baseDatos.FachadaBaseDatos(this);
        gu = new GestionUsuarios(fgui, fbd);
        gv = new GestionVuelos(fgui, fbd);
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
    public Boolean registrarUsuario(Usuario us) {//true si se registra y false si no
        return gu.registrarUsuario(us);
    }

    public Usuario credencialesCorrectos(String id, String cont) {
        return gu.credencialesCorrectos(id, cont);
    }

    //Vuelos
    public Boolean insertarVuelo(Vuelo v) {
        return gv.insertarVuelo(v);
    }

    public List<Vuelo> buscarVuelos(String numVuelo, String origen, String destino, Time fechaSalida, Time fechaLlegada) {
        return gv.buscarVuelos(numVuelo, origen, destino, fechaSalida, fechaLlegada);
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
