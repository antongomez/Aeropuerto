package gui.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class VErrorController extends Controlador implements Initializable {

    @FXML
    private Label mensaje;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void mostrarMensaje(String men) {
        mensaje.setText(men);
    }

    @FXML
    private void accionAceptar(ActionEvent event) {
        getVenta().close();
    }

}
