package aeropuerto;

import javafx.application.Application;
import javafx.stage.Stage;

public class FachadaAplicacion extends Application {

    gui.FachadaGui fgui;
    baseDatos.FachadaBaseDatos fbd;
    GestionUsuarios gu;

    public FachadaAplicacion() {
        fgui = new gui.FachadaGui(this);
        fbd = new baseDatos.FachadaBaseDatos(this);
        gu = new GestionUsuarios(fgui, fbd);
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

    public Boolean registrarUsuario(Usuario us) {//true si se registra y false si no
        return gu.registrarUsuario(us);
    }

    public void mostrarError(String menseje) {
        fgui.mostrarError(menseje);
    }

    public Usuario credencialesCorrectos(String id, String cont) {
        return gu.credencialesCorrectos(id, cont);
    }

}
