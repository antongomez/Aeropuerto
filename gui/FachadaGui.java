package gui;

import aeropuerto.FachadaAplicacion;
import controlador.Controlador;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FachadaGui {

    FachadaAplicacion fa;
    Controlador controlador;

    public FachadaGui(FachadaAplicacion fa) {
        this.fa = fa;
    }

    public void iniciarVista(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(FachadaGui.class.getResource("/gui/vista/vRegistrarse.fxml"));
        Pane root = (Pane) loader.load();

        controlador = new Controlador();

        primaryStage.setTitle("Aeropuerto");
        primaryStage.setScene(new Scene(root));

        controlador.setVenta(primaryStage);

        primaryStage.show();
    }

}
