package gui.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class vConfirmacionControlador extends Controlador implements Initializable {

    private Boolean confirmado;
    @FXML
    private Label mensaje;
    @FXML
    private Button btnConfirmar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        confirmado = false;
    }

    public void mostrarMensaje(String men) {
        mensaje.setText(men);
    }

    @FXML
    private void accionBtnConfirmar(ActionEvent event) {
        confirmado = true;
        getVenta().close();
    }

    @FXML
    private void accionBtnCancelar(ActionEvent event) {
        confirmado = false;
        getVenta().close();
    }

    public Boolean getConfirmado() {
        return confirmado;
    }

}
