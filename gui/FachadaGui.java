package gui;

import aeropuerto.FachadaAplicacion;
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
        this.modelo = new Modelo(fa);
    }

    public void iniciarVista(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(FachadaGui.class.getResource("/gui/vista/vAcceder.fxml"));
        Pane root = (Pane) loader.load();

        //Collemos o controlador de Acceder
        vAccederControlador contAcceder = loader.getController();
        contAcceder.setModelo(this.modelo);

        primaryStage.setTitle("Aeropuerto");
        primaryStage.setScene(new Scene(root));
        
        
        contAcceder.setVenta(primaryStage);

        primaryStage.show();
    }

}
