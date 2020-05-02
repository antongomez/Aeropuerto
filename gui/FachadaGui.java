package gui;

import aeropuerto.FachadaAplicacion;
import aeropuerto.elementos.Vuelo;
import gui.controlador.Controlador;
import gui.controlador.vAccederControlador;
import gui.modelo.Modelo;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FachadaGui {

    FachadaAplicacion fa;
    Modelo modelo; //Nuevo campo modelo

    public FachadaGui(FachadaAplicacion fa) {
        this.fa = fa;
        //Crease a instancia de modelo
        this.modelo = Modelo.newModelo(fa);
    }

    public void iniciarVista(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(FachadaGui.class.getResource("/gui/vista/vAcceder.fxml"));
        Pane root = (Pane) loader.load();

        //Collemos o controlador de Acceder
        vAccederControlador contAcceder = loader.getController();

        primaryStage.setTitle("Aeropuerto");
        primaryStage.setScene(new Scene(root));

        contAcceder.setVenta(primaryStage);
        Controlador.setStageIcon(primaryStage);
        primaryStage.show();

    }

    public void mostrarError(String mensaje) {
        //((VErrorController)loadWindow(getClass().getResource("/gui/vista/vError.fxml"), "Error", null)).mostrarMensaje(mensaje);
        modelo.mostrarError(mensaje);
    }

}
