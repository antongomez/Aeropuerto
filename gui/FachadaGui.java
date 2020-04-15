package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FachadaGui {

    aeropuerto.FachadaAplicacion fa;

    public FachadaGui(aeropuerto.FachadaAplicacion fa) {
        this.fa = fa;
    }

    public void iniciarVista(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(FachadaGui.class.getResource("/gui/vista/vAcceder.fxml"));
        primaryStage.setTitle("Aeroporto");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}
