/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 *
 * @author eliseopitavilarino
 */

//No se como hacer para que espere a se pulse uno de los dos botones
public class vConfirmacionControlador extends Controlador implements Initializable {

    @FXML
    private Label mensaje;
    @FXML
    private Button btnConfirmar;
    @FXML
    private Button btnCancelar;

    private Boolean confirmacion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        confirmacion = false;
    }

    public Boolean mostrarMensaje(String men) {
        mensaje.setText(men);
        getVenta().close();
        return this.confirmacion;
    }

    @FXML
    private void accionBtnConfirmar(ActionEvent event) {
        this.confirmacion = true;
    }

    @FXML
    private void accionBtnCancelar(ActionEvent event) {
        this.confirmacion = false;
    }

}
