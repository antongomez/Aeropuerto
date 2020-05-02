/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class vConfirmacionControlador extends Controlador implements Initializable {

    @FXML
    private Label mensaje;
    @FXML
    private Button btnConfirmar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void mostrarMensaje(String men) {
        mensaje.setText(men);
    }

    @FXML
    private void accionBtnConfirmar(ActionEvent event) {
        getVenta().close();
    }

}
